package com.tfg.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tfg.dao.GroupFieldRepository;
import com.tfg.dao.factory.EntityManagerLoader;
import com.tfg.model.GroupField;
import com.tfg.model.id.GroupFieldId;
import com.tfg.services.GroupFieldService;
import com.tfg.services.model.FieldValueModel;

/**
 * 
 * @author gcollada
 *
 */
@Service
@Transactional
public class GroupFieldServiceImpl implements GroupFieldService{
	
	@Autowired
	GroupFieldRepository repository;
	
	EntityManagerLoader repositoryEM = new EntityManagerLoader();
	

	@Override
	public void add(GroupField gc) {
		repository.save(gc);
	}


	@Override
	public void addListGroupFields(List<GroupField> grcampos) {
		List<GroupField> lista = new ArrayList<GroupField>();
		Optional<GroupField> opt = Optional.empty();
		grcampos.forEach((gr)-> {
			if(repository.findById(new GroupFieldId(gr.getGrupo().getId(),
					gr.getCampo().getId(),gr.getValue().getId()))==opt) {
				lista.add(gr);
			}
		});
		repository.saveAll(lista);
	}
	
	@Override
	public void deleteListGroupFields(List<GroupField> grcampos) {
		List<GroupField> lista = new ArrayList<GroupField>();
		Optional<GroupField> opt = Optional.empty();
		grcampos.forEach((gr)-> {
			if(repository.findById(new GroupFieldId(gr.getGrupo().getId(),
					gr.getCampo().getId(),gr.getValue().getId()))!=opt) {
				lista.add(gr);
			}
		});
		repository.deleteAll(lista);
	}


	@Override
	public List<FieldValueModel> getFieldsAndValuesByGroupAsset(String groupCode, String assetCode) {
		List<Object[]> results = repositoryEM.getFieldsAndValuesByGroupAsset(groupCode,assetCode);
//		List<Object[]> results = repository.getFieldsAndValuesByGroup(groupCode, assetCode);
		List<FieldValueModel> list = new ArrayList<FieldValueModel>();
		
		for(Object[] o : results) {
			FieldValueModel model = new FieldValueModel();
			model.setCode(o[0].toString());
			model.setValue(o[1].toString());
			list.add(model);
		}	
		return list;
	}
	
	@Override
	public List<FieldValueModel> getFieldsAndValuesByGroupTwin(String groupCode, String twinCode) {
		List<Object[]> results = repositoryEM.getFieldsAndValuesByGroupTwin(groupCode,twinCode);
//		List<Object[]> results = repository.getFieldsAndValuesByGroup(groupCode, assetCode);
		System.out.println("RESUUUULTS: "+results.size());
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
