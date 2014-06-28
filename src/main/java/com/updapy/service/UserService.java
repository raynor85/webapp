package com.updapy.service;

import java.util.List;
import java.util.Locale;

import org.springframework.social.connect.Connection;
import org.springframework.transaction.annotation.Transactional;

import com.updapy.form.model.UpdateUrl;
import com.updapy.model.ApplicationFollow;
import com.updapy.model.ApplicationNotification;
import com.updapy.model.ApplicationReference;
import com.updapy.model.ApplicationVersion;
import com.updapy.model.User;
import com.updapy.model.enumeration.TypeHelpMessage;

@Transactional
public interface UserService {

	User getCurrentUserLight();

	User getCurrentUserWithSettings();

	User getCurrentUserWithApplicationFolloweds();

	User getCurrentUserWithHelpMessages();

	User getCurrentUserFull();

	void changeLocale(Locale locale);

	User findByEmail(String email);

	User registerEarly(String email);

	User register(User user);

	User registerSocial(Connection<?> connection);

	String generateNewAccountKey(User user);

	String generateNewApiKey(User user);

	User activate(User user);

	User updatePassword(User user, String newPassword);

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

	List<User> findUsersFollowingApplication(ApplicationReference application);

	void notifyForNewVersion(User user, ApplicationVersion newVersion);

	void notifyForNewApplication(User user, ApplicationReference newApplication);

	Long getNbNotifications(User user);

	List<ApplicationNotification> getLastNbNotifications(User user, int nb);

	boolean markAsReadAllNotifications(User user);

}
