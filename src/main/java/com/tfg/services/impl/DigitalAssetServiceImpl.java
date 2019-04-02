package com.tfg.services.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tfg.dao.DigitalAssetRepository;
import com.tfg.dao.impl.DigitalAssetRepositoryImpl;
import com.tfg.model.DigitalAsset;
import com.tfg.services.DigitalAssetService;

@Service
public class DigitalAssetServiceImpl implements DigitalAssetService{
	
	@Autowired
	DigitalAssetRepository repository;
	

	@Override
	public void add(DigitalAsset da) {
		repository.save(da);		
	}

	@Override
	public void update(int id) {
		DigitalAsset da = repository.findById(id).orElse(null);
		repository.save(da);	
	}

	@Override
	public void delete(int id) {
		repository.deleteById(id);		
	}

	@Override
	public List<DigitalAsset> getDigitalAssets() {
		return (List<DigitalAsset>) repository.findAll();
	}

	@Override
	public List<DigitalAsset> getDigitalAssetsByFilter(String criterio, Object valor) {
		DigitalAssetRepositoryImpl r = new DigitalAssetRepositoryImpl();
		return r.getDigitalAsset(criterio, valor);
	}

	@Override
	public List<DigitalAsset> getDigitalAssetsByFilters(Map<String, Object> filters) {
		DigitalAssetRepositoryImpl r = new DigitalAssetRepositoryImpl();
		return r.getDigitalAssetsByFilters(filters);
	}

}
