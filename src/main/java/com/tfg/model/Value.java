package com.tfg.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Entity
@Table(name = "VALUE")
public class Value extends AbstractGeneratedIdEntity{
	
	@NotNull(message = "{valor.obligatorio}")
	@Size(max = 250, message = "{valor.longitud.maxima}")
	@Column(name = "value", nullable = false, length = 300)
	String valor;
	
	@OneToMany(mappedBy = "valor")
	Set<GroupField> grupoCampos;

	public Value(String valor) {
		super();
		this.valor = valor;
	}
	
	public Value() {	
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

}
