package com.renanmateus.employeeregistration.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.renanmateus.employeeregistration.domain.model.Funcao;

@Repository
public interface FuncaoRepository  extends JpaRepository<Funcao, Long>{

	Funcao findByNomeFuncao(String nomeFuncao);
	
	
}
