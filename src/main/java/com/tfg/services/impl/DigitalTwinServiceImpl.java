package com.tfg.services.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tfg.dao.DigitalAssetRepository;
import com.tfg.dao.DigitalTwinRepository;
import com.tfg.dao.impl.DigitalAssetRepositoryImpl;
import com.tfg.model.DigitalAsset;
import com.tfg.model.DigitalTwin;
import com.tfg.services.DigitalAssetService;
import com.tfg.services.DigitalTwinService;

/**
 * 
 * @author gcollada
 *
 */
@Service
public class DigitalTwinServiceImpl implements DigitalTwinService{
	
	@Autowired
	DigitalTwinRepository repository;
	

	@Override
	public void add(DigitalTwin da) {
		repository.save(da);
	}

	@Override
	public void update(String codigo) {
		DigitalTwin da = repository.findByCodigo(codigo);
		repository.save(da);
	}

	@Override
	public void delete(String codigo) {
		repository.deleteByCodigo(codigo);
	}
	
	@Override
	public DigitalTwin findByCodigo(String codigo) {
		return repository.findByCodigo(codigo);
	}

	@Override
	public List<DigitalTwin> getDigitalTwins() {
		return (List<DigitalTwin>) repository.findAll();
	}

	@Override
	public List<DigitalTwin> getDigitalTwinsByFilters(Map<String, Object> filters) {
		DigitalAssetRepositoryImpl r = new DigitalAssetRepositoryImpl();
		//return r.getDigitalTwinsByFilters(filters);
		return null;
	}

}
