package com.updapy.service.retriever;

import org.jsoup.nodes.Document;

import com.updapy.model.ApplicationReference;

public interface RemoteRetriever {

	boolean support(ApplicationReference applicationReference);

	String retrieveWin64UrlFr(Document doc);

	String retrieveWin64UrlEn(Document doc);

	String retrieveWin32UrlFr(Document doc);

	String retrieveWin32UrlEn(Document doc);

	String retrieveVersionNumber(Document doc);

}
