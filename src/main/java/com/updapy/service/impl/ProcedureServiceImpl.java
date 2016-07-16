package com.updapy.service.impl;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.updapy.repository.ProcedureRepository;
import com.updapy.service.ProcedureService;

@Service
public class ProcedureServiceImpl implements ProcedureService {

	@Inject
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
