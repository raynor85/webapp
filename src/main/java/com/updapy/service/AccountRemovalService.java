package com.updapy.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.updapy.model.AccountRemoval;

@Transactional
public interface AccountRemovalService {

	List<AccountRemoval> getNbLatestAccountRemovals(int nb);

	Long getNumberOfAccountDeletions();

}
