package com.updapy.service;

import java.util.List;

import com.updapy.model.ApplicationReference;
import com.updapy.model.RetrievalError;
import com.updapy.model.enumeration.TypeRetrievalError;

public interface RetrievalErrorService {

	RetrievalError addRetrievalError(ApplicationReference application, TypeRetrievalError type);

	void deleteRetrievalErrors(ApplicationReference application, List<TypeRetrievalError> types);

	int sendEmailRetrievalErrors();

}
