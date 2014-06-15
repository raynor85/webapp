package com.updapy.service;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;

import com.updapy.model.ApplicationFollow;
import com.updapy.model.ApplicationNotification;
import com.updapy.model.ApplicationReference;
import com.updapy.model.ApplicationRequest;
import com.updapy.model.ApplicationVersion;
import com.updapy.model.User;

@Transactional
public interface ApplicationService {

	/**
	 * Application References
	 */

	@Cacheable(value = "applications", key = "'applications.all'")
	List<ApplicationReference> getApplications();

	@Cacheable(value = "applications", key = "{'applications', #apiName}")
	ApplicationReference getApplication(String apiName);

	/**
	 * Application Versions
	 */

	@CacheEvict(value = "versions", key = "{'versions', #version.application.apiName}")
	ApplicationVersion addVersion(ApplicationVersion version);

	@Cacheable(value = "versions", key = "{'versions', #apiName}")
	ApplicationVersion getLatestVersion(String apiName);

	@Cacheable(value = "versions", key = "{'versions', #application.apiName}")
	ApplicationVersion getLatestVersion(ApplicationReference application);

	ApplicationVersion getLatestVersionNoCache(ApplicationReference application);

	/**
	 * Application follow
	 */

	ApplicationFollow saveFollowedApplication(ApplicationFollow followedApplication);

	void deleteFollowedApplication(ApplicationFollow followedApplication);

	ApplicationFollow enableEmailAlertFollowedApplication(ApplicationFollow followedApplication);

	ApplicationFollow disableEmailAlertFollowedApplication(ApplicationFollow followedApplication);

	/**
	 * Application Request
	 */

	ApplicationRequest saveRequestedApplication(ApplicationRequest requestedApplication);

	/**
	 * Application notification
	 */

	ApplicationNotification saveNotification(ApplicationNotification notification);

	void deleteNotifications(List<ApplicationNotification> notifications);

	List<ApplicationNotification> getNotifications(User user, ApplicationReference application);

	Long countNewNotifications(User user);

	List<ApplicationNotification> getNbLastNotifications(User user, int nb);

	boolean markAsReadAllNotifications(User user);

}
