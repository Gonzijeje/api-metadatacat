package com.mrfaces.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Interfaz que deben de seguir las entidades del dominio. El objetivo es asegurarse de que todas ellas tengan un campo
 * que las identifique unívocamente.
 * 
 * @author diegogr
 * 
 * @param <ID>
 *           el tipo del identificador.
 */
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler", "uuid" })
public interface DomainEntity<ID extends Serializable> extends Serializable {
	/**
	 * Retorna el identificador de la entidad.
	 * 
	 * @return el identificador de la entidad y <code>null</code> si no tiene porque aún no ha sido persistida.
	 */
	ID getId();

	/**
	 * Identificador único de la entidad.
	 * 
	 * @return identificador único de la entidad.
	 */
	String getUuid();

	/**
	 * Establece el identificador de la entidad.
	 * 
	 * @param id
	 *           id
	 */
	void setId(ID id);
}
