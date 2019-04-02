package com.mrfaces.busqueda.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.query.Jpa21Utils;
import org.springframework.data.jpa.repository.query.JpaEntityGraph;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.util.Assert;

import com.mrfaces.busqueda.*;
import com.mrfaces.model.DomainEntity;


/**
 * Implementacion para SearchJpaRepository.
 *
 * @author diegogr
 *
 * @param <T>
 * @param <ID>
 */
@NoRepositoryBean
public class SearchJpaRepositoryImpl<T extends DomainEntity<ID>, ID extends Serializable> extends SimpleJpaRepository<T, ID> implements
		com.mrfaces.busqueda.SearchJpaRepository<T, ID> {

	private final JpaEntityInformation<T, ?> entityInformation;
	private final EntityManager em;
	private CriteriaBuilder builder;

	/**
	 * Constructor.
	 * 
	 * @param domainClass
	 *           domainClass.
	 * @param em
	 *           EntityManager.
	 */
	public SearchJpaRepositoryImpl(Class<T> domainClass, EntityManager em) {
		super(domainClass, em);
		this.em = em;
		this.entityInformation = JpaEntityInformationSupport.getEntityInformation(domainClass, em);
		this.builder = em.getCriteriaBuilder();
	}

	/**
	 * Constructor.
	 * 
	 * @param entityInformation
	 *           entityInformation
	 * @param entityManager
	 *           entityManager
	 */
	public SearchJpaRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
		super(entityInformation, entityManager);
		this.em = entityManager;
		this.entityInformation = entityInformation;
		this.builder = em.getCriteriaBuilder();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<T> findByFilters(Specification<T> filters, Integer firstResult, Integer maxResults, List<SortInfo> sortInfo) {
		Sort sort = sortBuild(sortInfo);
		TypedQuery<T> query = getQuery(filters, sort);
		query.setFirstResult(firstResult);
		query.setMaxResults(maxResults);
		return query.getResultList();
	}

	@Override
	public List<T> findByFilters(Specification<T> filters, Integer firstResult, Integer maxResults) {
		return this.findByFilters(filters, firstResult, maxResults, null);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<T> findByFilters(Specification<T> filters, List<SortInfo> sortInfo) {
		Sort sort = sortBuild(sortInfo);
		return this.findAll(filters, sort);
	}

	@Override
	public List<T> findByFilters(Specification<T> filters) {
		return this.findByFilters(filters, null);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long countByFilters(Specification<T> filters) {
		return this.count(filters);
	}

	@Override
	public Specification<T> specificationBuild(Map<String, Object> filters) {
		return new SearchSpecification<T>(filters);
	}

	/**
	 * Obtiene la ordenacion.
	 * 
	 * @param sortInfo
	 *           criterios de ordenacion.
	 * @return la ordenacion
	 */
	protected Sort sortBuild(List<SortInfo> sortInfo) {
		return SortBuilder.build(sortInfo);
	}

	// Se deshabilita ya que es necesario garantizar que todas las busquedas van a traves de los filtros de seguridad de
	// ambitos en caso de que esten definidos.
	@Override
	@Deprecated
	public T findOne(ID id) {
		throw new UnsupportedOperationException();
	}

	// Se deshabilita ya que seria necesario realizar una busqueda a partir del id y debemos garantizar que las busquedas
	// vayan a traves de los filtros de seguridad de los ambitos. En su lugar se debe utilizar el metodo delete(T
	// element)
	@Deprecated
	public void delete(ID id) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Creates a {@link TypedQuery} for the given {@link Specification} and {@link Sort}.
	 * 
	 * @param spec
	 *           can be {@literal null}.
	 * @param sort
	 *           can be {@literal null}.
	 * @return Query.
	 */
	@Override
	protected TypedQuery<T> getQuery(Specification<T> spec, Sort sort) {

		CriteriaQuery<T> query = this.builder.createQuery(getDomainClass());

		Root<T> root = applySpecificationToCriteria(spec, query);
		query.select(root);

		if (sort != null) {
			query.orderBy(MrFacesQueryUtils.toOrders(sort, root, this.builder));
		}

		return applyRepositoryMethodMetadata(em.createQuery(query));
	}

	/**
	 * Applies the given {@link Specification} to the given {@link CriteriaQuery}.
	 * 
	 * @param spec
	 *           can be {@literal null}.
	 * @param query
	 *           must not be {@literal null}.
	 * @return
	 */
	private <S> Root<T> applySpecificationToCriteria(Specification<T> spec, CriteriaQuery<S> query) {

		Assert.notNull(query);
		Root<T> root = query.from(getDomainClass());

		if (spec == null) {
			return root;
		}

		Predicate predicate = spec.toPredicate(root, query, this.builder);

		if (predicate != null) {
			query.where(predicate);
		}

		return root;
	}

	private TypedQuery<T> applyRepositoryMethodMetadata(TypedQuery<T> query) {

		if (this.getRepositoryMethodMetadata() == null) {
			return query;
		}

		LockModeType type = this.getRepositoryMethodMetadata().getLockModeType();
		TypedQuery<T> toReturn = type == null ? query : query.setLockMode(type);

		for (Entry<String, Object> hint : this.getRepositoryMethodMetadata().getQueryHints().entrySet()) {
			query.setHint(hint.getKey(), hint.getValue());
		}

		//return Jpa21Utils.tryGetFetchGraphHints(em, this.getRepositoryMethodMetadata().getEntityGraph(), TypedQuery.class);
		return (TypedQuery<T>) Jpa21Utils.tryGetFetchGraphHints(em, (JpaEntityGraph) this.getRepositoryMethodMetadata(), TypedQuery.class);
	}

	@Override
	public List<ID> findIdsByFilters(Specification<T> filters) {
		return this.findIdsByFilters(filters, null);
	}

	@Override
	public List<ID> findIdsByFilters(Specification<T> filters, List<SortInfo> sortInfo) {
		Sort sort = sortBuild(sortInfo);
		TypedQuery<ID> typedQuery = getQueryFindIdsCustomMethod(filters, sort);
		return (List<ID>) typedQuery.getResultList();
	}

	/**
	 * Obtiene la query para realizar la busqueda de los ids de la entidad.
	 * 
	 * @param spec
	 *           Specification
	 * @param sort
	 *           Criterios de ordenacion
	 * @return la query para realizar la busqueda de los ids de la entidad.
	 */
	@SuppressWarnings("unchecked")
	protected TypedQuery<ID> getQueryFindIdsCustomMethod(Specification<T> spec, Sort sort) {

		CriteriaQuery<ID> criteriaQuery = (CriteriaQuery<ID>) this.builder.createQuery(this.entityInformation.getIdType().getClass());

		Root<T> root = applySpecificationToCriteria(spec, criteriaQuery);
		criteriaQuery.select(root.<ID> get("id"));

		if (sort != null) {
			criteriaQuery.orderBy(MrFacesQueryUtils.toOrders(sort, root, this.builder));
		}

		return applyRepositoryMethodMetadataFindCustomMethod((TypedQuery<ID>) em.createQuery(criteriaQuery));
	}

	private <V extends Serializable> TypedQuery<V> applyRepositoryMethodMetadataFindCustomMethod(TypedQuery<V> query) {

		if (this.getRepositoryMethodMetadata() == null) {
			return query;
		}

		LockModeType type = this.getRepositoryMethodMetadata().getLockModeType();
		TypedQuery<V> toReturn = type == null ? query : query.setLockMode(type);

		for (Entry<String, Object> hint : this.getRepositoryMethodMetadata().getQueryHints().entrySet()) {
			query.setHint(hint.getKey(), hint.getValue());
		}

		//return Jpa21Utils.tryConfigureFetchGraph(em, toReturn, this.getRepositoryMethodMetadata().getEntityGraph());
		return (TypedQuery<V>) Jpa21Utils.tryGetFetchGraphHints(em, (JpaEntityGraph) this.getRepositoryMethodMetadata(), TypedQuery.class);
	}

	@Override
	public <S extends ResultadoBusquedaDto<ID>> List<S> findDtoByFilters(Specification<T> spec, Integer firstResult, Integer maxResults,
			List<SortInfo> sortInfo, ResultadoBusquedaDtoSelectBuilder<S, ID> selectBuilder) {

		Sort sort = sortBuild(sortInfo);
		TypedQuery<S> query = this.getQueryFindDto(spec, sort, selectBuilder);
		query.setFirstResult(firstResult);
		query.setMaxResults(maxResults);
		return query.getResultList();
	}

	@Override
	public <S extends ResultadoBusquedaDto<ID>> List<S> findDtoByFilters(Specification<T> spec, List<SortInfo> sortInfo,
			ResultadoBusquedaDtoSelectBuilder<S, ID> selectBuilder) {

		Sort sort = sortBuild(sortInfo);

		TypedQuery<S> typedQuery = this.getQueryFindDto(spec, sort, selectBuilder);

		return (List<S>) typedQuery.getResultList();
	}

	/**
	 * Construye la Query para devolver dto como resultados de la busqueda.
	 * 
	 * @param spec
	 *           Specification
	 * @param sort
	 *           Criterios de ordenacion
	 * @param selectBuilder
	 *           selectBuilder
	 * @param <S>
	 *           Tipo del resultado
	 * @return la Query para devolver dto como resultados de la busqueda.
	 * 
	 */
	protected <S extends ResultadoBusquedaDto<ID>> TypedQuery<S> getQueryFindDto(Specification<T> spec, Sort sort,
			ResultadoBusquedaDtoSelectBuilder<S, ID> selectBuilder) {
		CriteriaQuery<S> criteriaQuery = (CriteriaQuery<S>) this.builder.createQuery(selectBuilder.getClassResult());

		Root<T> root = applySpecificationToCriteria(spec, criteriaQuery);
		criteriaQuery = this.buildSelectFindDtoByFilters(criteriaQuery, root, selectBuilder);

		if (sort != null) {
			criteriaQuery.orderBy(MrFacesQueryUtils.toOrders(sort, root, this.builder));
		}

		return applyRepositoryMethodMetadataFindCustomMethod((TypedQuery<S>) em.createQuery(criteriaQuery));
	}

	/**
	 * Construye la select con la que realizar la busqueda para trabajar con Dtos.
	 * 
	 * @param criteriaQuery
	 *           criteriaQuery
	 * @param root
	 *           root
	 * @param selectBuilder
	 *           selectBuilder
	 * @param <S>
	 *           S
	 * @return la select con la que realizar la busqueda para trabajar con Dtos.
	 */
	protected <S extends ResultadoBusquedaDto<ID>> CriteriaQuery<S> buildSelectFindDtoByFilters(CriteriaQuery<S> criteriaQuery,
			Root<T> root, ResultadoBusquedaDtoSelectBuilder<S, ID> selectBuilder) {

		criteriaQuery.select(this.builder.construct(selectBuilder.getClassResult(), selectBuilder.buildSelection(root)));

		return criteriaQuery;
	}

	@Override
	public void refresh(T entity) {
		this.em.refresh(entity);
	}
}
