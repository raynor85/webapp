package com.updapy.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.updapy.form.ajax.JsonResponse;
import com.updapy.form.model.AdministrationRetrievalError;
import com.updapy.form.model.DeleteRetrievalError;
import com.updapy.form.model.SendPersonalMessage;
import com.updapy.model.RetrievalError;
import com.updapy.model.User;
import com.updapy.model.enumeration.Lang;
import com.updapy.model.enumeration.SocialMediaService;
import com.updapy.service.AccountRemovalService;
import com.updapy.service.ApplicationService;
import com.updapy.service.EmailSenderService;
import com.updapy.service.ProcedureService;
import com.updapy.service.RetrievalErrorService;
import com.updapy.service.UserService;
import com.updapy.service.scheduler.ApplicationVersionScheduler;
import com.updapy.util.JsonResponseUtils;
import com.updapy.util.MessageUtils;

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
	private EmailSenderService emailSenderService;

	@Autowired
	private UserService userService;

	@Autowired
	private ProcedureService procedureService;

	@Autowired
	private AccountRemovalService accountRemovalService;

	@Autowired
	private MessageUtils messageUtils;

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
		modelAndView.addObject("numberOfRowsInDatabase", procedureService.getNumberOfRowsInDatabase());
		return addNotifications(user, modelAndView);
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

	@RequestMapping(value = "/cache/clear")
	public @ResponseBody
	JsonResponse clearCache() {
		applicationService.clearApplicationsCache();
		return jsonResponseUtils.buildSuccessfulJsonResponse("administration.action.cache.clear.confirm");
	}

	@RequestMapping(value = "/database/cleanup")
	public @ResponseBody
	JsonResponse cleanupDatabase() {
		procedureService.cleanDatabase();
		procedureService.analyzeAllTablesInDatabase();
		return jsonResponseUtils.buildSuccessfulJsonResponse("administration.action.database.cleanup.confirm");
	}

	@RequestMapping(value = "/numberOfRowsInDatabase/get")
	public @ResponseBody
	int getNumberOfRowsInDatabase() {
		return procedureService.getNumberOfRowsInDatabase();
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

	@RequestMapping(value = "/stats")
	public ModelAndView showStats() {
		User user = userService.getCurrentUserLight();
		ModelAndView modelAndView = new ModelAndView("stats");
		modelAndView.addObject("numberOfApplications", applicationService.getNumberOfApplications());
		modelAndView.addObject("numberOfApplicationsInactive", applicationService.getNumberOfApplicationsInactive());
		modelAndView.addObject("numberOfUsers", userService.getNumberOfUsers());
		Long numberOfFacebookSocialUsers = userService.getNumberOfSocialUsers(SocialMediaService.FACEBOOK);
		modelAndView.addObject("numberOfFacebookSocialUsers", numberOfFacebookSocialUsers);
		Long numberOfLinkedInSocialUsers = userService.getNumberOfSocialUsers(SocialMediaService.LINKEDIN);
		modelAndView.addObject("numberOfLinkedInSocialUsers", numberOfLinkedInSocialUsers);
		Long numberOfGoogleSocialUsers = userService.getNumberOfSocialUsers(SocialMediaService.GOOGLE);
		modelAndView.addObject("numberOfGoogleSocialUsers", numberOfGoogleSocialUsers);
		Long numberOfTwitterSocialUsers = userService.getNumberOfSocialUsers(SocialMediaService.TWITTER);
		modelAndView.addObject("numberOfTwitterSocialUsers", numberOfTwitterSocialUsers);
		modelAndView.addObject("numberOfSocialUsers", numberOfFacebookSocialUsers + numberOfGoogleSocialUsers + numberOfLinkedInSocialUsers + numberOfTwitterSocialUsers);
		modelAndView.addObject("numberOfUsersInactive", userService.getNumberOfUsersInactive());
		modelAndView.addObject("numberOfAccountDeletions", accountRemovalService.getNumberOfAccountDeletions());
		modelAndView.addObject("topFollowedApplications", applicationService.getNbTopFollowedApplications(25));
		modelAndView.addObject("topFollowers", userService.getNbTopFollowers(10));
		modelAndView.addObject("latestAccountDeletions", accountRemovalService.getLatestNbAccountRemovals(5));
		return addNotifications(user, modelAndView);
	}

	@RequestMapping(value = "/message")
	public ModelAndView personalMessagePage() {
		User user = userService.getCurrentUserLight();
		ModelAndView modelAndView = new ModelAndView("message");
		SendPersonalMessage sendPersonalMessage = new SendPersonalMessage();
		sendPersonalMessage.setLangEmail(Lang.valueOf(user.getLangEmail().toLanguageTag()));
		sendPersonalMessage.setMessage(messageUtils.getSimpleMessage("message.field.message.sample"));
		modelAndView.addObject("sendPersonalMessage", sendPersonalMessage);
		return addNotifications(user, modelAndView);
	}

	@RequestMapping(value = "/message/send", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	JsonResponse sendPersonalMessage(@Valid @RequestBody SendPersonalMessage sendPersonalMessage, BindingResult result) {
		if (result.hasErrors()) {
			return jsonResponseUtils.buildFailedJsonResponseFromErrorObject(result.getAllErrors());
		} else {
			// Send the message via email
			boolean isSent = emailSenderService.sendPersonalEmail(sendPersonalMessage.getEmail(), sendPersonalMessage.getSubject(), sendPersonalMessage.getTitle(), sendPersonalMessage.getMessage(), new Locale(sendPersonalMessage.getLangEmail().name()));
			if (!isSent) {
				return jsonResponseUtils.buildFailedJsonResponse("message.send.error");
			}
			return jsonResponseUtils.buildSuccessfulJsonResponse("message.send.confirm");
		}
	}

	@RequestMapping(value = "/retrievalError/delete", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	JsonResponse deleteRetrievalError(@RequestBody DeleteRetrievalError deleteRetrievalError) {
		retrievalErrorService.deleteRetrievalError(deleteRetrievalError.getRetrievalErrorId());
		return jsonResponseUtils.buildSuccessfulJsonResponse("administration.action.retrievalError.delete.confirm");
	}

	private ModelAndView addNotifications(User user, ModelAndView modelAndView) {
		if (user != null) {
			modelAndView.addObject("nbNotifications", userService.getNbNotifications(user));
			modelAndView.addObject("rssKey", user.getRssKey());
		}
		return modelAndView;
	}

}
