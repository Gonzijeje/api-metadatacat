package com.mrfaces.busqueda.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;

import com.mrfaces.busqueda.SortInfo;


/**
 * Clase para adaptar los criterios de ordenacion de FW-STE a la clase Sort utilizada por SpringData para ordenar las
 * consultas.
 * 
 * @author diegogr
 *
 */
public final class SortBuilder {

	/**
	 * Constructor.
	 */
	private SortBuilder() {

	}

	/**
	 * Construye un objeto Sort para realizar la ordenación de una consulta a partir de una lista de criterios de
	 * ordenacion de mrfaces.
	 * 
	 * @param sortInfo
	 *           Criterios de ordenación.
	 * @return Objeto que envuelve los criterios de ordenación para ser utilizado en SpringData.
	 */
	public static Sort build(List<SortInfo> sortInfo) {
		if (sortInfo == null) {
			return null;
		}

		Sort res = null;

		ArrayList<Order> orders = new ArrayList<Order>();

		for (SortInfo detalleSortInfo : sortInfo) {
			Order order = new Order(Direction.fromString(detalleSortInfo.getSortOrder().getValor()), detalleSortInfo.getSortField());
			orders.add(order);
		}

		if (!orders.isEmpty()) {
			res = new Sort(orders);
		}
		return res;
	}

}
