package com.updapy.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;
import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.updapy.form.ajax.JsonResponse;
import com.updapy.form.model.ChangeCurrentApplication;
import com.updapy.form.model.CurrentFollowedApplication;
import com.updapy.form.model.DismissMessage;
import com.updapy.form.model.FollowNewApplication;
import com.updapy.form.model.FollowNewApplications;
import com.updapy.form.model.Notification;
import com.updapy.form.model.RateApplication;
import com.updapy.form.model.RequestApplication;
import com.updapy.model.ApplicationFollow;
import com.updapy.model.ApplicationNotification;
import com.updapy.model.ApplicationRequest;
import com.updapy.model.ApplicationVersion;
import com.updapy.model.User;
import com.updapy.model.enumeration.TypeHelpMessage;
import com.updapy.service.ApplicationService;
import com.updapy.service.EmailSenderService;
import com.updapy.service.SettingsService;
import com.updapy.service.UserService;
import com.updapy.util.DozerHelper;
import com.updapy.util.JsonResponseUtils;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

	@Inject
	private ApplicationService applicationService;

	@Inject
	private Validator requestApplicationCustomValidator;

	@Inject
	private UserService userService;

	@Inject
	private SettingsService settingsService;

	@Inject
	private EmailSenderService emailSenderService;

	@Inject
	private DozerHelper dozerHelper;

	@Inject
	private JsonResponseUtils jsonResponseUtils;

	@InitBinder("requestApplication")
	private void initBinderChangePassword(WebDataBinder binder) {
		binder.addValidators(requestApplicationCustomValidator);
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.setAutoGrowCollectionLimit(1024);
	}

	@RequestMapping({ "/", "" })
	public ModelAndView dashboardPage() {
		User user = userService.getCurrentUserFull();
		if (user == null) {
			return new ModelAndView("welcome");
		}
		return initModelAndView(user, "dashboard");
	}

	@RequestMapping(value = "/follow", method = RequestMethod.POST)
	public ModelAndView followApplications(FollowNewApplications followNewApplications) {
		User user = userService.getCurrentUserWithApplicationFolloweds();
		userService.addFollowedApplications(user, followNewApplications.getApiNames());
		return initModelAndViewForRedirect(user, "redirect:/dashboard");
	}

	@RequestMapping(value = "/follow-single", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	JsonResponse followApplication(@Valid @RequestBody FollowNewApplication followNewApplication, BindingResult result) {
		if (result.hasErrors()) {
			return jsonResponseUtils.buildFailedJsonResponseFromErrorObject(result.getAllErrors());
		} else {
			User user = userService.getCurrentUserWithApplicationFolloweds();
			userService.addFollowedApplications(user, Arrays.asList(followNewApplication.getApiName()));
			return jsonResponseUtils.buildSuccessfulJsonResponse("dashboard.application.follow.confirm");
		}
	}

	@RequestMapping(value = "/unfollow", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	JsonResponse unfollowApplication(@RequestBody ChangeCurrentApplication unfollowCurrentApplication) {
		boolean isDeleted = userService.deleteFollowedApplication(userService.getCurrentUserWithApplicationFolloweds(), unfollowCurrentApplication.getApiName());
		if (isDeleted) {
			return jsonResponseUtils.buildSuccessfulJsonResponse("dashboard.applications.unfollow.confirm");
		} else {
			return jsonResponseUtils.buildFailedJsonResponse("dashboard.applications.unfollow.error");
		}
	}

	@RequestMapping(value = "/rate", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	JsonResponse rateApplication(@RequestBody RateApplication rateApplication) {
		boolean isDone = userService.rateApplication(userService.getCurrentUserLight(), rateApplication.getApiName(), rateApplication.getRating());
		if (isDone) {
			return jsonResponseUtils.buildSuccessfulJsonResponse("dashboard.applications.rating.confirm");
		} else {
			return jsonResponseUtils.buildFailedJsonResponse("dashboard.applications.rating.error");
		}
	}

	@RequestMapping(value = "/disable", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	JsonResponse disableEmailAlert(@RequestBody ChangeCurrentApplication disableCurrentApplication) {
		boolean isDone = userService.disableEmailAlertFollowedApplication(userService.getCurrentUserWithApplicationFolloweds(), disableCurrentApplication.getApiName());
		if (isDone) {
			return jsonResponseUtils.buildSuccessfulJsonResponse("dashboard.applications.alert.confirm");
		} else {
			return jsonResponseUtils.buildFailedJsonResponse("dashboard.applications.alert.error");
		}
	}

	@RequestMapping(value = "/enable", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	JsonResponse enableEmailAlert(@RequestBody ChangeCurrentApplication enableCurrentApplication) {
		boolean isDone = userService.enableEmailAlertFollowedApplication(userService.getCurrentUserWithApplicationFolloweds(), enableCurrentApplication.getApiName());
		if (isDone) {
			return jsonResponseUtils.buildSuccessfulJsonResponse("dashboard.applications.alert.confirm");
		} else {
			return jsonResponseUtils.buildFailedJsonResponse("dashboard.applications.alert.error");
		}
	}

	@RequestMapping(value = "/dismiss", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	JsonResponse dismissMessage(@RequestBody DismissMessage dismissMessage) {
		try {
			userService.dismissMessage(userService.getCurrentUserWithHelpMessages(), TypeHelpMessage.valueOf(dismissMessage.getTypeHelpMessage()));
		} catch (Exception e) {
			return jsonResponseUtils.buildFailedJsonResponse("dashboard.applications.dismiss.error");
		}
		return jsonResponseUtils.buildSuccessfulJsonResponse("dashboard.applications.dismiss.confirm");
	}

	@RequestMapping(value = "/notifications", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	List<Notification> readNotifications() {
		User user = userService.getCurrentUserLight();
		List<ApplicationNotification> applicationNotifications = userService.getLastNbNotifications(user, 10);
		List<Notification> notifications = dozerHelper.mapWithPropertyContext(applicationNotifications, Notification.class, "type");
		// populate version download links
		userService.addDownloadLinksToNotifications(notifications, user);
		userService.markAsReadAllNotifications(user);
		return notifications;
	}

	@RequestMapping(value = "/request", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	JsonResponse requestApplication(@Valid @RequestBody RequestApplication requestApplication, BindingResult result) {
		if (result.hasErrors()) {
			return jsonResponseUtils.buildFailedJsonResponseFromErrorObject(result.getAllErrors());
		} else {
			ApplicationRequest requestedApplication = dozerHelper.map(requestApplication, ApplicationRequest.class);
			User user = userService.getCurrentUserLight();
			requestedApplication.setUser(user);
			applicationService.saveRequestedApplication(requestedApplication);
			emailSenderService.sendAdminRequestedApplication(user.getEmail(), requestApplication.getName(), requestedApplication.getUrl(), user.getLangEmail());
			return jsonResponseUtils.buildSuccessfulJsonResponse("dashboard.applications.request.confirm");
		}
	}

	private ModelAndView initModelAndViewForRedirect(User user, String viewName) {
		ModelAndView modelAndView = new ModelAndView(viewName);
		return initModel(user, modelAndView);
	}

	private ModelAndView initModelAndView(User user, String viewName) {
		ModelAndView modelAndView = new ModelAndView(viewName);
		modelAndView.addObject("isDashboardHowToTipHidden", userService.isMessageDismissed(user, TypeHelpMessage.DASHBOARD_HOW_TO));
		modelAndView.addObject("isDashboardEmailDisableTipHidden", userService.isMessageDismissed(user, TypeHelpMessage.DASHBOARD_ALERT_DISABLED));
		modelAndView.addObject("isEmailDisabled", settingsService.isEmailDisabled(user));
		modelAndView.addObject("isEmailOnEachUpdateDisabled", !settingsService.isEmailOnEachUpdateActive(user));
		modelAndView.addObject("nbNotifications", userService.getNbNotifications(user));
		modelAndView.addObject("rssKey", user.getRssKey());
		modelAndView.addObject("dashboardGridSize", user.getDashboardGridSize());
		modelAndView.addObject("showRating", user.getShowRating());
		return initModel(user, modelAndView);
	}

	private ModelAndView initModel(User user, ModelAndView modelAndView) {
		modelAndView.addObject("newFollowedApplications", new FollowNewApplications());
		modelAndView.addObject("requestApplication", new RequestApplication());
		modelAndView.addObject("leftApplications", userService.getLeftApplications(user));
		modelAndView.addObject("latestApplications", applicationService.getNbLatestApplications(4));
		List<CurrentFollowedApplication> currentFollowedApplications = new ArrayList<CurrentFollowedApplication>();
		for (ApplicationFollow followedApplication : userService.getFollowedApplications(user)) {
			ApplicationVersion version = applicationService.getLatestVersion(followedApplication.getApplication());
			if (version == null) {
				// no version yet
				continue;
			}
			CurrentFollowedApplication currentFollowedApplication = dozerHelper.map(version, CurrentFollowedApplication.class);
			currentFollowedApplication.setDownloadUrl(userService.getDownloadUrlMatchingSettings(user, version).getUrl());
			currentFollowedApplication.setEmailNotificationActive(followedApplication.isEmailNotificationActive());
			currentFollowedApplication.setRating(followedApplication.getRating());
			currentFollowedApplication.setAverageRating(applicationService.getAverageRating(currentFollowedApplication.getApiName()));
			currentFollowedApplications.add(currentFollowedApplication);
		}
		modelAndView.addObject("currentFollowedApplications", currentFollowedApplications);
		return modelAndView;
	}

}
