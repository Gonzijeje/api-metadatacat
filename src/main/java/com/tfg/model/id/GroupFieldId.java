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
public class GroupFieldId implements Serializable{

	/**
	 * Serializable.
	 */
	private static final long serialVersionUID = -2465217422644085827L;
	
	@Column(name = "group_id")
	private Long group_id;
	@Column(name = "field_id")
	private Long field_id;
	@Column(name = "value_id")
	private Long value_id;
	
	public GroupFieldId(Long grupo_id, Long campo_id, Long valor_id) {
		super();
		this.group_id = grupo_id;
		this.field_id = campo_id;
		this.value_id = valor_id;
	}
	
	public GroupFieldId() {	
	}

	public Long getGrupo_id() {
		return group_id;
	}

	public void setGrupo_id(Long grupo_id) {
		this.group_id = grupo_id;
	}

	public Long getCampo_id() {
		return field_id;
	}

	public void setCampo_id(Long campo_id) {
		this.field_id = campo_id;
	}

	public Long getValor_id() {
		return value_id;
	}

	public void setValor_id(Long valor_id) {
		this.value_id = valor_id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((field_id == null) ? 0 : field_id.hashCode());
		result = prime * result + ((group_id == null) ? 0 : group_id.hashCode());
		result = prime * result + ((value_id == null) ? 0 : value_id.hashCode());
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
		GroupFieldId other = (GroupFieldId) obj;
		if (field_id == null) {
			if (other.field_id != null)
				return false;
		} else if (!field_id.equals(other.field_id))
			return false;
		if (group_id == null) {
			if (other.group_id != null)
				return false;
		} else if (!group_id.equals(other.group_id))
			return false;
		if (value_id == null) {
			if (other.value_id != null)
				return false;
		} else if (!value_id.equals(other.value_id))
			return false;
		return true;
	}
	
}
