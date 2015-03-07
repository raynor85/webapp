package com.updapy.util;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUtils {

	public static String getRedirectionUrl(String urlAsString) throws IOException {
		String urlRedirec = urlAsString;
		HttpURLConnection httpConn = (HttpURLConnection) (new URL(urlAsString).openConnection());
		httpConn.setInstanceFollowRedirects(false);
		httpConn.connect();
		int responseCode = httpConn.getResponseCode();
		if (responseCode >= 300 && responseCode < 400) {
			urlRedirec = httpConn.getHeaderField("Location");
		}
		httpConn.disconnect();
		return urlRedirec;
	}

	public static String getFilenameFromUrl(String urlAsString) throws IOException {
		String fileName = null;
		HttpURLConnection httpConn = (HttpURLConnection) (new URL(urlAsString).openConnection());
		int responseCode = httpConn.getResponseCode();
		// always check HTTP response code first
		if (responseCode == HttpURLConnection.HTTP_OK) {
			String disposition = httpConn.getHeaderField("Content-Disposition");
			if (disposition != null) {
				// extracts file name from header field
				int index = disposition.indexOf("filename=");
				if (index > 0) {
					fileName = disposition.substring(index + 10, disposition.length() - 1);
				}
			} else {
				// extracts file name from URL
				fileName = urlAsString.substring(urlAsString.lastIndexOf("/") + 1, urlAsString.length());
			}
		}
		httpConn.disconnect();
		return fileName;
	}
}