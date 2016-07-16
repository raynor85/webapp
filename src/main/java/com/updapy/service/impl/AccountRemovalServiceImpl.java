package com.updapy.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.updapy.model.AccountRemoval;
import com.updapy.repository.AccountRemovalRepository;
import com.updapy.service.AccountRemovalService;

@Service
public class AccountRemovalServiceImpl implements AccountRemovalService {

	@Inject
	private AccountRemovalRepository accountRemovalRepository;

	@Override
	public List<AccountRemoval> getNbLatestAccountRemovals(int nb) {
		return accountRemovalRepository.findByOrderByRemoveDateDesc(new PageRequest(0, nb));
	}

	@Override
	public Long getNumberOfAccountDeletions() {
		return accountRemovalRepository.count();
	}

}
