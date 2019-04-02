package com.mrfaces.busqueda;

import java.io.Serializable;

/**
 * Contiene información sobre la ordenación de un campo.
 * 
 * @author diegogr
 *
 */
public class SortInfo implements Serializable {

	/**
	 * Serializable.
	 */
	private static final long serialVersionUID = -7556364962560263565L;

	/**
	 * Nombre del campo por el que se ordena.
	 */
	private String sortField;

	/**
	 * Tipo de ordenación.
	 */
	private OrderQuery sortOrder;

	/**
	 * Constructor.
	 * 
	 * @param sortField
	 *           sortField
	 * @param sortOrder
	 *           sortOrder
	 */
	public SortInfo(String sortField, OrderQuery sortOrder) {
		super();
		this.sortField = sortField;
		this.sortOrder = sortOrder;
	}

	/**
	 * Obtiene el sortField.
	 * 
	 * @return el sortField.
	 */
	public String getSortField() {
		return sortField;
	}

	/**
	 * Obtiene el sortOrder.
	 * 
	 * @return el sortOrder.
	 */
	public OrderQuery getSortOrder() {
		return sortOrder;
	}

}
