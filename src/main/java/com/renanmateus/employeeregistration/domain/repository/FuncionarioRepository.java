package com.renanmateus.employeeregistration.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.renanmateus.employeeregistration.domain.model.Funcao;
import com.renanmateus.employeeregistration.domain.model.Funcionario;




@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, Long>{

	Funcionario findAllByFuncao(String nomeFuncao);
	long countByFuncao(Funcao funcao);


}
