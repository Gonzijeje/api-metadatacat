package com.tfg.services.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tfg.dao.DigitalAssetRepository;
import com.tfg.dao.impl.DigitalAssetRepositoryImpl;
import com.tfg.model.DigitalAsset;
import com.tfg.services.DigitalAssetService;

/**
 * 
 * @author gcollada
 *
 */
@Service
public class DigitalAssetServiceImpl implements DigitalAssetService{
	
	@Autowired
	DigitalAssetRepository repository;
	
	DigitalAssetRepositoryImpl repositoryEM = new DigitalAssetRepositoryImpl();
	

	@Override
	public int add(DigitalAsset da) {
		if(repository.findByCodigo(da.getCodigo())!=null) {
			return 1;
		}else {
			repository.save(da);
			return 0;
		}
	}

	@Override
	public void update(String codigo) {
		DigitalAsset da = repository.findByCodigo(codigo);
		repository.save(da);
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
	public DigitalAsset findByCodigo(String codigo) {
		return repository.findByCodigo(codigo);
	}

	@Override
	public List<DigitalAsset> getDigitalAssets() {
		return (List<DigitalAsset>) repository.findAll();
	}

	@Override
	public List<DigitalAsset> getDigitalAssetsByFilters(Map<String, Object> filters) {;
		return repositoryEM.getDigitalAssetsByFilters(filters);
	}

	@Override
	public DigitalAsset create(Map<String, Object> payload) {
		DigitalAsset asset;
		try {
			asset = new DigitalAsset(payload.get("codigo").toString(),payload.get("descripcion").toString());
			return asset;
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return null;
	}

}
