package com.renanmateus.employeeregistration.domain.service;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import com.renanmateus.employeeregistration.domain.model.Funcionario;
import com.renanmateus.employeeregistration.domain.repository.FuncionarioRepository;

public class DataTablesService {

	// nomes devem ser iguais aos definidos no Modelo e no Datatables
	
	private String[] colunas = {
	"funcionarioid", "nomeCompleto", "cpf", "funcao.nomeFuncao"		
	};


public Map<String, Object> execute(FuncionarioRepository funcionarioRepository, HttpServletRequest request ) {
	
	
	// start = numero da primera linha da página (levando em consideração uma paginação de 10 itens por página)
		// ex: primeiro item da pagina 1 = 0
		// ex: primeiro item da pagina 2 = 10
	// lenght = total de itens da página
	int start= Integer.parseInt(request.getParameter("start"));
	int length= Integer.parseInt(request.getParameter("length"));
	int draw= Integer.parseInt(request.getParameter("draw"));
	
	int paginaAtual = retornaPaginaAtual(start, length);
	
	// Definindo a coluna que ordena os dados
	String coluna = nomeColuna(request);

	
	// Definindo a ordenação
	Sort.Direction direcao = ordenacao(request);
	
	// Definindo a paginação
	Pageable paginacao = PageRequest.of(paginaAtual, length, direcao, coluna);
	
	// Consulta ao banco de dados
	Page<Funcionario> pagina = queryBy(funcionarioRepository, paginacao);
	
	
	Map<String, Object> json = new LinkedHashMap<>();
	json.put("draw", draw );
	json.put("recordsTotal", pagina.getTotalElements());
	json.put("recordsFiltered", pagina.getTotalElements());
	
	//insere no json a lista de funcionarios
	// getContent() retorna uma lista
	json.put("data", pagina.getContent());
	
	return json;
}

// retorno da consulta
private Page<Funcionario> queryBy(FuncionarioRepository funcionarioRepository, Pageable paginacao) {
	
	return funcionarioRepository.findAll(paginacao);
}


private Direction ordenacao(HttpServletRequest request) {
 String ordem = request.getParameter("order[0][dir]");
 Sort.Direction sort = Sort.Direction.ASC;
 if(ordem.equalsIgnoreCase("desc")) {
	 sort = Sort.Direction.DESC;
	 
 }
	return sort;
}


private String nomeColuna(HttpServletRequest request) {
	// order[0] ordena a partir da posicao 0 da coluna, neste caso é o id.
	int posicaoColuna = Integer.parseInt(request.getParameter("order[0][column]"));
	return colunas[posicaoColuna];
}




private int retornaPaginaAtual(int start, int lenght) {
	
	
	return start / lenght;
}


}