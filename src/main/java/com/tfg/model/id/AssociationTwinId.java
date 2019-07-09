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
	
	private GroupFieldId groupField_id;
	
	public AssociationTwinId(Long twin_id, GroupFieldId groupField_id) {
		super();
		this.twin_id = twin_id;
		this.groupField_id = groupField_id;;
	}
	
	public AssociationTwinId() {
		
	}

	public Long getTwin_id() {
		return twin_id;
	}

	public void setTwin_id(Long asset_id) {
		this.twin_id = asset_id;
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
		result = prime * result + ((groupField_id == null) ? 0 : groupField_id.hashCode());
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
		if (groupField_id == null) {
			if (other.groupField_id != null)
				return false;
		} else if (!groupField_id.equals(other.groupField_id))
			return false;
		if (twin_id == null) {
			if (other.twin_id != null)
				return false;
		} else if (!twin_id.equals(other.twin_id))
			return false;
		return true;
	}

}
