package com.updapy.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.updapy.model.AccountRemoval;

public interface AccountRemovalRepository extends JpaRepository<AccountRemoval, Long> {

	List<AccountRemoval> findByOrderByRemoveDateDesc(Pageable pageable);
}
