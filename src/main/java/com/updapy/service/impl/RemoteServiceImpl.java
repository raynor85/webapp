package com.updapy.service.impl;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMethod;

import com.updapy.model.ApplicationReference;
import com.updapy.model.ApplicationVersion;
import com.updapy.model.enumeration.TypeRetrievalError;
import com.updapy.service.RemoteService;
import com.updapy.service.RetrievalErrorService;
import com.updapy.service.retriever.RemoteRetriever;

@Service
public class RemoteServiceImpl implements RemoteService {

	@Autowired
	private RetrievalErrorService retrievalErrorService;

	@Autowired
	private List<RemoteRetriever> remoteRetrievers;

	@Override
	public ApplicationVersion retrieveRemoteLatestVersion(ApplicationReference application) {
		Document doc = retrieveHtmlDocument(application);
		if (doc == null) {
			return null;
		}

		ApplicationVersion version = new ApplicationVersion();
		version.setApplication(application);
		version.setVersionDate(new Date());

		try {
			retrieveInformation: {
				for (RemoteRetriever remoteRetriever : remoteRetrievers) {
					if (remoteRetriever.support(application)) {
						version.setVersionNumber(remoteRetriever.retrieveVersionNumber(doc));
						version.setWin32UrlEn(remoteRetriever.retrieveWin32UrlEn(doc));
						version.setWin32UrlFr(remoteRetriever.retrieveWin32UrlFr(doc));
						version.setWin64UrlEn(remoteRetriever.retrieveWin64UrlEn(doc));
						version.setWin64UrlFr(remoteRetriever.retrieveWin64UrlFr(doc));
						break retrieveInformation;
					}
				}
			}
		} catch (Exception e) {
			// parsing error
			retrievalErrorService.addRetrievalError(application, TypeRetrievalError.REMOTE_PARSING_ERROR, e.getMessage());
			return null;
		}

		if (StringUtils.isBlank(version.getVersionNumber()) || StringUtils.isBlank(version.getWin32UrlEn()) || !version.isValidVersionNumber() || !isUrlValid(version.getWin32UrlEn()) || !isUrlValid(version.getWin32UrlFr()) || !isUrlValid(version.getWin64UrlEn()) || !isUrlValid(version.getWin64UrlFr())) {
			// remote version not valid
			retrievalErrorService.addRetrievalError(application, TypeRetrievalError.REMOTE_URL_VERSION_ERROR);
			return null;
		}

		retrievalErrorService.deleteRetrievalErrors(application, Arrays.asList(TypeRetrievalError.REMOTE_PARSING_ERROR, TypeRetrievalError.REMOTE_URL_VERSION_ERROR, TypeRetrievalError.REMOTE_NEW_VERSION_WITH_NUMBER_NOT_CONSISTENT, TypeRetrievalError.REMOTE_URL_BASE_ERROR));
		return version;
	}

	@Override
	public boolean isUrlValid(String url) {
		if (StringUtils.isBlank(url)) {
			// empty url is fine
			return true;
		}
		int code;
		try {
			code = getResponseCode(url);
		} catch (SSLHandshakeException e) {
			return true; // https error are fine; jsoup cannot handle this properly, the link should be valid
		} catch (IOException e) {
			return false;
		}
		return code <= 400 || code == 403; // code above 399 are errors. 400 or 403 are fine. Most important is to avoid 404...
	}

	private int getResponseCode(String urlStr) throws IOException {
		URL url = new URL(urlStr);
		URLConnection urlConnection = url.openConnection();
		if (urlConnection instanceof HttpURLConnection) {
			HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;
			httpURLConnection.setRequestMethod(RequestMethod.GET.name());
			httpURLConnection.connect();
			return httpURLConnection.getResponseCode();
		}
		return 0; // we don't manage FTP
	}

	private Document retrieveHtmlDocument(ApplicationReference application) {
		Document doc = null;
		String url = application.getGlobalUrl();
		try {
			doc = retrieveHtmlDocumentAgent64(url);
		} catch (Exception e1) {
			try {
				// let's try a second time, network can be unreliable sometimes!
				doc = retrieveHtmlDocumentAgent64(url);
			} catch (Exception e2) {
				try {
					// let's try a third time WITHOUT timeout and SSL and ignoring http errors...
					doc = retrieveHtmlDocumentAgent64(url, 0, false, true);
				} catch (Exception e3) {
					// seems there is really a problem
					retrievalErrorService.addRetrievalError(application, TypeRetrievalError.REMOTE_URL_BASE_ERROR, e3.getMessage());
					return null;
				}
			}
		}
		return doc;
	}

	public static Document retrieveHtmlDocumentAgent64(String url, int timeout, boolean verifySsl, boolean ignoreHttpErrors) throws IOException {
		if (!verifySsl) {
			setTrustAllCerts();
		}
		return Jsoup.connect(url).ignoreHttpErrors(ignoreHttpErrors).ignoreContentType(true).userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0").referrer("http://www.google.com").timeout(timeout).followRedirects(true).get();
	}

	public static Document retrieveHtmlDocumentAgent32(String url, int timeout) throws IOException {
		return Jsoup.connect(url).ignoreContentType(true).userAgent("Mozilla/5.0 (Windows NT 6.1; Win32; rv:25.0) Gecko/20100101 Firefox/25.0").referrer("http://www.google.com").timeout(timeout).followRedirects(true).get();
	}

	public static Document retrieveHtmlDocumentAgent64(String url) throws IOException {
		return retrieveHtmlDocumentAgent64(url, 15 * 1000, true, false);
	}

	public static Document retrieveHtmlDocumentAgent32(String url) throws IOException {
		return retrieveHtmlDocumentAgent32(url, 15 * 1000);
	}

	private static void setTrustAllCerts() {
		TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
			public java.security.cert.X509Certificate[] getAcceptedIssuers() {
				return null;
			}

			public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) {
			}

			public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) {
			}
		} };

		// install the all-trusting trust manager
		try {
			SSLContext sc = SSLContext.getInstance("SSL");
			sc.init(null, trustAllCerts, new java.security.SecureRandom());
			HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
			HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
				public boolean verify(String urlHostName, SSLSession session) {
					return true;
				}
			});
		} catch (Exception e) {
			// we can not recover from this exception.
			e.printStackTrace();
		}
	}

}
