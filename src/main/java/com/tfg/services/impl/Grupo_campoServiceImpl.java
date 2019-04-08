package com.tfg.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tfg.dao.Grupo_campoRepository;
import com.tfg.model.Grupo_campo;
import com.tfg.services.Grupo_campoService;

/**
 * 
 * @author gcollada
 *
 */
@Service
public class Grupo_campoServiceImpl implements Grupo_campoService{
	
	@Autowired
	Grupo_campoRepository repository;
	

	@Override
	public void add(Grupo_campo gc) {
		repository.save(gc);
	}
	

}
