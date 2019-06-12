package com.tfg.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Entity
@Table(name = "VALOR_CAMPO")
public class Valor_Campo extends AbstractGeneratedIdEntity{
	
	@NotNull(message = "{valor.obligatorio}")
	@Size(max = 40, message = "{valor.longitud.maxima}")
	@Column(name = "VALOR", nullable = false, length = 45)
	String valor;
	
	@OneToMany(mappedBy = "valor")
	Set<Grupo_campo> grupoCampos;

	public Valor_Campo(String valor) {
		super();
		this.valor = valor;
	}
	
	public Valor_Campo() {	
	}

	public Object getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

}
