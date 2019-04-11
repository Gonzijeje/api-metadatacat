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
	@Column(name = "valor_id")
	private Long valor_id;
	
	public Grupo_Campo_Id(Long grupo_id, Long campo_id, Long valor_id) {
		super();
		this.grupo_id = grupo_id;
		this.campo_id = campo_id;
		this.valor_id = valor_id;
	}
	
	public Grupo_Campo_Id() {	
	}

	public Long getGrupo_id() {
		return grupo_id;
	}

	public void setGrupo_id(Long grupo_id) {
		this.grupo_id = grupo_id;
	}

	public Long getCampo_id() {
		return campo_id;
	}

	public void setCampo_id(Long campo_id) {
		this.campo_id = campo_id;
	}

	public Long getValor_id() {
		return valor_id;
	}

	public void setValor_id(Long valor_id) {
		this.valor_id = valor_id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((campo_id == null) ? 0 : campo_id.hashCode());
		result = prime * result + ((grupo_id == null) ? 0 : grupo_id.hashCode());
		result = prime * result + ((valor_id == null) ? 0 : valor_id.hashCode());
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
		if (campo_id == null) {
			if (other.campo_id != null)
				return false;
		} else if (!campo_id.equals(other.campo_id))
			return false;
		if (grupo_id == null) {
			if (other.grupo_id != null)
				return false;
		} else if (!grupo_id.equals(other.grupo_id))
			return false;
		if (valor_id == null) {
			if (other.valor_id != null)
				return false;
		} else if (!valor_id.equals(other.valor_id))
			return false;
		return true;
	}
	
}
