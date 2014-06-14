package com.updapy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.updapy.model.ApplicationNotification;
import com.updapy.model.ApplicationReference;
import com.updapy.model.User;

public interface ApplicationNotificationRepository extends JpaRepository<ApplicationNotification, Long> {

	List<ApplicationNotification> findByUserAndVersionApplication(User user, ApplicationReference application);

}
