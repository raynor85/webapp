package com.updapy.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.updapy.model.EmailNewsletter;

public interface EmailNewsletterRepository extends JpaRepository<EmailNewsletter, Long> {

	List<EmailNewsletter> findBySentFalse(Pageable pageable);

}
