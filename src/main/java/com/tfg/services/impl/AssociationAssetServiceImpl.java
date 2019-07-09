package com.tfg.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tfg.dao.AssociationAssetRepository;
import com.tfg.exceptions.ExceptionFactory;
import com.tfg.exceptions.ExceptionFactory.Errors;
import com.tfg.model.AssociationAsset;
import com.tfg.model.id.AssociationAssetId;
import com.tfg.services.AssociationAssetService;

/**
 * 
 * @author gcollada
 *
 */
@Service
public class AssociationAssetServiceImpl implements AssociationAssetService{
	
	@Autowired
	AssociationAssetRepository repository;

	@Override
	public void add(AssociationAsset ac) {
		repository.save(ac);		
	}

	@Override
	public void addListAssociationsAsset(List<AssociationAsset> asociaciones) {
		List<AssociationAsset> lista = new ArrayList<AssociationAsset>();
		Optional<AssociationAsset> opt = Optional.empty();
		asociaciones.forEach((ac)-> {
			if(repository.findById(new AssociationAssetId(ac.getDa().getId(),
					ac.getGf().getId()))==opt) {
				lista.add(ac);
			}
		});
		repository.saveAll(asociaciones);	
	}

	@Override
	public void deleteListAssociationsAsset(List<AssociationAsset> asociaciones) {
		List<AssociationAsset> lista = new ArrayList<AssociationAsset>();
		Optional<AssociationAsset> opt = Optional.empty();
		asociaciones.forEach((ac)-> {
			if(repository.findById(new AssociationAssetId(ac.getDa().getId(),
					ac.getGf().getId()))!=opt) {
				lista.add(ac);
			}else {
				throw ExceptionFactory.getError(Errors.ASSOCIATIONS_NO_EXIST);
			}
		});
		repository.deleteAll(asociaciones);
	}

}
