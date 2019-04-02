package com.tfg.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "GRUPO_METADATO")
public class Grupo extends AbstractBasicoEntity{

	/**
	 * Serializable
	 */
	private static final long serialVersionUID = 1078483709032718077L;
	
	public Grupo(String codigo, String descripcion) {
		super();
		super.codigo = codigo;
		this.descripcion = descripcion;
	}
	
	public Grupo() {
		
	}
	
}
