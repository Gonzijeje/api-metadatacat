package com.tfg.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "GRUPO")
public class Group extends AbstractBasicoEntity{
	
	@OneToMany(mappedBy = "grupo")
	Set<GroupField> grupoCampos;
	
	public Group(String codigo, String descripcion) {
		super();
		this.codigo = codigo;
		this.descripcion = descripcion;
	}
	
	public Group() {	
	}

	public Set<GroupField> getGrupoCampos() {
		return grupoCampos;
	}

	public void setGrupoCampos(Set<GroupField> grupoCampos) {
		this.grupoCampos = grupoCampos;
	}
	
}
