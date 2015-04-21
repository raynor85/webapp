package com.updapy.service.retriever.impl.a;

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
public class AudacityRemoteRetriever implements RemoteRetriever {

	@Override
	public boolean support(ApplicationReference application) {
		return application.getApiName().equalsIgnoreCase("audacity");
	}

	@Override
	public String retrieveWin64UrlFr(Document doc) {
		return null;
	}

	@Override
	public String retrieveWin64UrlEn(Document doc) {
		return null;
	}

	@Override
	public String retrieveWin32UrlFr(Document doc) {
		return null;
	}

	@Override
	public String retrieveWin32UrlEn(Document doc) {
		return getDownloadLink(doc.location());
	}

	@Override
	public String retrieveVersionNumber(Document doc) {
		return ParsingUtils.extractVersionNumberFromString(StringUtils.removePattern(getDownloadLink(doc.location()), "^.*/audacity"));
	}

	private String getDownloadLink(String pageLink) {
		return matchingLink(pageLink, ".*audacity-win.*.exe.*");
	}

	private String matchingLink(String pageLink, String regex) {
		NodeList nodes = extractLinks(pageLink);
		if (nodes == null) {
			return null;
		}
		for (int i = 0, len = nodes.getLength(); i < len; i++) {
			Node node = nodes.item(i);
			String link = node.getTextContent();
			if (link.matches(regex) && !link.matches(".*rc.{0,3}.exe.*")) {
				return link;
			}
		}
		return null;
	}

	private NodeList extractLinks(String pageLink) {
		XPathFactory xpf = XPathFactory.newInstance();
		XPath xp = xpf.newXPath();
		InputSource is = new InputSource(pageLink);
		NodeList nodes = null;
		try {
			nodes = (NodeList) xp.evaluate("//link", is, XPathConstants.NODESET);
		} catch (XPathExpressionException e) {
			return null;
		}
		return nodes;
	}

}
