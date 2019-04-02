package com.tfg.dao.impl;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.tfg.model.DigitalAsset;

public class DigitalAssetRepositoryImpl {
	
	EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("tfg-rest-service");
	EntityManager entityManager = emfactory.createEntityManager();
	
	public List<DigitalAsset> getDigitalAsset(String criterio,Object valor){
		Query query = entityManager.createQuery("Select d from DigitalAsset d where d."+criterio+" like '"+valor+"'");
		return (List<DigitalAsset>)query.getResultList();
	}
	
	public List<DigitalAsset> getDigitalAssetsByFilters(Map<String,Object> filters){
		String parts[] = {"Select d from DigitalAsset d where"," d."," like ", " and "};
		StringBuilder sb = new StringBuilder();
		sb.append(parts[0]);
		filters.forEach((k,v) -> sb.append(parts[1]+k+parts[2]+"'"+v.toString()+"'"+parts[3]));
		sb.delete(sb.length()-4, sb.length()-1);
		Query query = entityManager.createQuery(sb.toString());
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"+query.toString());
		return (List<DigitalAsset>)query.getResultList();
	}
	

}
