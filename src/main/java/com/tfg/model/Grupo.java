package com.tfg.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "GRUPO")
public class Grupo extends AbstractBasicoEntity{

	/**
	 * Serializable
	 */
	private static final long serialVersionUID = 1078483709032718077L;
	
	@OneToMany(mappedBy = "grupo")
	Set<Grupo_campo> grupoCampos;
	
	public Grupo(String codigo, String descripcion) {
		super();
		super.codigo = codigo;
		super.descripcion = descripcion;
	}
	
	public Grupo(String codigo, String descripcion, String createuser, Date createdate) {
		super();
		super.codigo=codigo;
		super.descripcion=descripcion;
		setCreateUser(createuser);
		setCreateDate(createdate);
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
