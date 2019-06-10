package com.tfg.services.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tfg.dao.DigitalTwinRepository;
import com.tfg.dao.impl.DigitalAssetRepositoryImpl;
import com.tfg.model.DigitalTwin;
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
	public int add(DigitalTwin dt) {
		if(repository.findByCodigo(dt.getCodigo())!=null) {
			return 1;
		}else {
			repository.save(dt);
			return 0;
		}
	}

	@Override
	public void update(String codigo) {
		DigitalTwin dt = repository.findByCodigo(codigo);
		repository.save(dt);
	}

	@Override
	public boolean delete(String codigo) {
		if(repository.findByCodigo(codigo)!=null) {
			repository.deleteByCodigo(codigo);
			return true;
		}
		return false;
	}
	
	@Override
	public DigitalTwin findByCodigo(String codigo) {
		return repository.findByCodigo(codigo);
	}
	
	@Override
	public DigitalTwin findByPath(String path) {
		return repository.findByPath(path);
	}

	@Override
	public List<DigitalTwin> getDigitalTwins() {
		return (List<DigitalTwin>) repository.findAll();
	}

	@Override
	public List<DigitalTwin> getDigitalTwinsByFilters(Map<String, Object> filters) {
		DigitalAssetRepositoryImpl r = new DigitalAssetRepositoryImpl();
		return r.getDigitalTwinsByFilters(filters);
	}

	@Override
	public DigitalTwin create(Map<String, Object> payload) {
		DigitalTwin twin;
		twin = new DigitalTwin(payload.get("codigo").toString(),payload.get("descripcion").toString());
		return twin;
	}

}
