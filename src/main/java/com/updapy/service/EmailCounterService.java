package com.updapy.service;

import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface EmailCounterService {

	boolean isEmailCounterReached(int nb);
	
	void incCounter();

}
