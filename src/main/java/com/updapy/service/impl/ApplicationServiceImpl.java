package com.updapy.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.updapy.model.ApplicationFollow;
import com.updapy.model.ApplicationNotification;
import com.updapy.model.ApplicationReference;
import com.updapy.model.ApplicationRequest;
import com.updapy.model.ApplicationVersion;
import com.updapy.model.User;
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
	public List<ApplicationReference> getApplications() {
		return applicationReferenceRepository.findByActiveTrueOrderByApiNameAsc();
	}

	@Override
	public ApplicationVersion getLatestVersion(String apiName) {
		ApplicationReference application = applicationReferenceRepository.findByApiNameAndActiveTrue(apiName);
		return getLatestVersion(application);
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
	public ApplicationReference getApplication(String apiName) {
		return applicationReferenceRepository.findByApiNameAndActiveTrue(apiName);
	}

	@Override
	public ApplicationFollow saveFollowedApplication(ApplicationFollow followedApplication) {
		return applicationFollowRepository.saveAndFlush(followedApplication);
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
	public ApplicationRequest saveRequestedApplication(ApplicationRequest requestedApplication) {
		return applicationRequestRepository.saveAndFlush(requestedApplication);
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
}
