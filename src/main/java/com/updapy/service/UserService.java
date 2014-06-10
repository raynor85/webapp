package com.updapy.service;

import java.util.List;

import org.springframework.social.connect.Connection;
import org.springframework.transaction.annotation.Transactional;

import com.updapy.model.ApplicationFollow;
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

	List<ApplicationFollow> addApplicationsToFollow(User user, List<String> apiNamesToFollow);

	boolean deleteApplicationToFollow(User user, String apiName);

	boolean disableEmailAlertApplicationToFollow(User user, String apiName);

	boolean enableEmailAlertApplicationToFollow(User user, String apiName);

	List<ApplicationReference> getLeftApplications(User user);

	String getDownloadUrlMatchingSettings(User user, ApplicationVersion applicationVersion);

	boolean isMessageDismissed(User user, TypeHelpMessage typeHelpMessage);

	void dismissMessage(User user, TypeHelpMessage typeHelpMessage);

}
