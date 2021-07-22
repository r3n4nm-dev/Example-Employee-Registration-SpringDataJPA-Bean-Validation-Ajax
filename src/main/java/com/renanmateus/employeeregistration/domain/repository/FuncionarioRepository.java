package com.renanmateus.employeeregistration.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.renanmateus.employeeregistration.domain.model.Funcao;
import com.renanmateus.employeeregistration.domain.model.Funcionario;




@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, Long>{
	Page<Funcionario> findByNomeCompletoLikeOrCpfLikeOrFuncaoNomeFuncaoLike(String nomeCompleto ,String cpf, String nomeFuncao, Pageable pageable);
	Funcionario findAllByFuncao(String nomeFuncao);
	long countByFuncao(Funcao funcao);


}
