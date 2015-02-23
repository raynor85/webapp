package com.updapy.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.updapy.exception.UnauthorizedException;
import com.updapy.model.ApplicationNotification;
import com.updapy.model.ApplicationVersion;
import com.updapy.model.User;
import com.updapy.model.rss.RssNotification;
import com.updapy.service.UserService;
import com.updapy.service.rss.RssNotificationViewer;
import com.updapy.util.MessageUtils;

@Controller
@RequestMapping("/rss")
public class RssController {

	@Autowired
	private UserService userService;

	@Autowired
	private MessageUtils messageUtils;

	@RequestMapping("/notifications")
	public ModelAndView rssFeed(@RequestParam(value = "key", required = true) String key) {
		User user = userService.getUserLightFromRssKey(key);
		if (user == null) {
			throw new UnauthorizedException();
		}
		Locale locale = user.getLangEmail();
		List<RssNotification> rssNotifications = new ArrayList<RssNotification>();
		for (ApplicationNotification notification : userService.getLastNbNotifications(user, 50)) {
			RssNotification rssNotification = new RssNotification();
			rssNotification.setDate(notification.getCreationDate());
			String prefix = messageUtils.getSimpleMessage("rss." + notification.getType().name(), locale) + " - ";
			switch (notification.getType()) {
			case NEW_APPLICATION:
				rssNotification.setTitle(prefix + notification.getApplication().getName());
				rssNotification.setUrl(messageUtils.getSimpleMessage("application.root.url") + "/applications/" + notification.getApplication().getApiName());
				break;
			case NEW_VERSION:
				ApplicationVersion version = notification.getVersion();
				rssNotification.setTitle(prefix + version.getApplication().getName() + " " + version.getVersionNumber());
				rssNotification.setUrl(messageUtils.getSimpleMessage("application.root.url") + "/applications/" + version.getApplication().getApiName() + "/download?key=" + key);
				break;
			case NOT_SUPPORTED_APPLICATION:
				rssNotification.setTitle(prefix + notification.getApplication().getName());
				break;
			}
			rssNotifications.add(rssNotification);
		}

		return new ModelAndView(new RssNotificationViewer(messageUtils, locale), "notifications", rssNotifications);
	}

}
