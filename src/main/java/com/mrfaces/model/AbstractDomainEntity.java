package com.mrfaces.model;

import java.util.UUID;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

/**
 * Clase base de la que derivar entidades. ID no generado.
 * 
 * @author jromero
 *
 */
@MappedSuperclass
public abstract class AbstractDomainEntity implements DomainEntity<Long> {

	/**
	 * Serializable.
	 */
	private static final long serialVersionUID = -6848135713338183736L;

	@Transient
	private String uuid = UUID.randomUUID().toString();

	@Override
	public String getUuid() {
		return this.uuid;
	}

	/**
	 * Indica si la entidad es nueva.
	 * 
	 * @return Indica si la entidad es nueva.
	 */
	@Transient
	public boolean isNew() {
		return null == getId();
	}

	@Override
	public String toString() {
		return String.format("Entidad de tipo %s con id: %s", this.getClass().getName(), getId());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getId() == null) ? uuid.hashCode() : getId().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}

		AbstractDomainEntity other = (AbstractDomainEntity) obj;

		// Si los dos objetos están sin id -> Se compara por su uuid
		if (getId() == null && other.getId() == null) {
			return uuid.equals(other.uuid);
		}

		if (getId() == null) {
			if (other.getId() != null) {
				return false;
			}
		} else if (!getId().equals(other.getId())) {
			return false;
		}
		return true;
	}

}
