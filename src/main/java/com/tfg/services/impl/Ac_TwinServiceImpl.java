package com.tfg.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tfg.dao.Ac_TwinRepository;
import com.tfg.factory.ExceptionFactory;
import com.tfg.factory.ExceptionFactory.Errors;
import com.tfg.model.Ac_Twin;
import com.tfg.model.id.Ac_Twin_Id;
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
		List<Ac_Twin> lista = new ArrayList<Ac_Twin>();
		Optional<Ac_Twin> opt = Optional.empty();
		asociaciones.forEach((ac)-> {
			if(repository.findById(new Ac_Twin_Id(ac.getDt().getId(),
					ac.getGrupo().getId(),ac.getCampo().getId(),ac.getValue().getId()))==opt) {
				lista.add(ac);
			}
		});
		repository.saveAll(asociaciones);	
	}
	
	@Override
	public void deleteListAc_Twin(List<Ac_Twin> asociaciones) {
		List<Ac_Twin> lista = new ArrayList<Ac_Twin>();
		Optional<Ac_Twin> opt = Optional.empty();
		asociaciones.forEach((ac)-> {
			if(repository.findById(new Ac_Twin_Id(ac.getDt().getId(),
					ac.getGrupo().getId(),ac.getCampo().getId(),ac.getValue().getId()))!=opt) {
				lista.add(ac);
			}else {
				throw ExceptionFactory.getError(Errors.ASSOCIATIONS_NO_EXIST);
			}
		});
		repository.deleteAll(asociaciones);	
	}

}
