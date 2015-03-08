package com.updapy.service;

import com.updapy.model.ApplicationReference;
import com.updapy.model.RetrievalError;
import com.updapy.model.enumeration.TypeRetrievalError;

public interface RetrievalErrorService {

	RetrievalError addRetrievalError(ApplicationReference application, TypeRetrievalError type);

	void deleteRetrievalError(ApplicationReference application, TypeRetrievalError type);

	int sendEmailRetrievalErrors();

}
