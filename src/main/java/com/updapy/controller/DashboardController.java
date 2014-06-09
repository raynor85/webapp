package com.updapy.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.updapy.form.ajax.JsonResponse;
import com.updapy.form.model.ChangeCurrentApplication;
import com.updapy.form.model.CurrentFollowApplication;
import com.updapy.form.model.DismissMessage;
import com.updapy.form.model.FollowNewApplications;
import com.updapy.model.ApplicationFollow;
import com.updapy.model.ApplicationVersion;
import com.updapy.model.enumeration.TypeHelpMessage;
import com.updapy.service.ApplicationService;
import com.updapy.service.SettingsService;
import com.updapy.service.UserService;
import com.updapy.util.DozerHelper;
import com.updapy.util.JsonResponseUtils;

@Controller
@RequestMapping("dashboard")
public class DashboardController {

	@Autowired
	private ApplicationService applicationService;

	@Autowired
	private UserService userService;

	@Autowired
	private SettingsService settingsService;

	@Autowired
	private DozerHelper dozerHelper;

	@Autowired
	private JsonResponseUtils jsonResponseUtils;

	@RequestMapping({ "/", "" })
	public ModelAndView dashboardPage() {
		return initModelAndView("dashboard");
	}

	@RequestMapping(value = "follow", method = RequestMethod.POST)
	public ModelAndView followApplications(FollowNewApplications followNewApplications) {
		userService.addApplicationsToFollow(followNewApplications.getApiNames());
		return initModelAndView("redirect:/dashboard");
	}

	@RequestMapping(value = "unfollow", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	JsonResponse update(@RequestBody ChangeCurrentApplication unfollowCurrentApplication) {
		boolean isDeleted = userService.deleteApplicationToFollow(unfollowCurrentApplication.getApiName());
		if (isDeleted) {
			return jsonResponseUtils.buildSuccessfulJsonResponse("dashboard.applications.unfollow.confirm");
		} else {
			return jsonResponseUtils.buildFailedJsonResponse("dashboard.applications.unfollow.error");
		}
	}

	@RequestMapping(value = "disable", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	JsonResponse disable(@RequestBody ChangeCurrentApplication disableCurrentApplication) {
		boolean isDone = userService.disableEmailAlertApplicationToFollow(disableCurrentApplication.getApiName());
		if (isDone) {
			return jsonResponseUtils.buildSuccessfulJsonResponse("dashboard.applications.alert.confirm");
		} else {
			return jsonResponseUtils.buildFailedJsonResponse("dashboard.applications.alert.error");
		}
	}

	@RequestMapping(value = "enable", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	JsonResponse enable(@RequestBody ChangeCurrentApplication enableCurrentApplication) {
		boolean isDone = userService.enableEmailAlertApplicationToFollow(enableCurrentApplication.getApiName());
		if (isDone) {
			return jsonResponseUtils.buildSuccessfulJsonResponse("dashboard.applications.alert.confirm");
		} else {
			return jsonResponseUtils.buildFailedJsonResponse("dashboard.applications.alert.error");
		}
	}

	@RequestMapping(value = "dismiss", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	JsonResponse dismiss(@RequestBody DismissMessage dismissMessage) {
		try {
			userService.dismissMessage(TypeHelpMessage.valueOf(dismissMessage.getTypeHelpMessage()));
		} catch (Exception e) {
			return jsonResponseUtils.buildFailedJsonResponse("dashboard.applications.dismiss.error");
		}
		return jsonResponseUtils.buildSuccessfulJsonResponse("dashboard.applications.dismiss.confirm");
	}

	private ModelAndView initModelAndView(String viewName) {
		ModelAndView modelAndView = new ModelAndView(viewName);
		modelAndView.addObject("newFollowApplications", new FollowNewApplications());
		modelAndView.addObject("leftApplications", userService.getLeftApplications());
		List<CurrentFollowApplication> currentFollowApplications = new ArrayList<CurrentFollowApplication>();
		for (ApplicationFollow applicationFollow : userService.getFollowedApplications()) {
			ApplicationVersion applicationVersion = applicationService.getLatestApplicationVersion(applicationFollow.getReferenceApp());
			CurrentFollowApplication currentFollowApplication = dozerHelper.map(applicationVersion, CurrentFollowApplication.class);
			currentFollowApplication.setDownloadUrl(userService.getDownloadUrlMatchingSettings(applicationVersion));
			currentFollowApplication.setEmailNotificationActive(applicationFollow.isEmailNotificationActive());
			currentFollowApplications.add(currentFollowApplication);
		}
		modelAndView.addObject("currentFollowApplications", currentFollowApplications);
		modelAndView.addObject("isDashboardHowToTipHidden", userService.isMessageDismissed(TypeHelpMessage.DASHBOARD_HOW_TO));
		modelAndView.addObject("isDashboardEmailDisableTipHidden", userService.isMessageDismissed(TypeHelpMessage.DASHBOARD_ALERT_DISABLED));
		modelAndView.addObject("isEmailDisabled", settingsService.isEmailDisabled());
		return modelAndView;
	}

}
