package com.updapy.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.updapy.model.EmailWeeklyUpdate;

public interface EmailWeeklyUpdateRepository extends JpaRepository<EmailWeeklyUpdate, Long> {

	List<EmailWeeklyUpdate> findBySentFalse(Pageable pageable);

}
