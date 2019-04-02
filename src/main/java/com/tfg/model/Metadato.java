package com.tfg.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "METADATO")
public class Metadato extends AbstractBasicoEntity{

	/**
	 * Serializable
	 */
	private static final long serialVersionUID = -7711695016687954376L;

	public Metadato(String codigo, String descripcion) {
		super();
		super.codigo = codigo;
		this.descripcion = descripcion;
	}
	
	public Metadato() {
		
	}

}
