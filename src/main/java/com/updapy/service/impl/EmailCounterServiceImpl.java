package com.updapy.service.impl;

import javax.inject.Inject;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.springframework.stereotype.Service;

import com.updapy.model.EmailCounter;
import com.updapy.repository.EmailCounterRepository;
import com.updapy.service.EmailCounterService;

@Service
public class EmailCounterServiceImpl implements EmailCounterService {

	@Inject
	private EmailCounterRepository emailCounterRepository;

	@Override
	public boolean isEmailCounterReached(int nb) {
		EmailCounter emailCounter = getEmailCounter();
		DateTime today = new LocalDate().toDateTimeAtStartOfDay();
		if (isSameDay(today, emailCounter) && emailCounter.getCount() >= nb) {
			return true;
		}
		return false;
	}

	@Override
	public void incCounter() {
		EmailCounter emailCounter = getEmailCounter();
		DateTime today = new LocalDate().toDateTimeAtStartOfDay();
		if (isSameDay(today, emailCounter)) {
			// increment
			emailCounter.setCount(emailCounter.getCount() + 1);
		} else {
			// a new day
			emailCounter.setDate(today.toDate());
			emailCounter.setCount(1);
		}
		emailCounterRepository.saveAndFlush(emailCounter);
	}

	private boolean isSameDay(DateTime date, EmailCounter emailCounter) {
		DateTime lastCountDate = new LocalDate(emailCounter.getDate()).toDateTimeAtStartOfDay();
		return lastCountDate.equals(date);
	}

	private EmailCounter getEmailCounter() {
		return emailCounterRepository.findOne(1L);
	}
}
