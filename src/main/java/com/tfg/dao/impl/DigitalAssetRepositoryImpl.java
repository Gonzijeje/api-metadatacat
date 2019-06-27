package com.tfg.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.tfg.model.DigitalAsset;
import com.tfg.model.DigitalTwin;
import com.tfg.pojos.FieldValueModel;

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
	
	public List<Object[]> getFieldsAndValuesByGroup(String groupCode, String assetCode){
		Query query = entityManager.createQuery("SELECT c.codigo, v.valor FROM Field c, Value v, GroupField gc, Group g, "
				+ "DigitalAsset da, Ac_Asset ac where da=ac.da and ac.grupo=gc.grupo and ac.campo=gc.campo and ac.value=gc.valor "
				+ "and c=gc.campo and v=gc.valor and g=gc.grupo and g.codigo=?1 and da.codigo=?2 group by c.codigo,v.valor");
		List<Object[]> results = query.setParameter(1, groupCode).setParameter(2, assetCode).getResultList();
		return 	results;
	}
	
}
