package com.tfg.dao.factory;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.tfg.exceptions.ExceptionFactory;
import com.tfg.exceptions.ExceptionFactory.Errors;

/**
 * Clase para establecer el contexto de persistencia con la base de datos. En esta clase se declaran y ejecutan consultas dinámicas
 * a la base de datos que los repositorios proporcionados por JPA no son capaces de realziar.
 * @author gcollada
 *
 */
public class EntityManagerLoader {
	
	EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("tfg-rest-service");
	EntityManager entityManager = emfactory.createEntityManager();
	
	/**
	 * Método que crea una consulta dinámica en base a los parámetros o filtros que proporciona el usuario a la hora
	 * de buscar activos por sus metadatos. Prepara una consulta y la ejecuta con la consulta creada dinámicamente.
	 * @param filters Un mapa de claves-valor que se corrsponden a los filtros por los que se realiza
	 * la búsqueda de recursos
	 * @return Lista de los códigos de aquellos activos cuyos metdatos cumplen con los filtros.
	 */
	public List<String> getDigitalAssetsByFilters(Map<String,Object> filters){
		String parts[] = {"Select d.codigo from AssociationAsset ac, DigitalAsset d, GroupField gc, Field c, Value v where "
				+ "ac.da=d and ac.gf=gc and gc.campo=c and gc.valor=v", " like ", " and ", " c.codigo ", "v.valor"};
		StringBuilder sb = new StringBuilder();
		sb.append(parts[0]);
		filters.forEach((k,v) -> {
			if(k.isEmpty() && v.toString().length()>0) {
				throw ExceptionFactory.getError(Errors.VALUE_WITH_NO_KEY);
			}else if(!k.isEmpty() && v.toString().length()==0) {
				sb.append(parts[2]+parts[3]+parts[1]+"'"+k+"'");
			}else {
				sb.append(parts[2]+parts[3]+parts[1]+"'"+k+"'"+parts[2]+parts[4]+parts[1]+" '%"+v.toString()+"%' ");
			}		
		});
		Query query = entityManager.createQuery(sb.toString());
		return (List<String>)query.getResultList();
	}
	
	/**
	 * Método que crea una consulta dinámica en base a los parámetros o filtros que proporciona el usuario a la hora
	 * de buscar gemelos digitales por sus metadatos. Prepara una consulta y la ejecuta con la consulta creada dinámicamente.
	 * @param filters Un mapa de claves-valor que se corrsponden a los filtros por los que se realiza
	 * la búsqueda de recursos
	 * @return Lista de los códigos de aquellos gemelos cuyos metdatos cumplen con los filtros.
	 */
	public List<String> getDigitalTwinsByFilters(Map<String,Object> filters){
		String parts[] = {"Select d.codigo from AssociationTwin ac, DigitalTwin d, GroupField gc, Field c, Value v where "
				+ "ac.dt=d and ac.gf=gc and gc.campo=c and gc.valor=v", " like ", " and ", " c.codigo ", "v.valor"};
		StringBuilder sb = new StringBuilder();
		sb.append(parts[0]);
		filters.forEach((k,v) -> {
			if(k.isEmpty() && v.toString().length()>0) {
				throw ExceptionFactory.getError(Errors.VALUE_WITH_NO_KEY);
			}else if(!k.isEmpty() && v.toString().length()==0) {
				sb.append(parts[2]+parts[3]+parts[1]+"'"+k+"'");
			}else {
				sb.append(parts[2]+parts[3]+parts[1]+"'"+k+"'"+parts[2]+parts[4]+parts[1]+" '%"+v.toString()+"%' ");
			}		
		});
		Query query = entityManager.createQuery(sb.toString());
		return (List<String>)query.getResultList();
	}
	
	public List<Object[]> getFieldsAndValuesByGroupAsset(String groupCode, String assetCode){
		Query query = entityManager.createQuery("SELECT c.codigo, v.valor FROM Field c, Value v, GroupField gc, Group g, "
				+ "DigitalAsset da, AssociationAsset ac where da=ac.da and ac.gf=gc "
				+ "and c=gc.campo and v=gc.valor and g=gc.grupo and g.codigo=?1 and da.codigo=?2 group by c.codigo,v.valor");
		List<Object[]> results = query.setParameter(1, groupCode).setParameter(2, assetCode).getResultList();
		return 	results;
	}
	
	public List<Object[]> getFieldsAndValuesByGroupTwin(String groupCode, String twinCode){
		Query query = entityManager.createQuery("SELECT c.codigo, v.valor FROM Field c, Value v, GroupField gc, Group g, "
				+ "DigitalTwin dt, AssociationTwin ac where dt=ac.dt and ac.gf=gc "
				+ "and c=gc.campo and v=gc.valor and g=gc.grupo and g.codigo=?1 and dt.codigo=?2 group by c.codigo,v.valor");
		List<Object[]> results = query.setParameter(1, groupCode).setParameter(2, twinCode).getResultList();
		return 	results;
	}
	
}
