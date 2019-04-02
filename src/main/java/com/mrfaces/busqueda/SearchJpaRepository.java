package com.mrfaces.busqueda;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * Interface basada en JpaRepository que añade métodos para realizar búsquedas a partir de filtros.
 * 
 * @author diegogr
 *
 * @param <T>
 * @param <ID>
 */
@NoRepositoryBean
public interface SearchJpaRepository<T, ID extends Serializable> extends JpaRepository<T, ID>, JpaSpecificationExecutor<T> {

	/**
	 * Obtiene una lista de elementos que cumplen las condiciones del filtro y se muestran paginados y ordenados según
	 * indican los parámetros.
	 * 
	 * @param filters
	 *           Datos del filtro.
	 * @param firstResult
	 *           Primer resultado a mostrar.
	 * @param maxResults
	 *           Número máximo de elementos a mostrar.
	 * @param sortInfo
	 *           Información para la ordenación.
	 * @return una lista de elementos que cumplen las condiciones del filtro y se muestran paginados y ordenados según
	 *         indican los parámetros.
	 */
	List<T> findByFilters(Specification<T> filters, Integer firstResult, Integer maxResults, List<SortInfo> sortInfo);

	/**
	 * Obtiene una lista de elementos que cumplen las condiciones del filtro y se muestran paginados según indican los
	 * parámetros.
	 * 
	 * @param filters
	 *           Datos del filtro.
	 * @param firstResult
	 *           Primer resultado a mostrar.
	 * @param maxResults
	 *           Número máximo de elementos a mostrar.
	 * @return una lista de elementos que cumplen las condiciones del filtro y se muestran paginados según indican los
	 *         parámetros.
	 */
	List<T> findByFilters(Specification<T> filters, Integer firstResult, Integer maxResults);

	/**
	 * Obtiene una lista de elementos que cumplen las condiciones del filtro y ordenados según indican los parámetros.
	 * 
	 * @param sortInfo
	 *           Información para la ordenación.
	 * @param filters
	 *           Datos del filtro.
	 * @return una lista de elementos que cumplen las condiciones del filtro y se muestran ordenados según indican los
	 *         parámetros.
	 */
	List<T> findByFilters(Specification<T> filters, List<SortInfo> sortInfo);

	/**
	 * Obtiene una lista de elementos que cumplen las condiciones del filtro.
	 * 
	 * @param filters
	 *           Datos del filtro.
	 * @return una lista de elementos que cumplen las condiciones del filtro.
	 */
	List<T> findByFilters(Specification<T> filters);

	/**
	 * Obtiene el numero de elementos que cumplen las condiciones del filtro.
	 * 
	 * @param filters
	 *           filters
	 * @return el numero de elementos que cumplen las condiciones del filtro.
	 */
	long countByFilters(Specification<T> filters);

	/**
	 * Obtiene la Specification correspondiente al mapa de filtros pasado. Método pensado para usar en las
	 * implementaciones "Custom" de repositorios que necesitan una tratamiento especializado de los filtros. En el
	 * mecanismo de Spring Data para tener repositorios con un compormatiento común en una clase Base pero al mismo
	 * tiempo personalizar parte del comportamiento no se puede usar la herencia directamente. Para poder reutilizar el
	 * comportamiento común de este método es necesario exponerlo en la API pública.
	 * 
	 * @param filters
	 *           filters
	 * @return la Specification.
	 */
	Specification<T> specificationBuild(Map<String, Object> filters);

	/**
	 * Sobreescribimos el método para forzarlo a lanzar UnsupportedOperationException. En vez de este método debemos
	 * utilizar findById que realiza la busqueda teniendo en cuenta los permisos del usuario logueado.
	 *
	 * @param id
	 *           id.
	 *
	 * @return el elemento buscado.
	 */
	T findOne(ID id);

	/**
	 * Obtiene una lista con los ids de los elementos que cumplen las condiciones del filtro.
	 * 
	 * @param filters
	 *           Datos del filtro.
	 * @return una lista con los ids de los elementos de elementos que cumplen las condiciones del filtro.
	 */
	List<ID> findIdsByFilters(Specification<T> filters);

	/**
	 * Obtiene una lista con los ids de los elementos que cumplen las condiciones del filtro y ordenados según indican
	 * los parámetros.
	 * 
	 * @param filters
	 *           Datos del filtro.
	 * @param sortInfo
	 *           sortInfo
	 * @return una lista con los ids de los elementos de elementos que cumplen las condiciones del filtro.
	 */
	List<ID> findIdsByFilters(Specification<T> filters, List<SortInfo> sortInfo);

	/**
	 * Obtiene una lista de elementos que cumplen las condiciones del filtro y se muestran paginados y ordenados según
	 * indican los parámetros.
	 * 
	 * @param filters
	 *           Datos del filtro.
	 * @param firstResult
	 *           Primer resultado a mostrar.
	 * @param maxResults
	 *           Número máximo de elementos a mostrar.
	 * @param sortInfo
	 *           Información para la ordenación.
	 * @param selectBuilder
	 *           Builder para construir la sentencia select.
	 * @param <S>
	 *           clase de los elementos devueltos.
	 * @return una lista de elementos que cumplen las condiciones del filtro y se muestran paginados y ordenados según
	 *         indican los parámetros.
	 *
	 */
	<S extends ResultadoBusquedaDto<ID>> List<S> findDtoByFilters(Specification<T> filters, Integer firstResult, Integer maxResults,
			List<SortInfo> sortInfo, ResultadoBusquedaDtoSelectBuilder<S, ID> selectBuilder);

	/**
	 * Obtiene una lista de elementos que cumplen las condiciones del filtro y ordenados según indican los parámetros.
	 * 
	 * @param sortInfo
	 *           Información para la ordenación.
	 * @param filters
	 *           Datos del filtro.
	 * @param <S>
	 *           clase de los elementos devueltos.
	 * @param selectBuilder
	 *           Builder para construir la sentencia select.
	 * @return una lista de elementos que cumplen las condiciones del filtro y se muestran ordenados según indican los
	 *         parámetros.
	 */
	<S extends ResultadoBusquedaDto<ID>> List<S> findDtoByFilters(Specification<T> filters, List<SortInfo> sortInfo,
			ResultadoBusquedaDtoSelectBuilder<S, ID> selectBuilder);

	/**
	 * Método que refresca la entidad desde base de datos invocando al método refresh del EntityManager.
	 * 
	 * @param entity
	 *           Entidad a refrescar
	 */
	void refresh(T entity);

}