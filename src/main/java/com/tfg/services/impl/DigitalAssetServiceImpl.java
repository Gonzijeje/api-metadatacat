package com.tfg.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tfg.dao.DigitalAssetRepository;
import com.tfg.dao.factory.EntityManagerLoader;
import com.tfg.exceptions.ExceptionFactory;
import com.tfg.exceptions.ExceptionFactory.Errors;
import com.tfg.model.AssociationAsset;
import com.tfg.model.DigitalAsset;
import com.tfg.model.Field;
import com.tfg.model.Group;
import com.tfg.model.GroupField;
import com.tfg.model.Value;
import com.tfg.services.AssociationAssetService;
import com.tfg.services.DigitalAssetService;
import com.tfg.services.FieldService;
import com.tfg.services.GroupService;
import com.tfg.services.GroupFieldService;
import com.tfg.services.ValueService;
import com.tfg.services.adapters.DigitalAssetAdapter;
import com.tfg.services.model.AssetModel;
import com.tfg.services.model.FieldValueModel;
import com.tfg.services.model.GroupFieldModel;
import com.tfg.services.model.NewAsset;

/**
 * 
 * @author gcollada
 *
 */
@Service
@Transactional
public class DigitalAssetServiceImpl implements DigitalAssetService{
	
	@Autowired
	DigitalAssetRepository repository;
	
	@Autowired
	FieldService fieldService;
	
	@Autowired
	GroupService groupService;
	
	@Autowired
	ValueService valueService;
	
	@Autowired
	GroupFieldService groupFieldService;
	
	@Autowired
	AssociationAssetService acAssetService;
	
	EntityManagerLoader repositoryEM = new EntityManagerLoader();
	
	
	@Override
	public void add(NewAsset newAsset, DigitalAsset asset) {
		if(repository.findByCodigo(asset.getCodigo())==null) {
			repository.save(asset);
			addGroupFieldsValuesAssociated(newAsset, asset);
		}else {
			throw ExceptionFactory.getError(Errors.UNIQUE_CODE);
		}
	}

	@Override
	public void delete(String codigo) {
		if(repository.findByCodigo(codigo)!=null) {
			repository.deleteByCodigo(codigo);
		}else{
			throw ExceptionFactory.getError(Errors.ENTITY_NOT_FOUND);
		}
	}
	
	@Override
	public AssetModel findByCodigo(String codigo) {
		DigitalAsset asset = repository.findByCodigo(codigo);
		if(asset!=null) {
			return DigitalAssetAdapter.getDigitalAssetModel(asset);
		}else {
			throw ExceptionFactory.getError(Errors.ENTITY_NOT_FOUND);
		}
	}

	@Override
	public List<AssetModel> getDigitalAssets() {
		List<AssetModel> models = new ArrayList<AssetModel>(
				DigitalAssetAdapter.getDigitalAssetModel(repository.findAll()));
		return models;
	}

	@Override
	public List<AssetModel> getDigitalAssetsByFilters(Map<String, Object> filters) {
		if(filters==null || filters.keySet().size()==0)
			return getDigitalAssets();
		else {
			List<AssetModel> list = new ArrayList<AssetModel>();
			List<String> results = repositoryEM.getDigitalAssetsByFilters(filters);
			for(String res : results) {
				AssetModel model = findByCodigo(res);
				list.add(model);
			}
			return list;
		}			
	}

	@Override
	public DigitalAsset create(NewAsset newAsset) {
		return DigitalAssetAdapter.getAssetEntity(newAsset);
	}
	
	private void addGroupFieldsValuesAssociated(NewAsset newAsset, DigitalAsset asset) {
		List<Field> listFields = DigitalAssetAdapter.getFieldsAssociatedToAsset(newAsset);
		fieldService.addListCampos(listFields);
		
		List<Group> listGroups = new ArrayList<Group>();
		Group group = new Group("básicos", "Grupo de metadatos básicos");
		listGroups.add(group);
		groupService.addListGrupos(listGroups);
		
		valueService.addListValores(newAsset.getValues());
		
		List<GroupField> listGroupFields = new ArrayList<GroupField>();
		List<AssociationAsset> asociaciones = new ArrayList<AssociationAsset>();
		group = groupService.getGrupoByCodigo("básicos");
		for(int i=0; i<newAsset.getAttributes().size();i++) {
			Field field = fieldService.getCampoByCodigo(newAsset.getAttributes().get(i));
			Value value = valueService.getValor(newAsset.getValues().get(i));
			listGroupFields.add(new GroupField(group,field,value));
			asociaciones.add(new AssociationAsset(asset,group,field,value));
		}								
		groupFieldService.addListGrupo_Campo(listGroupFields);

		acAssetService.addListAc_Asset(asociaciones);
		asset.getAsociaciones_asset().addAll(asociaciones);
		System.out.println("SET: "+asset.getAsociaciones_asset());
	}

	@Override
	public void addMetadata(List<GroupFieldModel> models, String code) {
		DigitalAsset asset = DigitalAssetAdapter.getAssetEntity(findByCodigo(code));
		for(GroupFieldModel model : models) {
			List<Group> listGroups = new ArrayList<Group>();
			Group group = new Group(model.getGroupCode(),null);
			listGroups.add(group);
			groupService.addListGrupos(listGroups);
			fieldService.addListCamposCodes(model.getFieldCodes());
			valueService.addListValores(model.getValues());
			
			List<GroupField> listGroupFields = new ArrayList<GroupField>();
			List<AssociationAsset> asociaciones = new ArrayList<AssociationAsset>();
			group = groupService.getGrupoByCodigo(model.getGroupCode());
			System.out.println("GRUPO: "+group.getCodigo()+group.getId());
			for(FieldValueModel fv : model.getFields()) {
				Field field = fieldService.getCampoByCodigo(fv.getCode());
				System.out.println("CAMPO: "+field.getCodigo()+field.getId());
				Value value = valueService.getValor(fv.getValue());
				System.out.println("VALOR: "+value.getId());
				listGroupFields.add(new GroupField(group,field,value));
				asociaciones.add(new AssociationAsset(asset,group,field,value));
			}								
			groupFieldService.addListGrupo_Campo(listGroupFields);
			acAssetService.addListAc_Asset(asociaciones);
			asset.getAsociaciones_asset().addAll(asociaciones);
		}
		
	}

	@Override
	public void deleteMetadata(List<GroupFieldModel> models, String code) {
		DigitalAsset asset =  DigitalAssetAdapter.getAssetEntity(findByCodigo(code));
		for(GroupFieldModel model : models) {
			Group group = groupService.getGrupoByCodigo(model.getGroupCode());
			List<GroupField> listGroupFields = new ArrayList<GroupField>();
			List<AssociationAsset> asociaciones = new ArrayList<AssociationAsset>();
			for(FieldValueModel fv : model.getFields()) {
				Field field = fieldService.getCampoByCodigo(fv.getCode());
				Value value = valueService.getValor(fv.getValue());
				listGroupFields.add(new GroupField(group,field,value));
				asociaciones.add(new AssociationAsset(asset,group,field,value));		
			}
			acAssetService.deleteListAc_Asset(asociaciones);
			asset.getAsociaciones_asset().removeAll(asociaciones);
			groupFieldService.deleteListGrupo_Campo(listGroupFields);			
		}
	}
	
	@Override
	public void addRealAsset(String codigo, NewAsset newAsset) {
		DigitalAsset asset = repository.findByCodigo(codigo);
		if(asset!=null) {
			repository.delete(asset);
			repository.flush();
			asset = new DigitalAsset(codigo,"");
			repository.save(asset);
			addGroupFieldsValuesAssociated(newAsset,asset);
		}else {
			asset = new DigitalAsset(codigo,"");
			repository.save(asset);
			addGroupFieldsValuesAssociated(newAsset,asset);
		}	
	}

	@Override
	public boolean checkListAssets(List<String> codes) {
		for(String str:codes) {
			findByCodigo(str);
		}
		return true;
	}
	
}
