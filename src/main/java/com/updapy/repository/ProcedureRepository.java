package com.updapy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;

import com.updapy.model.User;

public interface ProcedureRepository extends JpaRepository<User, Long> {

	@Procedure
	int getNumberOfRowsDatabase();

	@Procedure
	boolean cleanDatabase();
	
	@Procedure
	boolean analyzeDatabase();

}
