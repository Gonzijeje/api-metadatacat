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
public class Ac_Twin_Id implements Serializable{

	/**
	 * Serializable.
	 */
	private static final long serialVersionUID = -8539013321425846386L;
	
	@Column(name = "twin_id")
	private Long twin_id;
	@Column(name = "ac_grupo_id")
	private Long ac_grupo_id;
	@Column(name = "ac_campo_id")
	private Long ac_campo_id;
	
	public Ac_Twin_Id(Long twin_id, Long ac_grupo_id, Long ac_campo_id) {
		super();
		this.twin_id = twin_id;
		this.ac_grupo_id = ac_grupo_id;
		this.ac_campo_id = ac_campo_id;
	}
	
	public Ac_Twin_Id() {
		
	}

	public Long getTwin_id() {
		return twin_id;
	}

	public void setTwin_id(Long asset_id) {
		this.twin_id = asset_id;
	}

	public Long getAc_grupo_id() {
		return ac_grupo_id;
	}

	public void setAc_grupo_id(Long ac_grupo_id) {
		this.ac_grupo_id = ac_grupo_id;
	}

	public Long getAc_campo_id() {
		return ac_campo_id;
	}

	public void setAc_campo_id(Long ac_campo_id) {
		this.ac_campo_id = ac_campo_id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ac_campo_id == null) ? 0 : ac_campo_id.hashCode());
		result = prime * result + ((ac_grupo_id == null) ? 0 : ac_grupo_id.hashCode());
		result = prime * result + ((twin_id == null) ? 0 : twin_id.hashCode());
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
		Ac_Twin_Id other = (Ac_Twin_Id) obj;
		if (ac_campo_id == null) {
			if (other.ac_campo_id != null)
				return false;
		} else if (!ac_campo_id.equals(other.ac_campo_id))
			return false;
		if (ac_grupo_id == null) {
			if (other.ac_grupo_id != null)
				return false;
		} else if (!ac_grupo_id.equals(other.ac_grupo_id))
			return false;
		if (twin_id == null) {
			if (other.twin_id != null)
				return false;
		} else if (!twin_id.equals(other.twin_id))
			return false;
		return true;
	}

}
