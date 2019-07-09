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
	
	private GroupFieldId groupField_id;
	
	public AssociationAssetId(Long asset_id, GroupFieldId groupField_id) {
		super();
		this.asset_id = asset_id;
		this.groupField_id = groupField_id;
	}
	
	public AssociationAssetId() {
		
	}

	public Long getAsset_id() {
		return asset_id;
	}

	public void setAsset_id(Long asset_id) {
		this.asset_id = asset_id;
	}

	public GroupFieldId getGroupField_id() {
		return groupField_id;
	}

	public void setGroupField_id(GroupFieldId groupField_id) {
		this.groupField_id = groupField_id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((asset_id == null) ? 0 : asset_id.hashCode());
		result = prime * result + ((groupField_id == null) ? 0 : groupField_id.hashCode());
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
		if (asset_id == null) {
			if (other.asset_id != null)
				return false;
		} else if (!asset_id.equals(other.asset_id))
			return false;
		if (groupField_id == null) {
			if (other.groupField_id != null)
				return false;
		} else if (!groupField_id.equals(other.groupField_id))
			return false;
		return true;
	}

}
