package com.tfg.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tfg.dao.Grupo_campoRepository;
import com.tfg.dao.impl.DigitalAssetRepositoryImpl;
import com.tfg.model.GroupField;
import com.tfg.model.id.Grupo_Campo_Id;
import com.tfg.pojos.FieldValueModel;
import com.tfg.services.GroupFieldService;

/**
 * 
 * @author gcollada
 *
 */
@Service
@Transactional
public class GroupFieldServiceImpl implements GroupFieldService{
	
	@Autowired
	Grupo_campoRepository repository;
	
	DigitalAssetRepositoryImpl repositoryEM = new DigitalAssetRepositoryImpl();
	

	@Override
	public void add(GroupField gc) {
		repository.save(gc);
	}


	@Override
	public void addListGrupo_Campo(List<GroupField> grcampos) {
		List<GroupField> lista = new ArrayList<GroupField>();
		Optional<GroupField> opt = Optional.empty();
		grcampos.forEach((gr)-> {
			if(repository.findById(new Grupo_Campo_Id(gr.getGrupo().getId(),
					gr.getCampo().getId(),gr.getValue().getId()))==opt) {
				lista.add(gr);
			}
		});
		repository.saveAll(lista);
	}


	@Override
	public List<FieldValueModel> getFieldsAndValuesByGroup(String groupCode, String assetCode) {
		List<Object[]> results = repositoryEM.getFieldsAndValuesByGroup(groupCode,assetCode);
		List<FieldValueModel> list = new ArrayList<FieldValueModel>();
		
		for(Object[] o : results) {
			FieldValueModel model = new FieldValueModel();
			model.setCode(o[0].toString());
			model.setValue(o[1].toString());
			list.add(model);
		}	
		return list;
	}
	

}
