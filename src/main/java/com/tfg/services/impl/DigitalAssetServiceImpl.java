package com.tfg.services.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
	

	@Override
	public int add(DigitalAsset da) {
		if(repository.findByCodigo(da.getCodigo())!=null) {
			return 1;
		}else if(repository.findByPath(da.getPath())!=null) {
			return 2;
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
	public DigitalAsset findByPath(String path) {
		return repository.findByPath(path);
	}

	@Override
	public List<DigitalAsset> getDigitalAssets() {
		return (List<DigitalAsset>) repository.findAll();
	}

	@Override
	public List<DigitalAsset> getDigitalAssetsByFilters(Map<String, Object> filters) {
		DigitalAssetRepositoryImpl r = new DigitalAssetRepositoryImpl();
		return r.getDigitalAssetsByFilters(filters);
	}

	@Override
	public DigitalAsset create(Map<String, Object> payload) {
		DigitalAsset asset;
		try {
			asset = new DigitalAsset(payload.get( "codigo" ).toString(),payload.get( "descripcion" ).toString(),
					payload.get( "tipo" ).toString(), payload.get( "entidad" ).toString(), payload.get( "contacto" ).toString(),
					payload.get( "autor" ).toString(),Double.parseDouble((String) payload.get( "tamano" )), payload.get( "unidad_tamano" ).toString(), 
					payload.get( "path" ).toString(), new SimpleDateFormat("dd/MM/yyyy").parse(payload.get( "fecha_creacion" ).toString()),
					new SimpleDateFormat("dd/MM/yyyy").parse(payload.get( "fecha_modificacion" ).toString()));
			asset.setCreateUser("gonzi");
			asset.setCreateDate(new Date());
			return asset;
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

}
