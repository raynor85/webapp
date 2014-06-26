package com.updapy.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.updapy.model.ApplicationReference;
import com.updapy.model.EmailSingleUpdate;
import com.updapy.model.User;

public interface EmailSingleUpdateRepository extends JpaRepository<EmailSingleUpdate, Long> {

	List<EmailSingleUpdate> findByUserAndVersionApplicationAndSentFalse(User user, ApplicationReference application);

	List<EmailSingleUpdate> findBySentFalse(Pageable pageable);

}
