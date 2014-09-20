package com.updapy.service.impl.retriever;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.updapy.model.ApplicationReference;
import com.updapy.service.retriever.RemoteRetriever;
import com.updapy.util.ParsingUtils;

@Component
public class SmPlayerRemoteRetriever implements RemoteRetriever {

	private static final String ROOT_DOWNLOAD_WEBSITE = "http://sourceforge.net/projects/smplayer/rss";

	@Override
	public boolean support(ApplicationReference application) {
		return application.getApiName().equalsIgnoreCase("smplayer");
	}

	@Override
	public String retrieveWin64UrlFr(Document doc) {
		return null;
	}

	@Override
	public String retrieveWin64UrlEn(Document doc) {
		return matchingLink("^.*/SMPlayer/.*x64\\.exe.*$");
	}

	@Override
	public String retrieveWin32UrlFr(Document doc) {
		return null;
	}

	@Override
	public String retrieveWin32UrlEn(Document doc) {
		return getDownloadLink32(doc);
	}

	private String matchingLink(String regex) {
		NodeList nodes = extractLinks();
		if (nodes == null) {
			return null;
		}
		for (int i = 0, len = nodes.getLength(); i < len; i++) {
			Node node = nodes.item(i);
			String link = node.getTextContent();
			if (link.matches(regex)) {
				return link;
			}
		}
		return null;
	}

	private NodeList extractLinks() {
		XPathFactory xpf = XPathFactory.newInstance();
		XPath xp = xpf.newXPath();
		InputSource is = new InputSource(ROOT_DOWNLOAD_WEBSITE);
		NodeList nodes = null;
		try {
			nodes = (NodeList) xp.evaluate("//link", is, XPathConstants.NODESET);
		} catch (XPathExpressionException e) {
			return null;
		}
		return nodes;
	}

	@Override
	public String retrieveVersionNumber(Document doc) {
		return ParsingUtils.extractVersionNumberFromString(StringUtils.removePattern(StringUtils.removePattern(getDownloadLink32(doc), "^.*/SMPlayer/"), "/smplayer.*$"));
	}

	private String getDownloadLink32(Document doc) {
		return matchingLink("^.*/SMPlayer/.*win32\\.exe.*$");
	}

}