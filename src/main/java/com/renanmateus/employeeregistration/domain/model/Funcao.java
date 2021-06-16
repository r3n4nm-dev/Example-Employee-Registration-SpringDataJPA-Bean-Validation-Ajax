package com.renanmateus.employeeregistration.domain.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;

@SuppressWarnings("serial")
@Entity
@Table(name = "funcao")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Funcao implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long funcaoid;
	@Column
	private String nomeFuncao;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "funcionarioid",fetch=FetchType.LAZY) //puxar dados do banco (fetch) 
	@JsonIgnore
	//transient
	private transient List<Funcionario> funcionarios;
	
}
