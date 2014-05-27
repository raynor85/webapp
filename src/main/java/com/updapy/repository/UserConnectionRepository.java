package com.updapy.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.updapy.model.UserConnection;

public interface UserConnectionRepository extends JpaRepository<UserConnection, Long> {

	UserConnection findByUserConnectionIdUserId(String userId);
}
