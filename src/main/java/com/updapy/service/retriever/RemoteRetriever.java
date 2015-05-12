package com.updapy.service.retriever;

import java.io.IOException;

import org.jsoup.nodes.Document;

import com.updapy.model.ApplicationReference;

public interface RemoteRetriever {

	boolean support(ApplicationReference application);

	String retrieveWin64UrlFr(Document doc) throws IOException;

	String retrieveWin64UrlEn(Document doc) throws IOException;

	String retrieveWin32UrlFr(Document doc) throws IOException;

	String retrieveWin32UrlEn(Document doc) throws IOException;

	String retrieveVersionNumber(Document doc) throws IOException;

}
