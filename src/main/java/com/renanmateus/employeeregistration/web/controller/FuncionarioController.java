package com.renanmateus.employeeregistration.web.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.renanmateus.employeeregistration.domain.model.Funcao;
import com.renanmateus.employeeregistration.domain.model.Funcionario;
import com.renanmateus.employeeregistration.domain.model.FuncionarioDTO;
import com.renanmateus.employeeregistration.domain.repository.FuncaoRepository;
import com.renanmateus.employeeregistration.domain.repository.FuncionarioRepository;
import com.renanmateus.employeeregistration.domain.service.FuncionarioService;
import com.renanmateus.employeeregistration.domain.service.DataTablesService;

@Controller
public class FuncionarioController {

	@Autowired
	private FuncionarioRepository funcionarioRepository;
	@Autowired
	private FuncionarioService funcionarioService;

	@Autowired
	private FuncaoRepository funcaoRepository;

	@RequestMapping(value = { "/index", "/" }, method = RequestMethod.GET)
	private String index(Funcionario funcionario, Model model) {

		long quantidadeAdm = funcionarioRepository.countByFuncao(funcaoRepository.findByNomeFuncao("Administrador"));
		long quantidadeSup = funcionarioRepository.countByFuncao(funcaoRepository.findByNomeFuncao("Supervisor"));
		long quantidadeFunc = funcionarioRepository.countByFuncao(funcaoRepository.findByNomeFuncao("Funcionario"));
		model.addAttribute("lista", Arrays.asList(quantidadeAdm, quantidadeSup, quantidadeFunc));
		return "index";

	}

	@PostMapping("/salvar")
	public String salvar(@Valid Funcionario funcionario, BindingResult result) {
		if (result.hasErrors()) {
			return "index";
		}
		funcionarioService.salvar(funcionario);
		return "redirect:/index";
	}

	@RequestMapping(path = "/editar/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> editar(@PathVariable long id) {
		Funcionario funcionario = funcionarioRepository.findById(id).get();
		return ResponseEntity.ok(funcionario);
	}

	@PostMapping("/atualizar")
	public ResponseEntity<?> atualizar(@Valid FuncionarioDTO funcionarioDTO, BindingResult result) {
		System.out.println(funcionarioDTO.toString());
		if (result.hasErrors()) {
			Map<String, String> errors = new HashMap<>();
			for (FieldError error : result.getFieldErrors()) {
				errors.put(error.getField(), error.getDefaultMessage());
			}

			return ResponseEntity.unprocessableEntity().body(errors);
		}
		Funcionario funcionario = funcionarioRepository.findById(funcionarioDTO.getFuncionarioid()).get();
		funcionario.setNomeCompleto(funcionarioDTO.getNomeCompleto());
		funcionario.setCpf(funcionarioDTO.getCpf());
		funcionario.setFuncao(funcionarioDTO.getFuncao());
		funcionarioService.salvar(funcionario);

		return ResponseEntity.ok().build();
	}

	@RequestMapping(path = "/excluir/{id}", method = { RequestMethod.GET })
	public String deletar(@PathVariable long id) {
		funcionarioService.deletar(id);
		return "redirect:/index";
	}

	@ModelAttribute("funcao")
	public List<Funcao> getFuncoes() {
		return funcaoRepository.findAll();
	}

	// ========================================DATATABLES==============================================================

	@GetMapping("/funcionarios/lista")
	public ResponseEntity<?> dataTables(HttpServletRequest request) {
		Map<String, Object> data = new DataTablesService().execute(funcionarioRepository, request);
		return ResponseEntity.ok(data);

	}

}
