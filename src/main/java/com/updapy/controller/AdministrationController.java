package com.updapy.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
import com.updapy.form.model.AddVersion;
import com.updapy.form.model.AdministrationRetrievalError;
import com.updapy.form.model.DeleteIgnoredApplication;
import com.updapy.form.model.DeleteRetrievalError;
import com.updapy.form.model.SendPersonalMessage;
import com.updapy.model.ApplicationReference;
import com.updapy.model.ApplicationVersion;
import com.updapy.model.RetrievalError;
import com.updapy.model.User;
import com.updapy.model.enumeration.Lang;
import com.updapy.model.enumeration.SocialMediaService;
import com.updapy.service.AccountRemovalService;
import com.updapy.service.ApplicationService;
import com.updapy.service.EmailSenderService;
import com.updapy.service.ProcedureService;
import com.updapy.service.RetrievalErrorService;
import com.updapy.service.SocialService;
import com.updapy.service.UserService;
import com.updapy.service.scheduler.ApplicationVersionScheduler;
import com.updapy.util.DozerHelper;
import com.updapy.util.JsonResponseUtils;
import com.updapy.util.MessageUtils;

@Controller
@RequestMapping("/administration")
public class AdministrationController {

	@Inject
	private JsonResponseUtils jsonResponseUtils;

	@Inject
	private DozerHelper dozerHelper;

	@Inject
	private ApplicationVersionScheduler applicationVersionScheduler;

	@Inject
	private ApplicationService applicationService;

	@Inject
	private SocialService twitterService;
	
	@Inject
	private SocialService facebookService;

	@Inject
	private RetrievalErrorService retrievalErrorService;

	@Inject
	private EmailSenderService emailSenderService;

	@Inject
	private UserService userService;

	@Inject
	private ProcedureService procedureService;

	@Inject
	private AccountRemovalService accountRemovalService;

	@Inject
	private MessageUtils messageUtils;

	@Inject
	private Validator addVersionCustomValidator;

	@InitBinder("addVersion")
	private void initBinderAddVersion(WebDataBinder binder) {
		binder.addValidators(addVersionCustomValidator);
	}

	@RequestMapping({ "/", "" })
	public ModelAndView administrationPage() {
		User user = userService.getCurrentUserWithSettings();
		if (user == null) {
			return new ModelAndView("welcome");
		}
		ModelAndView modelAndView = new ModelAndView("administration");
		modelAndView.addObject("nbNotifications", userService.getNbNotifications(user));
		modelAndView.addObject("addVersion", new AddVersion());
		List<RetrievalError> retrievalErrors = retrievalErrorService.getRetrievalErrors(2);
		modelAndView.addObject("administrationRetrievalErrors", convertToAdministrationRetrievalError(retrievalErrors));
		modelAndView.addObject("numberOfRowsInDatabase", procedureService.getNumberOfRowsInDatabase());
		List<ApplicationReference> ignoredApplications = applicationService.getIgnoredApplications();
		Collections.sort(ignoredApplications, new Comparator<ApplicationReference>() {
			@Override
			public int compare(ApplicationReference a1, ApplicationReference a2) {
				return a1.getApiName().compareTo(a2.getApiName());
			}
		});
		modelAndView.addObject("ignoredApplications", applicationService.getIgnoredApplications());
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

	@RequestMapping(value = "/version/add", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	JsonResponse addVersion(@Valid @RequestBody AddVersion addVersion, BindingResult result) {
		if (result.hasErrors()) {
			return jsonResponseUtils.buildFailedJsonResponseFromErrorObject(result.getAllErrors());
		} else {
			ApplicationVersion version = dozerHelper.map(addVersion, ApplicationVersion.class);
			version.setVersionDate(new Date());
			version.setApplication(applicationService.getApplication(addVersion.getApiNameFormatted()));
			// Add the version
			applicationService.addVersion(version);
			twitterService.sendStatusNewVersion(version);
			facebookService.sendStatusNewVersion(version);
			return jsonResponseUtils.buildSuccessfulJsonResponse("administration.version.add.confirm");
		}
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
		modelAndView.addObject("latestUserCreations", userService.getNbLatestUsers(5));
		modelAndView.addObject("latestAccountDeletions", accountRemovalService.getNbLatestAccountRemovals(5));
		modelAndView.addObject("latestApplicationRequests", applicationService.getNbLatestRequestedApplications(10));
		return addNotifications(user, modelAndView);
	}

	@RequestMapping(value = "/message")
	public ModelAndView personalMessagePage() {
		User user = userService.getCurrentUserLight();
		ModelAndView modelAndView = new ModelAndView("message");
		SendPersonalMessage sendPersonalMessage = new SendPersonalMessage();
		sendPersonalMessage.setLangEmail(Lang.valueOf(user.getLangEmail().toLanguageTag()));
		sendPersonalMessage.setMessage(messageUtils.getSimpleMessage("administration.message.field.message.sample"));
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
				return jsonResponseUtils.buildFailedJsonResponse("administration.message.send.error");
			}
			return jsonResponseUtils.buildSuccessfulJsonResponse("administration.message.send.confirm");
		}
	}

	@RequestMapping(value = "/retrievalError/delete", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	JsonResponse deleteRetrievalError(@RequestBody DeleteRetrievalError deleteRetrievalError) {
		retrievalErrorService.deleteRetrievalError(deleteRetrievalError.getRetrievalErrorId());
		return jsonResponseUtils.buildSuccessfulJsonResponse("administration.action.retrievalError.delete.confirm");
	}

	@RequestMapping(value = "/retrievalErrors/clear")
	public @ResponseBody
	JsonResponse clearRetrievalErrors() {
		retrievalErrorService.deleteRetrievalErrors();
		return jsonResponseUtils.buildSuccessfulJsonResponse("administration.action.retrievalErrors.clear.confirm");
	}

	@RequestMapping(value = "/ignoredApplication/delete", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	JsonResponse deleteIgnoredApplication(@RequestBody DeleteIgnoredApplication deleteIgnoredApplication) {
		applicationService.markAsIgnored(applicationService.getApplication(deleteIgnoredApplication.getIgnoredApplicationApiName()));
		return jsonResponseUtils.buildSuccessfulJsonResponse("administration.action.ignoredApplication.delete.confirm");
	}

	private ModelAndView addNotifications(User user, ModelAndView modelAndView) {
		if (user != null) {
			modelAndView.addObject("nbNotifications", userService.getNbNotifications(user));
			modelAndView.addObject("rssKey", user.getRssKey());
		}
		return modelAndView;
	}

}
