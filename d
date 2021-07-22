[1mdiff --git a/src/main/java/com/renanmateus/employeeregistration/domain/repository/FuncionarioRepository.java b/src/main/java/com/renanmateus/employeeregistration/domain/repository/FuncionarioRepository.java[m
[1mindex f1dca7c..09313cf 100644[m
[1m--- a/src/main/java/com/renanmateus/employeeregistration/domain/repository/FuncionarioRepository.java[m
[1m+++ b/src/main/java/com/renanmateus/employeeregistration/domain/repository/FuncionarioRepository.java[m
[36m@@ -1,6 +1,10 @@[m
 package com.renanmateus.employeeregistration.domain.repository;[m
 [m
[32m+[m[32mimport org.springframework.data.domain.Page;[m
[32m+[m[32mimport org.springframework.data.domain.Pageable;[m
 import org.springframework.data.jpa.repository.JpaRepository;[m
[32m+[m[32mimport org.springframework.data.jpa.repository.Query;[m
[32m+[m[32mimport org.springframework.data.repository.query.Param;[m
 import org.springframework.stereotype.Repository;[m
 [m
 import com.renanmateus.employeeregistration.domain.model.Funcao;[m
[36m@@ -11,7 +15,7 @@[m [mimport com.renanmateus.employeeregistration.domain.model.Funcionario;[m
 [m
 @Repository[m
 public interface FuncionarioRepository extends JpaRepository<Funcionario, Long>{[m
[31m-[m
[32m+[m	[32mPage<Funcionario> findByNomeCompletoLikeOrCpfLikeOrFuncaoNomeFuncaoLike(String nomeCompleto ,String cpf, String nomeFuncao, Pageable pageable);[m
 	Funcionario findAllByFuncao(String nomeFuncao);[m
 	long countByFuncao(Funcao funcao);[m
 [m
[1mdiff --git a/src/main/java/com/renanmateus/employeeregistration/domain/service/DataTablesService.java b/src/main/java/com/renanmateus/employeeregistration/domain/service/DataTablesService.java[m
[1mindex 6354e6f..722a1dd 100644[m
[1m--- a/src/main/java/com/renanmateus/employeeregistration/domain/service/DataTablesService.java[m
[1m+++ b/src/main/java/com/renanmateus/employeeregistration/domain/service/DataTablesService.java[m
[36m@@ -17,83 +17,88 @@[m [mimport com.renanmateus.employeeregistration.domain.repository.FuncionarioReposit[m
 public class DataTablesService {[m
 [m
 	// nomes devem ser iguais aos definidos no Modelo e no Datatables[m
[31m-	[m
[31m-	private String[] colunas = {[m
[31m-	"funcionarioid", "nomeCompleto", "cpf", "funcao.nomeFuncao"		[m
[31m-	};[m
 [m
[32m+[m	[32mprivate String[] colunas = { "funcionarioid", "nomeCompleto", "cpf", "funcao.nomeFuncao" };[m
 [m
[31m-public Map<String, Object> execute(FuncionarioRepository funcionarioRepository, HttpServletRequest request ) {[m
[31m-	[m
[31m-	[m
[31m-	// start = numero da primera linha da página (levando em consideração uma paginação de 10 itens por página)[m
[32m+[m	[32mpublic Map<String, Object> execute(FuncionarioRepository funcionarioRepository, HttpServletRequest request) {[m
[32m+[m
[32m+[m		[32m// start = numero da primera linha da página (levando em consideração uma[m
[32m+[m		[32m// paginação de 10 itens por página)[m
 		// ex: primeiro item da pagina 1 = 0[m
 		// ex: primeiro item da pagina 2 = 10[m
[31m-	// lenght = total de itens da página[m
[31m-	int start= Integer.parseInt(request.getParameter("start"));[m
[31m-	int length= Integer.parseInt(request.getParameter("length"));[m
[31m-	int draw= Integer.parseInt(request.getParameter("draw"));[m
[31m-	[m
[31m-	int paginaAtual = retornaPaginaAtual(start, length);[m
[31m-	[m
[31m-	// Definindo a coluna que ordena os dados[m
[31m-	String coluna = nomeColuna(request);[m
[31m-[m
[31m-	[m
[31m-	// Definindo a ordenação[m
[31m-	Sort.Direction direcao = ordenacao(request);[m
[31m-	[m
[31m-	// Definindo a paginação[m
[31m-	Pageable paginacao = PageRequest.of(paginaAtual, length, direcao, coluna);[m
[31m-	[m
[31m-	// Consulta ao banco de dados[m
[31m-	Page<Funcionario> pagina = queryBy(funcionarioRepository, paginacao);[m
[31m-	[m
[31m-	[m
[31m-	Map<String, Object> json = new LinkedHashMap<>();[m
[31m-	json.put("draw", draw );[m
[31m-	json.put("recordsTotal", pagina.getTotalElements());[m
[31m-	json.put("recordsFiltered", pagina.getTotalElements());[m
[31m-	[m
[31m-	//insere no json a lista de funcionarios[m
[31m-	// getContent() retorna uma lista[m
[31m-	json.put("data", pagina.getContent());[m
[31m-	[m
[31m-	return json;[m
[31m-}[m
[32m+[m		[32m// lenght = total de itens da página[m
[32m+[m		[32mint start = Integer.parseInt(request.getParameter("start"));[m
[32m+[m		[32mint length = Integer.parseInt(request.getParameter("length"));[m
[32m+[m		[32mint draw = Integer.parseInt(request.getParameter("draw"));[m
 [m
[31m-// retorno da consulta[m
[31m-private Page<Funcionario> queryBy(FuncionarioRepository funcionarioRepository, Pageable paginacao) {[m
[31m-	[m
[31m-	return funcionarioRepository.findAll(paginacao);[m
[31m-}[m
[32m+[m		[32mint paginaAtual = retornaPaginaAtual(start, length);[m
[32m+[m
[32m+[m		[32m// Definindo a coluna que ordena os dados[m
[32m+[m		[32mString coluna = nomeColuna(request);[m
[32m+[m
[32m+[m		[32m// Definindo a ordenação[m
[32m+[m		[32mSort.Direction direcao = ordenacao(request);[m
[32m+[m
[32m+[m		[32m// Definindo a busca[m
[32m+[m		[32mString busca = buscarPor(request);[m
[32m+[m
[32m+[m		[32m// Definindo a paginação[m
[32m+[m		[32mPageable paginacao = PageRequest.of(paginaAtual, length, direcao, coluna);[m
 [m
[32m+[m		[32m// Consulta ao banco de dados[m
[32m+[m		[32mPage<Funcionario> pagina = queryBy(busca, funcionarioRepository, paginacao);[m
 [m
[31m-private Direction ordenacao(HttpServletRequest request) {[m
[31m- String ordem = request.getParameter("order[0][dir]");[m
[31m- Sort.Direction sort = Sort.Direction.ASC;[m
[31m- if(ordem.equalsIgnoreCase("desc")) {[m
[31m-	 sort = Sort.Direction.DESC;[m
[31m-	 [m
[31m- }[m
[31m-	return sort;[m
[31m-}[m
[32m+[m		[32mMap<String, Object> json = new LinkedHashMap<>();[m
[32m+[m		[32mjson.put("draw", draw);[m
[32m+[m		[32mjson.put("recordsTotal", pagina.getTotalElements());[m
[32m+[m		[32mjson.put("recordsFiltered", pagina.getTotalElements());[m
[32m+[m
[32m+[m		[32m// insere no json a lista de funcionarios[m
[32m+[m		[32m// getContent() retorna uma lista[m
[32m+[m		[32mjson.put("data", pagina.getContent());[m
[32m+[m
[32m+[m		[32mreturn json;[m
[32m+[m	[32m}[m
[32m+[m
[32m+[m	[32mprivate String buscarPor(HttpServletRequest request) {[m
[32m+[m		[32mString busca = request.getParameter("search[value]");[m
[32m+[m		[32mif (busca.isEmpty()) {[m
[32m+[m			[32mreturn "";[m
[32m+[m		[32m}[m
[32m+[m		[32mreturn busca;[m
[32m+[m	[32m}[m
[32m+[m
[32m+[m[32m// retorno da consulta[m
[32m+[m	[32mprivate Page<Funcionario> queryBy(String busca, FuncionarioRepository funcionarioRepository, Pageable paginacao) {[m
 [m
[32m+[m		[32mif (busca.isEmpty()) {[m
[32m+[m			[32mreturn funcionarioRepository.findAll(paginacao);[m
 [m
[31m-private String nomeColuna(HttpServletRequest request) {[m
[31m-	// order[0] ordena a partir da posicao 0 da coluna, neste caso é o id.[m
[31m-	int posicaoColuna = Integer.parseInt(request.getParameter("order[0][column]"));[m
[31m-	return colunas[posicaoColuna];[m
[31m-}[m
[32m+[m		[32m}[m
[32m+[m[41m		[m
[32m+[m		[32mString b = "%"+busca+"%"; 	// "like" SQL (repository)[m
[32m+[m		[32mreturn funcionarioRepository.findByNomeCompletoLikeOrCpfLikeOrFuncaoNomeFuncaoLike(b, b, b, paginacao);[m
[32m+[m	[32m}[m
 [m
[32m+[m	[32mprivate Direction ordenacao(HttpServletRequest request) {[m
[32m+[m		[32mString ordem = request.getParameter("order[0][dir]");[m
[32m+[m		[32mSort.Direction sort = Sort.Direction.ASC;[m
[32m+[m		[32mif (ordem.equalsIgnoreCase("desc")) {[m
[32m+[m			[32msort = Sort.Direction.DESC;[m
 [m
[32m+[m		[32m}[m
[32m+[m		[32mreturn sort;[m
[32m+[m	[32m}[m
 [m
[32m+[m	[32mprivate String nomeColuna(HttpServletRequest request) {[m
[32m+[m		[32m// order[0] ordena a partir da posicao 0 da coluna, neste caso é o id.[m
[32m+[m		[32mint posicaoColuna = Integer.parseInt(request.getParameter("order[0][column]"));[m
[32m+[m		[32mreturn colunas[posicaoColuna];[m
[32m+[m	[32m}[m
 [m
[31m-private int retornaPaginaAtual(int start, int lenght) {[m
[31m-	[m
[31m-	[m
[31m-	return start / lenght;[m
[31m-}[m
[32m+[m	[32mprivate int retornaPaginaAtual(int start, int lenght) {[m
 [m
[32m+[m		[32mreturn start / lenght;[m
[32m+[m	[32m}[m
 [m
 }[m
\ No newline at end of file[m
[1mdiff --git a/src/main/java/com/renanmateus/employeeregistration/web/controller/FuncionarioController.java b/src/main/java/com/renanmateus/employeeregistration/web/controller/FuncionarioController.java[m
[1mindex 07484b1..10a3615 100644[m
[1m--- a/src/main/java/com/renanmateus/employeeregistration/web/controller/FuncionarioController.java[m
[1m+++ b/src/main/java/com/renanmateus/employeeregistration/web/controller/FuncionarioController.java[m
[36m@@ -44,7 +44,6 @@[m [mpublic class FuncionarioController {[m
 	@RequestMapping(value = { "/index", "/" }, method = RequestMethod.GET)[m
 	private String index(Funcionario funcionario, Model model) {[m
 [m
[31m-		funcionarioRepository.countByFuncao(funcionarioRepository.findAll().get(2).getFuncao());[m
 		long quantidadeAdm = funcionarioRepository.countByFuncao(funcaoRepository.findByNomeFuncao("Administrador"));[m
 		long quantidadeSup = funcionarioRepository.countByFuncao(funcaoRepository.findByNomeFuncao("Supervisor"));[m
 		long quantidadeFunc = funcionarioRepository.countByFuncao(funcaoRepository.findByNomeFuncao("Funcionario"));[m
[1mdiff --git a/src/main/resources/application.properties b/src/main/resources/application.properties[m
[1mindex 7592a3d..8f2ce53 100644[m
[1m--- a/src/main/resources/application.properties[m
[1m+++ b/src/main/resources/application.properties[m
[36m@@ -1,4 +1,4 @@[m
[31m-#MySQL[m
[32m+[m
 spring.datasource.url=[m
 spring.datasource.username=[m
 spring.datasource.password=[m
