package com.updapy.service;

public interface EmailCounterService {

	boolean isEmailCounterReached(int nb);
	
	void incCounter();

}
