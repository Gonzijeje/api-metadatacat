package com.tfg.services.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tfg.dao.CampoRepository;
import com.tfg.model.Campo;
import com.tfg.services.CampoService;

/**
 * 
 * @author gcollada
 *
 */
@Service
public class CampoServiceImpl implements CampoService{
	
	@Autowired
	CampoRepository repository;
	
	@Override
	public void add(Campo metadato) {
		repository.save(metadato);
	}

	@Override
	public Campo getMetadatoByCodigo(String nombre) {
		return repository.findByCodigo(nombre);
	}

	@Override
	public List<Campo> getMetadatos() {
		return (List<Campo>) repository.findAll();
	}

	@Override
	public void addListCampos(List<String> campos) {
		campos.forEach((campo)-> {
			if(repository.findByCodigo(campo)==null) {
				repository.save(new Campo(campo,"Campo básico", "gonzi", new Date()));
			}
			});	
	}

}
