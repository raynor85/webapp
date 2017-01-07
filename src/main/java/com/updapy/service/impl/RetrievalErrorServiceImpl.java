package com.updapy.service.impl;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.updapy.model.ApplicationReference;
import com.updapy.model.RetrievalError;
import com.updapy.model.enumeration.TypeRetrievalError;
import com.updapy.repository.RetrievalErrorRepository;
import com.updapy.service.EmailSenderService;
import com.updapy.service.RetrievalErrorService;

@Service
public class RetrievalErrorServiceImpl implements RetrievalErrorService {

	@Inject
	private RetrievalErrorRepository retrievalErrorRepository;

	@Inject
	private EmailSenderService emailSenderService;

	@Override
	public List<RetrievalError> getAllRetrievalErrors(int count) {
		return retrievalErrorRepository.findByCountGreaterThanEqualOrderByCountDesc(count);
	}

	@Override
	public RetrievalError addRetrievalError(ApplicationReference application, TypeRetrievalError type) {
		return addRetrievalError(application, type, null);
	}

	@Override
	public RetrievalError addRetrievalError(ApplicationReference application, TypeRetrievalError type, String message) {
		RetrievalError retrievalError = findRetrievalError(application, type);
		if (retrievalError == null) {
			// first time there is an error on this app
			retrievalError = new RetrievalError();
			retrievalError.setApplication(application);
			retrievalError.setTypeLastError(type);
			retrievalError.setMessage(message);
			retrievalError.setCount(1);
		} else {
			// refresh the message
			retrievalError.setMessage(message);
			// not the first time, increment counter
			retrievalError.setCount(retrievalError.getCount() + 1);
		}
		retrievalErrorRepository.saveAndFlush(retrievalError);
		return retrievalError;
	}

	@Override
	public void deleteRetrievalError(long id) {
		retrievalErrorRepository.delete(id);
	}

	@Override
	public void deleteRetrievalErrors(ApplicationReference application, List<TypeRetrievalError> types) {
		List<RetrievalError> retrievalErrors = findRetrievalErrors(application, types);
		if (retrievalErrors != null && !retrievalErrors.isEmpty()) {
			deleteRetrievalErrors(retrievalErrors);
		}
	}

	@Override
	public void deleteRetrievalErrors() {
		retrievalErrorRepository.deleteAll();
	}

	@Override
	public int sendEmailRetrievalErrors() {
		int count = 0;
		List<RetrievalError> retrievalErrors = retrievalErrorRepository.findByCountGreaterThanEqual(2); // 2 errors really mean there is a problem!
		retrievalErrors.addAll(retrievalErrorRepository.findByTypeLastErrorInAndCountGreaterThanEqual(Arrays.asList(TypeRetrievalError.LOCAL_URL_VERSION_ERROR, TypeRetrievalError.SAME_VERSION_DIFFERENT_URL), 3)); // 3 errors is enough in case of invalid URL
		for (RetrievalError retrievalError : retrievalErrors) {
			boolean isIgnored = RetrievalErrorIgnoredApplication.FULLY_IGNORED_APPLICATIONS.contains(retrievalError.getApplication().getApiName());
			if (!isIgnored || (isIgnored && retrievalError.getCount() >= 100)) {
				boolean hasBeenSent = false;
				if (TypeRetrievalError.REMOTE_URL_BASE_ERROR.equals(retrievalError.getTypeLastError())) {
					hasBeenSent = emailSenderService.sendAdminConnectionError(retrievalError.getApplication().getGlobalUrl());
				} else {
					hasBeenSent = emailSenderService.sendAdminRetrieverError(retrievalError.getApplication().getName(), retrievalError.getTypeLastError());
				}
				if (hasBeenSent) {
					count++;
					deleteRetrievalError(retrievalError);
				}
			}
		}
		return count;
	}

	private void deleteRetrievalErrors(List<RetrievalError> retrievalErrors) {
		retrievalErrorRepository.delete(retrievalErrors);
	}

	private void deleteRetrievalError(RetrievalError retrievalError) {
		retrievalErrorRepository.delete(retrievalError);
	}

	private RetrievalError findRetrievalError(ApplicationReference application, TypeRetrievalError type) {
		return retrievalErrorRepository.findByApplicationAndTypeLastError(application, type);
	}

	private List<RetrievalError> findRetrievalErrors(ApplicationReference application, List<TypeRetrievalError> types) {
		return retrievalErrorRepository.findByApplicationAndTypeLastErrorIn(application, types);
	}

}
