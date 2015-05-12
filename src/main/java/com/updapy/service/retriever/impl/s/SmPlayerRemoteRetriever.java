package com.updapy.service.retriever.impl.s;

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
import com.updapy.service.retriever.RemoteRetriever;
import com.updapy.util.ParsingUtils;

@Component
public class SmPlayerRemoteRetriever implements RemoteRetriever {

	@Override
	public boolean support(ApplicationReference application) {
		return application.getApiName().equalsIgnoreCase("smplayer");
	}

	@Override
	public String retrieveWin64UrlFr(Document doc) throws IOException {
		return null;
	}

	@Override
	public String retrieveWin64UrlEn(Document doc) throws IOException {
		return matchingLink(doc.baseUri(), "^.*/(SMP|smp)layer/.*x64\\.exe.*$");
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
			if (link.matches(regex) && !link.matches(".*(smt|SMT)ube.*")) {
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
		return ParsingUtils.extractVersionNumberFromString(StringUtils.removePattern(StringUtils.removePattern(ParsingUtils.selectFromPattern(getDownloadLink32(doc), ".*exe"), "^.*/"), "qt.*"));
	}

	private String getDownloadLink32(Document doc) {
		return matchingLink(doc.baseUri(), "^.*/(SMP|smp)layer/.*win32\\.exe.*$");
	}

}
