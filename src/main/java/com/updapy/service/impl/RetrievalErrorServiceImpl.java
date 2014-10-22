package com.updapy.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.updapy.model.ApplicationReference;
import com.updapy.model.RetrievalError;
import com.updapy.model.enumeration.TypeRetrievalError;
import com.updapy.repository.RetrievalErrorRepository;
import com.updapy.service.EmailSenderService;
import com.updapy.service.RetrievalErrorService;

@Service
public class RetrievalErrorServiceImpl implements RetrievalErrorService {

	@Autowired
	private RetrievalErrorRepository retrievalErrorRepository;

	@Autowired
	private EmailSenderService emailSenderService;

	@Override
	public RetrievalError addRetrievalError(ApplicationReference application, TypeRetrievalError type) {
		RetrievalError retrievalError = findRetrievalErrors(application);
		if (retrievalError == null) {
			// first time there is an error on this app
			retrievalError = new RetrievalError();
			retrievalError.setApplication(application);
			retrievalError.setCount(1);
		} else {
			// not the first time, increment counter
			retrievalError.setCount(retrievalError.getCount() + 1);
		}
		retrievalError.setTypeLastError(type);
		retrievalErrorRepository.saveAndFlush(retrievalError);
		return retrievalError;
	}

	@Override
	public void deleteRetrievalError(ApplicationReference application) {
		RetrievalError retrievalError = findRetrievalErrors(application);
		if (retrievalError != null) {
			deleteRetrievalError(retrievalError);
		}
	}

	private void deleteRetrievalError(RetrievalError retrievalError) {
		retrievalErrorRepository.delete(retrievalError);
	}

	private RetrievalError findRetrievalErrors(ApplicationReference application) {
		return retrievalErrorRepository.findByApplication(application);
	}

	@Override
	public int sendEmailRetrievalErrors() {
		int count = 0;
		List<RetrievalError> retrievalErrors = retrievalErrorRepository.findByCountGreaterThanEqual(10); // 10 errors really mean there is a problem!
		for (RetrievalError retrievalError : retrievalErrors) {
			boolean hasBeenSent = false;
			if (TypeRetrievalError.URL_BASE_ERROR.equals(retrievalError.getTypeLastError())) {
				hasBeenSent = emailSenderService.sendAdminConnectionError(retrievalError.getApplication().getGlobalUrl());
			} else {
				hasBeenSent = emailSenderService.sendAdminRetrieverError(retrievalError.getApplication().getName());
			}
			if (hasBeenSent) {
				count++;
				deleteRetrievalError(retrievalError);
			}
		}
		return count;
	}

}
