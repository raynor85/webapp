package com.updapy.service.retriever.impl.m;

import java.io.IOException;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.updapy.model.ApplicationReference;
import com.updapy.service.impl.RemoteServiceImpl;
import com.updapy.service.retriever.RemoteRetriever;
import com.updapy.util.ParsingUtils;

@Component
public class MediaPlayerClassicBeRemoteRetriever implements RemoteRetriever {

	private static final String DOWNLOAD_WEBSITE = "https://sourceforge.net/p/mpcbe/activity/feed";

	@Override
	public boolean support(ApplicationReference application) {
		return application.getApiName().equalsIgnoreCase("mediaplayerclassicbe");
	}

	@Override
	public String retrieveWin64UrlFr(Document doc) throws IOException {
		return null;
	}

	@Override
	public String retrieveWin64UrlEn(Document doc) throws IOException {
		return matchingLink(".*\\.x64-installer.*\\.zip.*");
	}

	@Override
	public String retrieveWin32UrlFr(Document doc) throws IOException {
		return null;
	}

	@Override
	public String retrieveWin32UrlEn(Document doc) throws IOException {
		return matchingLink(".*\\.x86-installer.*\\.zip.*");
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
		InputSource is = new InputSource(DOWNLOAD_WEBSITE);
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
		Elements items = doc.select("item").select("title:containsOwn(x86)");
		if (items.isEmpty()) {
			return RemoteServiceImpl.VERSION_NOT_FOUND;
		}
		return ParsingUtils.extractVersionNumberFromString(StringUtils.removePattern(StringUtils.removePattern(StringUtils.removePattern(items.select("title:containsOwn(/MPC-BE/)").first().text(), "^.*/MPC-BE").replace("build", "."), ".x...*$"), "/.*$"));
	}
}
