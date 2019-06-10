package com.tfg.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "GRUPO")
public class Grupo extends AbstractBasicoEntity{
	
	@OneToMany(mappedBy = "grupo")
	Set<Grupo_campo> grupoCampos;
	
	public Grupo(String codigo, String descripcion) {
		super();
		this.codigo = codigo;
		this.descripcion = descripcion;
	}
	
	public Grupo() {	
	}

	public Set<Grupo_campo> getGrupoCampos() {
		return grupoCampos;
	}

	public void setGrupoCampos(Set<Grupo_campo> grupoCampos) {
		this.grupoCampos = grupoCampos;
	}
	
}
