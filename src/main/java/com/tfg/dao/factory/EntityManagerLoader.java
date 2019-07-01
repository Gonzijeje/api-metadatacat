package com.tfg.dao.factory;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.tfg.exceptions.ExceptionFactory;
import com.tfg.exceptions.ExceptionFactory.Errors;

public class EntityManagerLoader {
	
	EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("tfg-rest-service");
	EntityManager entityManager = emfactory.createEntityManager();
	
	public List<String> getDigitalAssetsByFilters(Map<String,Object> filters){
		String parts[] = {"Select d.codigo from Ac_Asset ac, DigitalAsset d, GroupField gc, Field c, Value v where "
				+ "ac.da=d and ac.campo=gc.campo and ac.value=gc.valor and gc.campo=c and gc.valor=v", " like ", " and ", " c.codigo ", " v.valor "};
		StringBuilder sb = new StringBuilder();
		sb.append(parts[0]);
		filters.forEach((k,v) -> {
			if(k.isEmpty() && v.toString().length()>0) {
				throw ExceptionFactory.getError(Errors.VALUE_WITH_NO_KEY);
			}else if(!k.isEmpty() && v.toString().length()==0) {
				sb.append(parts[2]+parts[3]+parts[1]+"'"+k+"'");
			}else {
				sb.append(parts[2]+parts[3]+parts[1]+"'"+k+"'"+parts[2]+parts[4]+parts[1]+"'"+v.toString()+"'");
			}		
		});
		Query query = entityManager.createQuery(sb.toString());
		return (List<String>)query.getResultList();
	}
	
	public List<String> getDigitalTwinsByFilters(Map<String,Object> filters){
		String parts[] = {"Select d.codigo from Ac_Twin ac, DigitalTwin d, GroupField gc, Field c, Value v where "
				+ "ac.dt=d and ac.campo=gc.campo and ac.value=gc.valor and gc.campo=c and gc.valor=v", " like ", " and ", " c.codigo ", " v.valor "};
		StringBuilder sb = new StringBuilder();
		sb.append(parts[0]);
		filters.forEach((k,v) -> {
			if(k.isEmpty() && v.toString().length()>0) {
				throw ExceptionFactory.getError(Errors.VALUE_WITH_NO_KEY);
			}else if(!k.isEmpty() && v.toString().length()==0) {
				sb.append(parts[2]+parts[3]+parts[1]+"'"+k+"'");
			}else {
				sb.append(parts[2]+parts[3]+parts[1]+"'"+k+"'"+parts[2]+parts[4]+parts[1]+"'"+v.toString()+"'");
			}		
		});
		Query query = entityManager.createQuery(sb.toString());
		return (List<String>)query.getResultList();
	}
	
	public List<Object[]> getFieldsAndValuesByGroup(String groupCode, String assetCode){
		Query query = entityManager.createQuery("SELECT c.codigo, v.valor FROM Field c, Value v, GroupField gc, Group g, "
				+ "DigitalAsset da, Ac_Asset ac where da=ac.da and ac.grupo=gc.grupo and ac.campo=gc.campo and ac.value=gc.valor "
				+ "and c=gc.campo and v=gc.valor and g=gc.grupo and g.codigo=?1 and da.codigo=?2 group by c.codigo,v.valor");
		List<Object[]> results = query.setParameter(1, groupCode).setParameter(2, assetCode).getResultList();
		return 	results;
	}
	
}
