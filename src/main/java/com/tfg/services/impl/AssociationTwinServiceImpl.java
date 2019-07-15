package com.tfg.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tfg.dao.AssociationTwinRepository;
import com.tfg.exceptions.ExceptionFactory;
import com.tfg.exceptions.ExceptionFactory.Errors;
import com.tfg.model.AssociationTwin;
import com.tfg.model.id.AssociationTwinId;
import com.tfg.services.AssociationTwinService;

/**
 * Implementación de la interfaz de operaciones para AssociationTwins
 * @author gcollada
 *
 */
@Service
public class AssociationTwinServiceImpl implements AssociationTwinService{
	
	@Autowired
	AssociationTwinRepository repository;

	@Override
	public void add(AssociationTwin ac) {
		repository.save(ac);		
	}

	@Override
	public void addListAssociationsTwin(List<AssociationTwin> asociaciones) {
		List<AssociationTwin> lista = new ArrayList<AssociationTwin>();
		Optional<AssociationTwin> opt = Optional.empty();
		asociaciones.forEach((ac)-> {
			if(repository.findById(new AssociationTwinId(ac.getDt().getId(),
					ac.getGf().getId()))==opt) {
				lista.add(ac);
			}
		});
		repository.saveAll(asociaciones);	
	}
	
	@Override
	public void deleteListAssociationsTwin(List<AssociationTwin> asociaciones) {
		List<AssociationTwin> lista = new ArrayList<AssociationTwin>();
		Optional<AssociationTwin> opt = Optional.empty();
		asociaciones.forEach((ac)-> {
			if(repository.findById(new AssociationTwinId(ac.getDt().getId(),
					ac.getGf().getId()))!=opt) {
				lista.add(ac);
			}else {
				throw ExceptionFactory.getError(Errors.ASSOCIATIONS_NO_EXIST);
			}
		});
		repository.deleteAll(asociaciones);	
	}

}
