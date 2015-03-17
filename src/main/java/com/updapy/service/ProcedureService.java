package com.updapy.service;

import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface ProcedureService {

	int getNumberOfRowsInDatabase();

	boolean cleanDatabase();
	
	boolean analyzeAllTablesInDatabase();

}
