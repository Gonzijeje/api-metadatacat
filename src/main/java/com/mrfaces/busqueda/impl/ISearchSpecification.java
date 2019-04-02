/**
 * 
 */
package com.mrfaces.busqueda.impl;


import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;


/**
 * @author diegogr
 *
 * @param <T>
 */
public interface ISearchSpecification<T> extends Specification<T> {

	/**
	 * Construye las condiciones de busqueda a partir de los filtros.
	 * 
	 * @param root
	 *           root
	 * @param query
	 *           query
	 * @param cb
	 *           cb
	 * @return las condiciones de busqueda a partir de los filtros.
	 */
	Predicate toPredicateFromFilters(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb);

	/**
	 * Establece el valor del parametro fetchAttributtes.
	 * 
	 * @param fetchAttributtes
	 *           el valor del parametro fetchAttributtes.
	 */
	//void setFetchAttributtes(List<FetchAttributeVo> fetchAttributtes);

	/**
	 * Establece el valor del parametro isCountQuery.
	 * 
	 * @param isCountQuery
	 *           el valor del parametro isCountQuery.
	 */
	void setIsCountQuery(Boolean isCountQuery);
}
