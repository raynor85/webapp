package com.updapy.service.retriever.impl.l;

import java.io.IOException;

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
import com.updapy.service.impl.RemoteServiceImpl;
import com.updapy.service.retriever.RemoteRetriever;
import com.updapy.util.ParsingUtils;

@Component
public class LightFirefoxRemoteRetriever implements RemoteRetriever {

	@Override
	public boolean support(ApplicationReference application) {
		return application.getApiName().equalsIgnoreCase("lightfirefox");
	}

	@Override
	public String retrieveWin64UrlFr(Document doc) throws IOException {
		return null;
	}

	@Override
	public String retrieveWin64UrlEn(Document doc) throws IOException {
		return matchingLink(doc.baseUri(), "^.*lightfirefox.*/\\d+(rc|)/.*win64.*\\.exe/.*$");
	}

	@Override
	public String retrieveWin32UrlFr(Document doc) throws IOException {
		return null;
	}

	@Override
	public String retrieveWin32UrlEn(Document doc) throws IOException {
		return getDownloadLink32(doc);
	}

	private String matchingLink(String url, String regex) {
		NodeList nodes = extractLinks(url);
		if (nodes == null) {
			return null;
		}
		for (int i = 0, len = nodes.getLength(); i < len; i++) {
			Node node = nodes.item(i);
			String link = node.getTextContent();
			if (link.matches(regex) && !link.matches(".*test.*")) {
				return link;
			}
		}
		return null;
	}

	private NodeList extractLinks(String url) {
		XPathFactory xpf = XPathFactory.newInstance();
		XPath xp = xpf.newXPath();
		InputSource is = new InputSource(url);
		NodeList nodes = null;
		try {
			nodes = (NodeList) xp.evaluate("//link", is, XPathConstants.NODESET);
		} catch (XPathExpressionException e) {
			return null;
		}
		return nodes;
	}

	@Override
	public String retrieveVersionNumber(Document doc) throws IOException {
		String downloadLink = getDownloadLink32(doc);
		if (downloadLink == null) {
			return RemoteServiceImpl.VERSION_NOT_FOUND;
		}
		return ParsingUtils.extractVersionNumberFromString(StringUtils.removePattern(StringUtils.removePattern(ParsingUtils.selectFromPattern(downloadLink, ".*exe"), "^.*/"), "win.*"));
	}

	private String getDownloadLink32(Document doc) {
		return matchingLink(doc.baseUri(), "^.*lightfirefox.*/\\d+(rc|)/.*win32.*\\.exe/.*$");
	}

}
