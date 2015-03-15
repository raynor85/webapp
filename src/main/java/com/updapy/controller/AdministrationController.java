package com.updapy.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.updapy.form.ajax.JsonResponse;
import com.updapy.form.model.AdministrationRetrievalError;
import com.updapy.model.RetrievalError;
import com.updapy.model.User;
import com.updapy.service.ApplicationService;
import com.updapy.service.RetrievalErrorService;
import com.updapy.service.UserService;
import com.updapy.service.scheduler.ApplicationVersionScheduler;
import com.updapy.util.JsonResponseUtils;

@Controller
@RequestMapping("/administration")
public class AdministrationController {

	@Autowired
	private JsonResponseUtils jsonResponseUtils;

	@Autowired
	private ApplicationVersionScheduler applicationVersionScheduler;

	@Autowired
	private ApplicationService applicationService;

	@Autowired
	private RetrievalErrorService retrievalErrorService;

	@Autowired
	private UserService userService;

	@RequestMapping({ "/", "" })
	public ModelAndView administrationPage() {
		User user = userService.getCurrentUserWithSettings();
		if (user == null) {
			return new ModelAndView("welcome");
		}
		ModelAndView modelAndView = new ModelAndView("administration");
		modelAndView.addObject("nbNotifications", userService.getNbNotifications(user));
		List<RetrievalError> retrievalErrors = retrievalErrorService.getAllRetrievalErrors();
		modelAndView.addObject("administrationRetrievalErrors", convertToAdministrationRetrievalError(retrievalErrors));
		return modelAndView;
	}

	private List<AdministrationRetrievalError> convertToAdministrationRetrievalError(List<RetrievalError> retrievalErrors) {
		List<AdministrationRetrievalError> administrationRetrievalErrors = new ArrayList<AdministrationRetrievalError>();
		for (RetrievalError retrievalError : retrievalErrors) {
			AdministrationRetrievalError administrationRetrievalError = new AdministrationRetrievalError();
			administrationRetrievalError.setRetrievalError(retrievalError);
			administrationRetrievalError.setLatestVersion(applicationService.getLatestVersion(retrievalError.getApplication()));
			administrationRetrievalErrors.add(administrationRetrievalError);
		}
		return administrationRetrievalErrors;
	}

	@RequestMapping(value = "/clear/cache")
	public @ResponseBody
	JsonResponse clearCache() {
		applicationService.clearApplicationsCache();
		return jsonResponseUtils.buildSuccessfulJsonResponse("administration.action.cache.clear.confirm");
	}

	@RequestMapping(value = "/repository/update")
	public @ResponseBody
	JsonResponse updateRepository() {
		applicationVersionScheduler.updateApplicationRepositoryAndCreateEmailSingleUpdates();
		return jsonResponseUtils.buildSuccessfulJsonResponse("administration.action.repository.update.confirm");
	}

	@RequestMapping(value = "/repository/check")
	public @ResponseBody
	JsonResponse checkRepository() {
		applicationVersionScheduler.checkThatDownloadLinksAreValid();
		return jsonResponseUtils.buildSuccessfulJsonResponse("administration.action.repository.check.confirm");
	}

	@RequestMapping(value = "/email/application/added")
	public @ResponseBody
	JsonResponse createEmailsForAddedApplications() {
		applicationVersionScheduler.createEmailsAndNotificationsForAddedApplications();
		return jsonResponseUtils.buildSuccessfulJsonResponse("administration.action.email.application.added.confirm");
	}

	@RequestMapping(value = "/email/application/deleted")
	public @ResponseBody
	JsonResponse createEmailsForDeletedApplications() {
		applicationVersionScheduler.createEmailsAndNotificationsForDeletedApplications();
		return jsonResponseUtils.buildSuccessfulJsonResponse("administration.action.email.application.deleted.confirm");
	}

	@RequestMapping(value = "/email/newsletter")
	public @ResponseBody
	JsonResponse createEmailsNewsletters() {
		applicationVersionScheduler.createEmailsNewsletters();
		return jsonResponseUtils.buildSuccessfulJsonResponse("administration.action.email.newsletter.confirm");
	}

	@RequestMapping(value = "/email/send")
	public @ResponseBody
	JsonResponse sendEmails() {
		applicationVersionScheduler.sendEmails();
		return jsonResponseUtils.buildSuccessfulJsonResponse("administration.action.email.send.confirm");
	}

}
