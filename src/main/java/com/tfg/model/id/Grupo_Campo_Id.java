package com.tfg.model.id;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;


/**
 * 
 * @author gcollada
 *
 */
@Embeddable
public class Grupo_Campo_Id implements Serializable{

	/**
	 * Serializable.
	 */
	private static final long serialVersionUID = -2465217422644085827L;
	
	@Column(name = "grupo_id")
	private Long grupo_id;
	@Column(name = "campo_id")
	private Long campo_id;
	
	public Grupo_Campo_Id(Long grupo, Long metadato) {
		super();
		this.grupo_id = grupo;
		this.campo_id = metadato;
	}
	
	public Grupo_Campo_Id() {
		
	}

	public Long getGrupo() {
		return grupo_id;
	}

	public void setGrupo(Long grupo) {
		this.grupo_id = grupo;
	}

	public Long getMetadato() {
		return campo_id;
	}

	public void setMetadato(Long metadato) {
		this.campo_id = metadato;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((grupo_id == null) ? 0 : grupo_id.hashCode());
		result = prime * result + ((campo_id == null) ? 0 : campo_id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Grupo_Campo_Id other = (Grupo_Campo_Id) obj;
		if (grupo_id == null) {
			if (other.grupo_id != null)
				return false;
		} else if (!grupo_id.equals(other.grupo_id))
			return false;
		if (campo_id == null) {
			if (other.campo_id != null)
				return false;
		} else if (!campo_id.equals(other.campo_id))
			return false;
		return true;
	}	

}
