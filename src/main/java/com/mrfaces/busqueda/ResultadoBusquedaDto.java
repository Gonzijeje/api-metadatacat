package com.mrfaces.busqueda;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Transient;

import com.mrfaces.model.DomainEntity;


/**
 * Clase base para los resultados devueltos por los metodos findDtoByFilters de la clase SearchJpaRepositoryImpl.
 *
 * @author diegogr
 *
 * @param <ID>
 */
public abstract class ResultadoBusquedaDto<ID extends Serializable> implements DomainEntity<ID> {

	/**
	 * Serializable.
	 */
	private static final long serialVersionUID = -6250223236844683683L;

	private ID id;

	@Transient
	private String uuid = UUID.randomUUID().toString();

	/**
	 * Constructor.
	 * 
	 * @param id
	 *           id.
	 */
	public ResultadoBusquedaDto(ID id) {
		super();

		this.id = id;
	}

	/**
	 * Obtiene el valor del parametro id.
	 * 
	 * @return el valor del parametro id.
	 */
	public ID getId() {
		return id;
	}

	/**
	 * Establece el valor del parametro id.
	 * 
	 * @param id
	 *           el valor del parametro id.
	 */
	public void setId(ID id) {
		this.id = id;
	}

	@Override
	public String getUuid() {
		return this.uuid;
	}

}
