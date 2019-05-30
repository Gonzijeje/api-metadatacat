package com.tfg.dao.impl;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.tfg.model.DigitalAsset;
import com.tfg.model.DigitalTwin;

public class DigitalAssetRepositoryImpl {
	
	EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("tfg-rest-service");
	EntityManager entityManager = emfactory.createEntityManager();
	
	public List<DigitalAsset> getDigitalAssetsByFilters(Map<String,Object> filters){
		String parts[] = {"Select d.codigo from Ac_Asset ac, DigitalAsset d, Grupo_campo gc, Campo c, Valor_Campo v where "
				+ "ac.da=d and ac.campo=gc.campo and gc.campo=c and gc.valor=v", " like ", " and ", " c.codigo ", " v.valor "};
		StringBuilder sb = new StringBuilder();
		sb.append(parts[0]);
		filters.forEach((k,v) -> sb.append(parts[2]+parts[3]+parts[1]+"'"+k+"'"+parts[2]+parts[4]+parts[1]+"'"+v.toString()+"'"));
		Query query = entityManager.createQuery(sb.toString());
		return (List<DigitalAsset>)query.getResultList();
	}
	
	public List<DigitalTwin> getDigitalTwinsByFilters(Map<String,Object> filters){
		String parts[] = {"Select d.codigo from Ac_Twin ac, DigitalTwin d, Grupo_campo gc, Campo c, Valor_Campo v where "
				+ "ac.dt=d and ac.campo=gc.campo and gc.campo=c and gc.valor=v", " like ", " and ", " c.codigo ", " v.valor "};
		StringBuilder sb = new StringBuilder();
		sb.append(parts[0]);
		filters.forEach((k,v) -> sb.append(parts[2]+parts[3]+parts[1]+"'"+k+"'"+parts[2]+parts[4]+parts[1]+"'"+v.toString()+"'"));
		Query query = entityManager.createQuery(sb.toString());
		return (List<DigitalTwin>)query.getResultList();
	}
	
}
