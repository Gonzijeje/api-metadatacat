package com.tfg.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tfg.dao.GrupoRepository;
import com.tfg.model.Grupo;
import com.tfg.services.GrupoService;

/**
 * 
 * @author gcollada
 *
 */
@Service
public class GrupoServiceImpl implements GrupoService{
	
	@Autowired
	GrupoRepository repository;

	@Override
	public void add(Grupo grupo) {
		repository.save(grupo);
	}

	@Override
	public Grupo getGrupoByCodigo(String nombre) {
		return repository.findByCodigo(nombre);
	}

	@Override
	public List<Grupo> getGrupos() {
		return (List<Grupo>) repository.findAll();
	}

}
