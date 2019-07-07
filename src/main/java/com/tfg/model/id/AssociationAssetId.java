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
public class AssociationAssetId implements Serializable{

	/**
	 * Serializable.
	 */
	private static final long serialVersionUID = -8539013321425846386L;
	
	@Column(name = "asset_id")
	private Long asset_id;
	@Column(name = "ac_group_id")
	private Long ac_group_id;
	@Column(name = "ac_field_id")
	private Long ac_field_id;
	@Column(name = "ac_value_id")
	private Long ac_value_id;
	
	public AssociationAssetId(Long asset_id, Long ac_grupo_id, Long ac_campo_id, Long ac_value_id) {
		super();
		this.asset_id = asset_id;
		this.ac_group_id = ac_grupo_id;
		this.ac_field_id = ac_campo_id;
		this.ac_value_id = ac_value_id;
	}
	
	public AssociationAssetId() {
		
	}

	public Long getAsset_id() {
		return asset_id;
	}

	public void setAsset_id(Long asset_id) {
		this.asset_id = asset_id;
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
		result = prime * result + ((asset_id == null) ? 0 : asset_id.hashCode());
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
		AssociationAssetId other = (AssociationAssetId) obj;
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
		if (asset_id == null) {
			if (other.asset_id != null)
				return false;
		} else if (!asset_id.equals(other.asset_id))
			return false;
		return true;
	}

}
