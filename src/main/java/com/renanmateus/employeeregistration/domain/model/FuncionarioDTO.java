package com.renanmateus.employeeregistration.domain.model;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.br.CPF;


import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class FuncionarioDTO {
	
	@NotNull
	private long funcionarioid;
	
	@NotBlank(message=" É Obrigatorio.")
	private String nomeCompleto;

	@NotNull(message=" É Obrigatorio.")
	@CPF(message=" CPF inválido")
	private String cpf;
	
	@NotNull(message=" É Obrigatorio.")
	private Funcao funcao;

	@Override
	public String toString() {
		return "FuncionarioDTO [funcionarioid=" + funcionarioid + ", nomeCompleto=" + nomeCompleto + ", cpf=" + cpf
				+ ", funcao=" + funcao + "]";
	}

	
}
