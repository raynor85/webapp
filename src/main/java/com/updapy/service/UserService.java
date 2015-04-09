package com.updapy.service;

import java.util.List;
import java.util.Locale;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.social.connect.Connection;
import org.springframework.transaction.annotation.Transactional;

import com.updapy.form.model.Notification;
import com.updapy.form.model.UpdateUrl;
import com.updapy.model.ApplicationFollow;
import com.updapy.model.ApplicationNotification;
import com.updapy.model.ApplicationReference;
import com.updapy.model.ApplicationVersion;
import com.updapy.model.User;
import com.updapy.model.enumeration.SocialMediaService;
import com.updapy.model.enumeration.TypeHelpMessage;
import com.updapy.model.stats.Follower;

@Transactional
public interface UserService {

	User getCurrentUserLight();

	User getUserLightFromRssKey(String key);

	User getCurrentUserWithSettings();

	User getCurrentUserWithApplicationFolloweds();

	User getCurrentUserWithHelpMessages();

	User getCurrentUserFull();

	User updateAccessDate(User user);

	void changeLocale(Locale locale);

	User findByEmail(String email);

	User findByAccountKey(String key);

	User register(User user);

	User registerSocial(Connection<?> connection);

	String generateNewAccountKey(User user);

	String generateNewApiKey(User user);

	User activate(User user);

	User updatePassword(User user, String newPassword);

	User updateEmail(User user, String newEmail);

	User save(User user);

	boolean isCurrentPassword(String password);

	boolean delete(User user);

	boolean isValidApiKey(String key);

	List<ApplicationFollow> getFollowedApplications(User user);

	List<ApplicationFollow> addFollowedApplications(User user, List<String> followedApiNames);

	boolean deleteFollowedApplication(User user, String apiName);

	boolean disableEmailAlertFollowedApplication(User user, String apiName);

	boolean enableEmailAlertFollowedApplication(User user, String apiName);

	List<ApplicationReference> getLeftApplications(User user);

	UpdateUrl getDownloadUrlMatchingSettings(User user, ApplicationVersion version);

	boolean isMessageDismissed(User user, TypeHelpMessage typeHelpMessage);

	User dismissMessage(User user, TypeHelpMessage typeHelpMessage);

	List<User> findUsersActive();

	List<User> findUsersFollowingNewsletters();

	List<User> findUsersFollowingApplication(ApplicationReference application);

	void notifyForNewVersion(User user, ApplicationVersion newVersion);

	@CacheEvict(value = "applications", key = "'applications.all'")
	void notifyForAddedApplication(User user, ApplicationReference addedApplication);

	@CacheEvict(value = "applications", key = "'applications.all'")
	void notifyForDeletedApplication(User user, ApplicationReference deletedApplication);

	Long getNbNotifications(User user);

	List<ApplicationNotification> getLastNbNotifications(User user, int nb);

	boolean markAsReadAllNotifications(User user);

	void addDownloadLinksToNotifications(List<Notification> notifications, User user);

	String getAvatarUrl(User user);

	Long getNumberOfUsers();

	Long getNumberOfSocialUsers(SocialMediaService socialMediaService);

	Long getNumberOfUsersInactive();

	List<Follower> getNbTopFollowers(int nb);

}
