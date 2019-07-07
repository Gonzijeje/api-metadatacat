package com.tfg.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "FIELD")
public class Field extends AbstractBasicoEntity{
	
	@OneToMany(mappedBy = "campo")
	Set<GroupField> grupoCampos;

	public Field(String codigo, String descripcion) {
		super();
		this.codigo = codigo;
		this.descripcion = descripcion;
	}
	
	public Field() {	
	}
	
	public Set<GroupField> getGrupoCampos() {
		return grupoCampos;
	}

	public void setGrupoCampos(Set<GroupField> grupoCampos) {
		this.grupoCampos = grupoCampos;
	}

}
