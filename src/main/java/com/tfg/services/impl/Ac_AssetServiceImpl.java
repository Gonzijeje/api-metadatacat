package com.tfg.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tfg.dao.Ac_AssetRepository;
import com.tfg.model.Ac_Asset;
import com.tfg.services.Ac_AssetService;

/**
 * 
 * @author gcollada
 *
 */
@Service
public class Ac_AssetServiceImpl implements Ac_AssetService{
	
	@Autowired
	Ac_AssetRepository repository;

	@Override
	public void add(Ac_Asset ac) {
		repository.save(ac);		
	}

	@Override
	public void addListAc_Asset(List<Ac_Asset> asociaciones) {
		repository.saveAll(asociaciones);	
	}

}
