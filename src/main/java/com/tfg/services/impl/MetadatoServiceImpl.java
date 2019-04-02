package com.tfg.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tfg.dao.MetadatoRepository;
import com.tfg.model.Metadato;
import com.tfg.services.MetadatoService;

/**
 * 
 * @author gcollada
 *
 */

@Service
public class MetadatoServiceImpl implements MetadatoService{
	
	@Autowired
	MetadatoRepository repository;

	@Override
	public Metadato getMetadatoByCodigo(String nombre) {
		return repository.findByCodigo(nombre);
	}

	@Override
	public void add(Metadato metadato) {
		repository.save(metadato);	
	}

	@Override
	public List<Metadato> getMetadatos() {
		return (List<Metadato>) repository.findAll();
	}

}
