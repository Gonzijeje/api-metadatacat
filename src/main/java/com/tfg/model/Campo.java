package com.tfg.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "CAMPO")
public class Campo extends AbstractBasicoEntity{

	/**
	 * Serializable
	 */
	private static final long serialVersionUID = -7711695016687954376L;
	
	@OneToMany(mappedBy = "campo")
	Set<Grupo_campo> grupoCampos;

	public Campo(String codigo, String descripcion) {
		super();
		super.codigo = codigo;
		this.descripcion = descripcion;
	}
	
	public Campo(String codigo, String descripcion, String createUser, Date createDate) {
		super();
		super.codigo=codigo;
		super.descripcion=descripcion;
		setCreateUser(createUser);
		setCreateDate(createDate);
	}
	
	public Campo() {	
	}
	
	public Set<Grupo_campo> getGrupoCampos() {
		return grupoCampos;
	}

	public void setGrupoCampos(Set<Grupo_campo> grupoCampos) {
		this.grupoCampos = grupoCampos;
	}

}
