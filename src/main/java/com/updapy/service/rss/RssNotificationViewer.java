package com.updapy.service.rss;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.feed.AbstractRssFeedView;

import com.rometools.rome.feed.rss.Channel;
import com.rometools.rome.feed.rss.Content;
import com.rometools.rome.feed.rss.Item;
import com.updapy.model.rss.RssNotification;
import com.updapy.util.MessageUtils;

public class RssNotificationViewer extends AbstractRssFeedView {

	private MessageUtils messageUtils;

	private Locale locale;

	public RssNotificationViewer(MessageUtils messageUtils, Locale locale) {
		this.messageUtils = messageUtils;
		this.locale = locale;
	}

	@Override
	protected void buildFeedMetadata(Map<String, Object> model, Channel feed, HttpServletRequest request) {
		feed.setTitle(messageUtils.getSimpleMessage("application.name", locale));
		feed.setDescription(messageUtils.getSimpleMessage("rss.application.description", locale));
		feed.setLink(messageUtils.getSimpleMessage("application.root.url", locale));
		super.buildFeedMetadata(model, feed, request);
	}

	@Override
	@SuppressWarnings("unchecked")
	protected List<Item> buildFeedItems(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<RssNotification> rssNotifications = (List<RssNotification>) model.get("notifications");
		if (rssNotifications == null || rssNotifications.isEmpty()) {
			return new ArrayList<Item>(0);
		}
		List<Item> items = new ArrayList<Item>(rssNotifications.size());
		for (RssNotification rssNotification : rssNotifications) {
			Item item = new Item();
			Content content = new Content();
			content.setValue(rssNotification.getSummary());
			item.setContent(content);
			item.setTitle(rssNotification.getTitle());
			item.setLink(rssNotification.getUrl());
			item.setPubDate(rssNotification.getDate());
			items.add(item);
		}
		return items;
	}

}
