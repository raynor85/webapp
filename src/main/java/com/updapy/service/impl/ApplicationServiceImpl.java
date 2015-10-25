package com.updapy.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.updapy.model.ApplicationDescription;
import com.updapy.model.ApplicationFollow;
import com.updapy.model.ApplicationNotification;
import com.updapy.model.ApplicationReference;
import com.updapy.model.ApplicationRequest;
import com.updapy.model.ApplicationVersion;
import com.updapy.model.User;
import com.updapy.model.stats.FollowedApplication;
import com.updapy.model.stats.Rating;
import com.updapy.repository.ApplicationDescriptionRepository;
import com.updapy.repository.ApplicationFollowRepository;
import com.updapy.repository.ApplicationNotificationRepository;
import com.updapy.repository.ApplicationReferenceRepository;
import com.updapy.repository.ApplicationRequestRepository;
import com.updapy.repository.ApplicationVersionRepository;
import com.updapy.service.ApplicationService;
import com.updapy.service.UserService;

@Service
public class ApplicationServiceImpl implements ApplicationService {

	@Autowired
	private ApplicationReferenceRepository applicationReferenceRepository;

	@Autowired
	private ApplicationDescriptionRepository applicationDescriptionRepository;

	@Autowired
	private ApplicationVersionRepository applicationVersionRepository;

	@Autowired
	private ApplicationFollowRepository applicationFollowRepository;

	@Autowired
	private ApplicationRequestRepository applicationRequestRepository;

	@Autowired
	private ApplicationNotificationRepository applicationNotificationRepository;

	@Autowired
	private UserService userService;

	@Override
	public void clearApplicationsCache() {
		// empty as the annotation is taking care of the work
	}

	@Override
	public Rating getAverageRating(String apiName) {
		Rating rating = applicationFollowRepository.findAverageRatingByApplication(apiName);
		rating.setAverageRating(roundToHalf(rating.getAverageRating()));
		return rating;
	}

	private Double roundToHalf(Double number) {
		if (number == null) {
			return null;
		}
		return 0.5 * Math.round(number * 2);
	}

	@Override
	public Long getNumberOfApplications() {
		return applicationReferenceRepository.count();
	}

	@Override
	public Long getNumberOfApplicationsActive() {
		return applicationReferenceRepository.countByActiveTrue();
	}

	@Override
	public Long getNumberOfApplicationsInactive() {
		return getNumberOfApplications() - getNumberOfApplicationsActive();
	}

	@Override
	public List<ApplicationReference> getApplications() {
		return applicationReferenceRepository.findByNotifiedTrueAndActiveTrueOrderByApiNameAsc();
	}

	@Override
	public List<ApplicationReference> getAllActiveApplications() {
		return applicationReferenceRepository.findByActiveTrue();
	}

	@Override
	public List<ApplicationReference> getAddedApplications() {
		return applicationReferenceRepository.findByNotifiedFalseAndActiveTrue();
	}

	@Override
	public List<ApplicationReference> getDeletedApplications() {
		return applicationReferenceRepository.findByNotifiedFalseAndActiveFalse();
	}

	@Override
	public List<ApplicationReference> getNbLatestApplications(int nb) {
		return applicationReferenceRepository.findByActiveTrueOrderByCreationDateDesc(new PageRequest(0, nb));
	}

	@Override
	public ApplicationVersion getLatestVersion(String apiName) {
		ApplicationReference application = getApplication(apiName);
		return getLatestVersion(application);
	}

	@Override
	public List<ApplicationDescription> getApplicationDescriptions() {
		return applicationDescriptionRepository.findByApplicationActiveTrueOrderByApplicationNameAsc();
	}

	@Override
	public ApplicationDescription getApplicationDescription(String apiName) {
		return applicationDescriptionRepository.findByApplicationApiNameAndApplicationActiveTrue(apiName);
	}

	@Override
	public ApplicationDescription getApplicationDescription(ApplicationReference application) {
		return applicationDescriptionRepository.findByApplicationAndApplicationActiveTrue(application);
	}

	@Override
	public ApplicationReference markAsNotified(ApplicationReference application) {
		application.setNotified(true);
		return applicationReferenceRepository.saveAndFlush(application);
	}

	@Override
	public ApplicationVersion addVersion(ApplicationVersion version) {
		ApplicationVersion versionSaved = applicationVersionRepository.saveAndFlush(version);
		notifyUsers(versionSaved);
		return versionSaved;
	}

	private void notifyUsers(ApplicationVersion newVersion) {
		List<User> userToNotifys = userService.findUsersFollowingApplication(newVersion.getApplication());
		for (User userToNotify : userToNotifys) {
			userService.notifyForNewVersion(userToNotify, newVersion);
		}
	}

	@Override
	public ApplicationVersion getLatestVersion(ApplicationReference application) {
		return applicationVersionRepository.findLatestByApplicationReference(application);
	}

	@Override
	public ApplicationVersion getLatestVersionNoCache(ApplicationReference application) {
		return getLatestVersion(application);
	}

	@Override
	public List<ApplicationVersion> getNewVersionsOnPeriod(Date from, Date to) {
		return new ArrayList<ApplicationVersion>(applicationVersionRepository.findByVersionDateBetweenOrderByVersionDateDesc(from, to));
	}

	@Override
	public ApplicationReference getApplication(String apiName) {
		return applicationReferenceRepository.findByApiNameAndNotifiedTrueAndActiveTrue(apiName);
	}

	@Override
	public ApplicationFollow saveFollowedApplication(ApplicationFollow followedApplication) {
		return applicationFollowRepository.saveAndFlush(followedApplication);
	}

	@Override
	public ApplicationFollow getFollowedApplication(User user, String apiName) {
		return applicationFollowRepository.findByUserAndApplicationApiName(user, apiName);
	}

	@Override
	public void deleteFollowedApplication(ApplicationFollow followedApplication) {
		applicationFollowRepository.delete(followedApplication);
	}

	@Override
	public ApplicationFollow enableEmailAlertFollowedApplication(ApplicationFollow followedApplication) {
		followedApplication.setEmailNotificationActive(true);
		return saveFollowedApplication(followedApplication);
	}

	@Override
	public ApplicationFollow disableEmailAlertFollowedApplication(ApplicationFollow followedApplication) {
		followedApplication.setEmailNotificationActive(false);
		return saveFollowedApplication(followedApplication);
	}

	@Override
	public List<FollowedApplication> getNbTopFollowedApplications(int nb) {
		return applicationFollowRepository.getTopFollowedApplications(new PageRequest(0, nb));
	}

	@Override
	public ApplicationRequest saveRequestedApplication(ApplicationRequest requestedApplication) {
		return applicationRequestRepository.saveAndFlush(requestedApplication);
	}

	@Override
	public List<ApplicationRequest> getNbLatestRequestedApplications(int nb) {
		return applicationRequestRepository.findByOrderByCreationDateDesc(new PageRequest(0, nb));
	}

	@Override
	public ApplicationNotification saveNotification(ApplicationNotification notification) {
		return applicationNotificationRepository.saveAndFlush(notification);
	}

	@Override
	public List<ApplicationNotification> getNotifications(User user, ApplicationReference application) {
		return applicationNotificationRepository.findByUserAndVersionApplication(user, application);
	}

	@Override
	public void deleteNotifications(List<ApplicationNotification> notifications) {
		applicationNotificationRepository.delete(notifications);
	}

	@Override
	public Long countNewNotifications(User user) {
		return applicationNotificationRepository.countByUserAndReadFalse(user);
	}

	@Override
	public List<ApplicationNotification> getNbLatestNotifications(User user, int nb) {
		List<ApplicationNotification> notifications = applicationNotificationRepository.findByUserOrderByCreationDateDesc(user, new PageRequest(0, nb));
		CollectionUtils.filter(notifications, new Predicate() {
			@Override
			public boolean evaluate(Object notification) {
				ApplicationNotification notif = (ApplicationNotification) notification;
				if (notif.getApplication() != null) {
					switch (notif.getType()) {
					case NOT_SUPPORTED_APPLICATION:
						return true;
					case NEW_APPLICATION:
						return notif.getApplication().isActive();
					default:
						return false;

					}
				}
				if (notif.getVersion() != null) {
					return notif.getVersion().getApplication().isActive();
				}
				return false;
			}
		});
		return notifications;
	}

	@Override
	public boolean markAsReadAllNotifications(User user) {
		List<ApplicationNotification> notifications = applicationNotificationRepository.findByUserAndReadFalse(user);
		for (ApplicationNotification notification : notifications) {
			notification.setRead(true);
			applicationNotificationRepository.saveAndFlush(notification);
		}
		return true;
	}

}
