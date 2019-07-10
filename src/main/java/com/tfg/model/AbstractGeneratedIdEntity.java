package com.tfg.model;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * Clase abstracta que contiene el atributo id, común al resto de entidades del sistema.
 *
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractGeneratedIdEntity {
	
	/**
	 * Attributo id que se autogenera en la base de datos y que es común
	 * al resto de entidades del sistema
	 */
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
