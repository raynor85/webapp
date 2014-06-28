package com.updapy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.updapy.model.ApplicationReference;
import com.updapy.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

	User findByEmail(String email);

	User findByApiKey(String key);

	List<User> findByActiveTrue();

	List<User> findByFollowedApplicationsApplication(ApplicationReference application);

}
