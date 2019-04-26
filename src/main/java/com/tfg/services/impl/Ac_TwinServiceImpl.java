package com.tfg.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tfg.dao.Ac_TwinRepository;
import com.tfg.model.Ac_Twin;
import com.tfg.services.Ac_TwinService;

/**
 * 
 * @author gcollada
 *
 */
@Service
public class Ac_TwinServiceImpl implements Ac_TwinService{
	
	@Autowired
	Ac_TwinRepository repository;

	@Override
	public void add(Ac_Twin ac) {
		repository.save(ac);		
	}

	@Override
	public void addListAc_Twin(List<Ac_Twin> asociaciones) {
		repository.saveAll(asociaciones);	
	}

}
