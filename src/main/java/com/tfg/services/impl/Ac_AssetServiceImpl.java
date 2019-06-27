package com.tfg.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tfg.dao.Ac_AssetRepository;
import com.tfg.model.Ac_Asset;
import com.tfg.model.id.Ac_Asset_Id;
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
		List<Ac_Asset> lista = new ArrayList<Ac_Asset>();
		Optional<Ac_Asset> opt = Optional.empty();
		asociaciones.forEach((ac)-> {
			if(repository.findById(new Ac_Asset_Id(ac.getDa().getId(),
					ac.getGrupo().getId(),ac.getCampo().getId(), ac.getValue().getId()))==opt) {
				lista.add(ac);
			}
		});
		repository.saveAll(asociaciones);	
	}

}
