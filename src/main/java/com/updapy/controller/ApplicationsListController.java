package com.updapy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.updapy.exception.ResourceNotFoundException;
import com.updapy.exception.UnauthorizedException;
import com.updapy.form.model.UpdateUrl;
import com.updapy.model.ApplicationDescription;
import com.updapy.model.ApplicationReference;
import com.updapy.model.ApplicationVersion;
import com.updapy.model.User;
import com.updapy.model.enumeration.ApplicationCategory;
import com.updapy.model.enumeration.ApplicationType;
import com.updapy.service.ApplicationService;
import com.updapy.service.UserService;

@Controller
@RequestMapping("/applications")
public class ApplicationsListController {

	@Autowired
	private UserService userService;

	@Autowired
	private ApplicationService applicationService;

	@RequestMapping({ "/", "" })
	public ModelAndView getApplicationsListPage() {
		ModelAndView modelAndView = new ModelAndView("apps-list");
		modelAndView.addObject("applicationDescriptions", applicationService.getApplicationDescriptions());
		modelAndView.addObject("applicationCategories", ApplicationCategory.values());
		modelAndView.addObject("applicationTypes", ApplicationType.values());
		return addNotifications(modelAndView);
	}

	@RequestMapping(value = "/{apiName}")
	public ModelAndView getApplicationDetail(@PathVariable String apiName) {
		ApplicationDescription applicationDescription = applicationService.getApplicationDescription(apiName);
		if (applicationDescription == null) {
			throw new ResourceNotFoundException();
		}
		ModelAndView modelAndView = new ModelAndView("app-detail");
		modelAndView.addObject("applicationDescription", applicationDescription);
		return addNotifications(modelAndView);
	}

	@RequestMapping(value = "/{apiName}/download")
	public ModelAndView getApplicationDownloadLink(@PathVariable String apiName, @RequestParam(value = "key", required = true) String key) {
		User user = userService.getUserLightFromRssKey(key);
		if (user == null) {
			throw new UnauthorizedException();
		}
		ApplicationReference application = applicationService.getApplication(apiName);
		ApplicationDescription applicationDescription = applicationService.getApplicationDescription(application);
		if (applicationDescription == null) {
			throw new ResourceNotFoundException();
		}
		ModelAndView modelAndView = new ModelAndView("app-download");
		modelAndView.addObject("applicationDescription", applicationDescription);
		ApplicationVersion latestVersion = applicationService.getLatestVersion(application);
		UpdateUrl defaultUpdateUrl = userService.getDownloadUrlMatchingSettings(user, latestVersion);
		modelAndView.addObject("mainDownloadLink", defaultUpdateUrl.getUrl());
		modelAndView.addObject("otherDownloadLinks", userService.getOtherDownloadUrls(defaultUpdateUrl, latestVersion));
		modelAndView.addObject("versionNumber", latestVersion.getVersionNumber());
		return addNotifications(modelAndView, user);
	}

	private ModelAndView addNotifications(ModelAndView modelAndView) {
		User user = userService.getCurrentUserLight();
		return addNotifications(modelAndView, user);
	}

	private ModelAndView addNotifications(ModelAndView modelAndView, User user) {
		if (user != null) {
			modelAndView.addObject("nbNotifications", userService.getNbNotifications(user));
			modelAndView.addObject("rssKey", user.getRssKey());
		}
		return modelAndView;
	}
}
