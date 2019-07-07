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
public class AssociationTwinId implements Serializable{

	/**
	 * Serializable.
	 */
	private static final long serialVersionUID = -8539013321425846386L;
	
	@Column(name = "twin_id")
	private Long twin_id;
	@Column(name = "ac_group_id")
	private Long ac_group_id;
	@Column(name = "ac_field_id")
	private Long ac_field_id;
	@Column(name = "ac_value_id")
	private Long ac_value_id;
	
	public AssociationTwinId(Long twin_id, Long ac_grupo_id, Long ac_campo_id, Long ac_value_id) {
		super();
		this.twin_id = twin_id;
		this.ac_group_id = ac_grupo_id;
		this.ac_field_id = ac_campo_id;
		this.ac_value_id = ac_value_id;
	}
	
	public AssociationTwinId() {
		
	}

	public Long getTwin_id() {
		return twin_id;
	}

	public void setTwin_id(Long asset_id) {
		this.twin_id = asset_id;
	}

	public Long getAc_grupo_id() {
		return ac_group_id;
	}

	public void setAc_grupo_id(Long ac_grupo_id) {
		this.ac_group_id = ac_grupo_id;
	}

	public Long getAc_campo_id() {
		return ac_field_id;
	}

	public void setAc_campo_id(Long ac_campo_id) {
		this.ac_field_id = ac_campo_id;
	}

	public Long getAc_value_id() {
		return ac_value_id;
	}

	public void setAc_value_id(Long ac_value_id) {
		this.ac_value_id = ac_value_id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ac_field_id == null) ? 0 : ac_field_id.hashCode());
		result = prime * result + ((ac_group_id == null) ? 0 : ac_group_id.hashCode());
		result = prime * result + ((ac_value_id == null) ? 0 : ac_value_id.hashCode());
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
		AssociationTwinId other = (AssociationTwinId) obj;
		if (ac_field_id == null) {
			if (other.ac_field_id != null)
				return false;
		} else if (!ac_field_id.equals(other.ac_field_id))
			return false;
		if (ac_group_id == null) {
			if (other.ac_group_id != null)
				return false;
		} else if (!ac_group_id.equals(other.ac_group_id))
			return false;
		if (ac_value_id == null) {
			if (other.ac_value_id != null)
				return false;
		} else if (!ac_value_id.equals(other.ac_value_id))
			return false;
		if (twin_id == null) {
			if (other.twin_id != null)
				return false;
		} else if (!twin_id.equals(other.twin_id))
			return false;
		return true;
	}

}
