package com.renanmateus.employeeregistration.domain.model;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.br.CPF;


import lombok.Data;
import lombok.EqualsAndHashCode;

@SuppressWarnings("serial")
@Entity
@Table(name = "funcionario")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Funcionario implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long funcionarioid;
	
	@Column(nullable = false, length = 255)
	@NotBlank(message=" É Obrigatorio.")
	private String nomeCompleto;

	@NotNull(message=" É Obrigatorio.")
	@Column(nullable = false, length = 255)
	@CPF(message=" CPF inválido")
	private String cpf;
	
	
	@NotNull(message=" É Obrigatorio.")
	@JoinColumn(name = "funcaoid", referencedColumnName = "funcaoid")
    @ManyToOne(optional = false)
   // @JsonBackReference
	private Funcao funcao;

	
}
