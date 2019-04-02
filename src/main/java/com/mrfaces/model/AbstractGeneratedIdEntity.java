package com.mrfaces.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * Clase base de la que derivar entidades. ID con valor autogenerado.
 * 
 * @author jromero
 *
 */
@MappedSuperclass
public abstract class AbstractGeneratedIdEntity extends AbstractDomainEntity {

	/**
	 * Serializable.
	 */
	private static final long serialVersionUID = -4591214851506143146L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	@Override
	public Long getId() {
		return this.id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}
}
