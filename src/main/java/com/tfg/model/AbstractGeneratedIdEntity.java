package com.tfg.model;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractGeneratedIdEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;
	
	/**
	 * Método get de la propiedad id.
	 * 
	 * @return id.
	 */
	public Long getId() {
		return this.id;
	}
	
	/**
	 * Método set de la propiedad id.
	 * 
	 * @param id
	 *           tipo long.
	 */
	public void setId(Long id) {
		this.id = id;
	}

}
