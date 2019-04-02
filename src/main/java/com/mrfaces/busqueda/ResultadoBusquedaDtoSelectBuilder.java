package com.mrfaces.busqueda;

import java.io.Serializable;

import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;


/**
 * Clase padre para construir las sentencias select en los buscadores que devuelven ResultadoBusquedaDto.
 * 
 * @author diegogr
 *
 * @param <S>
 * @param <ID>
 */
public abstract class ResultadoBusquedaDtoSelectBuilder<S extends ResultadoBusquedaDto<ID>, ID extends Serializable> {

	/**
	 * Clase en la que se envuelve el resultado de la consulta.
	 */
	private Class<S> classResult;

	/**
	 * Obtiene un array de Selection para construir la select con la que realizar la busqueda.
	 * 
	 * @param root
	 *           root
	 * @return un array de Selection para construir la select con la que realizar la busqueda.
	 */
	public abstract Selection<?>[] buildSelection(Root<?> root);

	/**
	 * Constructor.
	 * 
	 * @param classResult
	 *           classResult
	 */
	public ResultadoBusquedaDtoSelectBuilder(Class<S> classResult) {
		super();
		this.classResult = classResult;
	}

	/**
	 * Obtiene el classResult.
	 * 
	 * @return el classResult.
	 */
	public Class<S> getClassResult() {
		return classResult;
	}
}
