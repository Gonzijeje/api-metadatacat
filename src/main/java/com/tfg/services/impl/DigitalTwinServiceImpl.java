package com.tfg.services.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
		}else if(repository.findByPath(dt.getPath())!=null) {
			return 2;
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
		//return r.getDigitalTwinsByFilters(filters);
		return null;
	}

	@Override
	public DigitalTwin create(Map<String, Object> payload) {
		DigitalTwin twin;
		try {
			twin = new DigitalTwin(payload.get( "codigo" ).toString(),payload.get( "descripcion" ).toString(),
					payload.get( "tipo" ).toString(), payload.get( "entidad" ).toString(), payload.get( "entorno" ).toString(),
					payload.get( "autor" ).toString(),payload.get( "path" ).toString(), new SimpleDateFormat("dd/MM/yyyy").parse(payload.get( "fecha_creacion" ).toString()),
					new SimpleDateFormat("dd/MM/yyyy").parse(payload.get( "fecha_modificacion" ).toString()));
			twin.setCreateUser("gonzi");
			twin.setCreateDate(new Date());
			return twin;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;	
	}

}
