package com.updapy.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.updapy.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

	User findByEmail(String email);
}
