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

	User getCurrentUser();

	User findByEmail(String email);

	User registerEarly(String email);

	User register(User user);

	User registerSocial(Connection<?> connection);

	String generateNewAccountKey(User user);

	String generateCurrentNewApiKey();

	User activate(User user);

	User updatePassword(User user, String newPassword);

	User updateCurrentPassword(String newPassword);

	User save(User user);

	boolean isCurrentPassword(String password);

	boolean delete(User user);

	boolean deleteCurrentUser();

	boolean isValidApiKey(String key);

	List<ApplicationFollow> getFollowedApplications();

	List<ApplicationFollow> addApplicationsToFollow(List<String> apiNamesToFollow);

	boolean deleteApplicationToFollow(String apiName);

	boolean disableEmailAlertApplicationToFollow(String apiName);

	boolean enableEmailAlertApplicationToFollow(String apiName);

	List<ApplicationReference> getLeftApplications();

	String getDownloadUrlMatchingSettings(ApplicationVersion applicationVersion);

	boolean isMessageDismissed(TypeHelpMessage typeHelpMessage);

	void dismissMessage(TypeHelpMessage typeHelpMessage);

}
