package com.mrfaces.busqueda.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Fetch;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;



/**
 * 
 * Clase para realizar búsquedas a partir de los criterios definidos en los filtros de búsqueda. Se realiza una
 * transformación de las condiciones de búsqueda a predicados de Criteria.
 * 
 * @author roberto soto
 *
 * @param <T>
 */
public class SearchSpecification<T> implements ISearchSpecification<T> {

	protected static final String COMODIN_MULTI = "%";
	protected static final String COMODIN_SINGLE = "_";
	protected static final char COMODIN_ESCAPE = '\\';
	protected static final String SEPARADOR_CAMPOS = ".";

	public static final String FILTROS_AMBITO_SEGURIDAD_USUARIO_SESSION = "FILTROS_AMBITO_SEGURIDAD_USUARIO_SESSION";

	protected final Map<String, Object> filters;

	/**
	 * Listado de atributos LAZY a cargar.
	 */
	//protected List<FetchAttributeVo> fetchAttributtes;

	/**
	 * Indica si estamos en un COUNT, sino es un SELECT.
	 */
	protected Boolean isCountQuery;

	/**
	 * Constructor. En caso de recibir filters como null -> Se inicializa para permitir añadir filtros de seguridad en el
	 * caso de que no haya ningun filtro más.
	 * 
	 * @param filters
	 *           Filtros a aplicar en las busquedas.
	 */
	public SearchSpecification(Map<String, Object> filters) {
		if (filters == null) {
			this.filters = new HashMap<String, Object>();
		} else {
			// Se construye un nuevo mapa para evitar que las modificaciones que se realizan en el mismo afecten al mapa
			// que se pasa como
			this.filters = new HashMap<String, Object>(filters);
		}

		//this.fetchAttributtes = new ArrayList<FetchAttributeVo>();

		this.isCountQuery = null;
	}

	/**
	 * Este método se define como final para asegurarnos que todas las busquedas se realizan con la seguridad incluida.
	 * En caso de ser necesario redefinir su comportamiento hay que sobreescribir el metodo Predicate toPredicate(Root<T>
	 * root, List<Predicate> predicados, CriteriaBuilder cb)
	 * 
	 * @param root
	 *           root
	 * @param query
	 *           query
	 * @param cb
	 *           db
	 * @see org.springframework.data.jpa.domain.Specification#toPredicate(javax. persistence.criteria.Root,
	 *      javax.persistence.criteria.CriteriaQuery, javax.persistence.criteria.CriteriaBuilder)
	 * 
	 * @return el predicado.
	 */
	@Override
	public final Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

		// Se construyen los criterios de busqueda a partir de los filtros
		return this.toPredicateFromFilters(root, query, cb);
	}

	/**
	 * Método para crear una relacción join entre el atributo y el elemento que actua como padre de la relacción.
	 * 
	 * @param attribute
	 * @param join
	 * @param joinType
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private From createJoin(String attribute, From join, JoinType joinType) {

		// Se realiza el casta from, ya que el parent será o un Root ó un JOIN, y ambos son instancias de FROM.
		Set<Join> joins = (Set<Join>) ((From) join).getJoins();

		for (Join element : joins) {

			if (element.getAttribute().getName().equals(attribute)) {
				return element;
			}
		}

		join = ((From) join).join(attribute, joinType);
		return join;
	}

	/**
	 * Método para crear una relacción fetch entre el atributo y el elemento que actua como padre de la relacción.
	 * 
	 * @param attribute
	 * @param fetch
	 * @param joinType
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private From createFetch(String attribute, From fetch, JoinType joinType) {

		Set<Fetch> fetches = (Set<Fetch>) fetch.getFetches();

		for (Fetch element : fetches) {

			if (element.getAttribute().getName().equals(attribute)) {
				return (From) element;
			}
		}

		fetch = (From) fetch.fetch(attribute, joinType);
		return fetch;
	}

	/**
	 * Método para crear relacciones join ó fetch sobre el atributo pasado por parámetro.
	 * 
	 * Para las queries de tipo count se crean relacciones join y para las demás fetch.
	 * 
	 * @param attributte
	 * @param root
	 * @param joinType
	 * @param isCountQuery
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private From createJoinOrFetch(String attributte, From root, JoinType joinType, Boolean isCountQuery) {
		if (isCountQuery == null || isCountQuery) {
			return this.createJoin(attributte, root, joinType);
		} else {
			return this.createFetch(attributte, root, joinType);
		}
	}

	/**
	 * Método para crear las relacicones entre entidades.
	 * 
	 * @param root
	 *           root
	 * @param attributte
	 *           attributte
	 * @param joinType
	 *           joinType
	 * @param isCountQuery
	 *           isCountQuery
	 */
	@SuppressWarnings("rawtypes")
	protected void joinOrFetch(Root root, String attributte, JoinType joinType, Boolean isCountQuery) {

		String[] elements = StringUtils.split(attributte, ".");

		From raiz = root;

		for (int i = 0; i < elements.length; i++) {
			raiz = (From) this.createJoinOrFetch(elements[i], raiz, joinType, isCountQuery);
		}
	}

	

	@SuppressWarnings("rawtypes")
	private Boolean tieneValorSessionUsuario(Object valueSessionUsuario) {
		if (valueSessionUsuario == null) {
			return Boolean.FALSE;
		}

		if (valueSessionUsuario instanceof Collection) {
			return !((Collection) valueSessionUsuario).isEmpty();
		}

		return Boolean.TRUE;
	}

	/**
	 * Obtiene el path que representa la propiedad indicada en el elemento.
	 * 
	 * @param element
	 *           element
	 * @param name
	 *           nombre de la propiedad
	 * @return path
	 */
	@SuppressWarnings("rawtypes")
	protected static Path<?> getPath(Path<?> element, String name) {
		if (name.contains(".")) {
			String pre = name.substring(0, name.indexOf('.'));
			String post = name.substring(name.indexOf('.') + 1);

			Path<?> newPath;
			// Para JAVI: le meti el instance of ya que estaba dando un pete :
			// org.hibernate.ejb.criteria.path.SingularAttributePath cannot be cast to javax.persistence.criteria.From
			if (element instanceof From) {
				newPath = getElementPath((From) element, pre);
			} else {
				newPath = element.get(pre);
			}

			return getPath(newPath, post);
		} else {
			if (element instanceof From) {
				return getElementPath((From) element, name);
			} else {
				return element.get(name);
			}
		}
	}

	/**
	 * Método para obtener el path de un elemento considerando las relacciones join / fetch que puede contener.
	 * 
	 * @param element
	 * @param name
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static Path<?> getElementPath(From element, String name) {

		if (element.getFetches() != null && !element.getFetches().isEmpty()) {
			Set<Fetch> fetches = element.getFetches();
			for (Fetch fetch : fetches) {
				if (fetch.getAttribute().getName().equals(name)) {
					return (Join) fetch;
				}
			}
		}

		if (element.getJoins() != null && !element.getJoins().isEmpty()) {
			Set<Join> joins = element.getJoins();
			for (Join join : joins) {
				if (join.getAttribute().getName().equals(name)) {
					return join;
				}
			}
		}

		return element.get(name);
	}

	/**
	 * Obtiene un predicado a partir de una lista de predicados.
	 * 
	 * @param predicados
	 *           predicados
	 * @param cb
	 *           builder
	 * @return un predicado a partir de una lista de predicados.
	 */
	protected static Predicate predicateAndFromList(List<Predicate> predicados, CriteriaBuilder cb) {
		Predicate predicate = null;
		if (predicados.size() == 1) {
			predicate = predicados.get(0);
		} else {
			predicate = cb.and(predicados.toArray(new Predicate[0]));
		}
		return predicate;
	}

	/**
	 * Indica si alguno de los valores contiene un comodin.
	 * 
	 * @param entrada
	 *           valores
	 * @return Indica si alguno de los valores se encuentra escapado.
	 */
	protected Boolean hasComodin(Map.Entry<String, Object> entrada) {
		if (entrada.getValue() instanceof String) {
			String value = String.valueOf(entrada.getValue());
			if (value == null) {
				return false;
			}

			return value.contains(COMODIN_MULTI) || value.contains(COMODIN_SINGLE);
		}

		return false;
	}

	/**
	 * Indica si alguno de los valores se encuentra escapado.
	 * 
	 * @param entrada
	 *           valores
	 * @return Indica si alguno de los valores se encuentra escapado.
	 */
	protected Boolean hasEscape(Map.Entry<String, Object> entrada) {
		if (entrada.getValue() instanceof String) {
			String value = String.valueOf(entrada.getValue());
			if (value == null) {
				return false;
			}

			return value.contains(COMODIN_ESCAPE + COMODIN_MULTI) || value.contains(COMODIN_ESCAPE + COMODIN_SINGLE);
		}

		return false;
	}

	

	@Override
	public void setIsCountQuery(Boolean isCountQuery) {
		this.isCountQuery = isCountQuery;
	}

	@Override
	public Predicate toPredicateFromFilters(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		// TODO Auto-generated method stub
		return null;
	}

}
