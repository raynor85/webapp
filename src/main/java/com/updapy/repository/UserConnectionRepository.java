package com.updapy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.updapy.model.UserConnection;

public interface UserConnectionRepository extends JpaRepository<UserConnection, Long> {

	List<UserConnection> findByUserConnectionIdUserId(String userId);
}
