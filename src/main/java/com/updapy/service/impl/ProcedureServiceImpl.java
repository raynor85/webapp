package com.updapy.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.updapy.repository.ProcedureRepository;
import com.updapy.service.ProcedureService;

@Service
public class ProcedureServiceImpl implements ProcedureService {

	@Autowired
	private ProcedureRepository procedureRepository;

	@Override
	public int getNumberOfRowsInDatabase() {
		return procedureRepository.getNumberOfRowsDatabase();
	}

	@Override
	public boolean cleanDatabase() {
		return procedureRepository.cleanDatabase();
	}

	@Override
	public boolean analyzeAllTablesInDatabase() {
		return procedureRepository.analyzeDatabase();
	}

}
