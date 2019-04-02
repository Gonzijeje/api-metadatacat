package com.mrfaces.busqueda;

/**
 * Tipos de ordenación en las búsquedas.
 * 
 * @author diegogr
 * 
 */
public enum OrderQuery {
	ASC("ASC"), DESC("DESC");

	private String valor;

	private OrderQuery(String valor) {
		this.valor = valor;
	}

	/**
	 * Obtiene el campo valor.
	 * 
	 * @return el campo valor.
	 */
	public String getValor() {
		return this.valor;
	}
}
