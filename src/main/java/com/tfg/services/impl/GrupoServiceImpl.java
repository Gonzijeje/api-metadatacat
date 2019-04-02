package com.tfg.services.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.tfg.dao.GrupoRepository;
import com.tfg.model.Grupo;
import com.tfg.services.GrupoService;

public class GrupoServiceImpl implements GrupoService{
	
	@Autowired
	GrupoRepository repository;

	@Override
	public void add(Grupo grupo) {
		repository.save(grupo);
	}

	@Override
	public void delete(int id) {
		repository.deleteById(id);		
	}

	@Override
	public Grupo getGrupoByCodigo(String nombre) {
		return repository.findByCodigo(nombre);
	}

}
