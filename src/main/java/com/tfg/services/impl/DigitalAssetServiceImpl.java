package com.tfg.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tfg.adapters.DigitalAssetAdapter;
import com.tfg.adapters.GroupAdapter;
import com.tfg.dao.DigitalAssetRepository;
import com.tfg.dao.impl.DigitalAssetRepositoryImpl;
import com.tfg.factory.ExceptionFactory;
import com.tfg.factory.ExceptionFactory.Errors;
import com.tfg.model.Ac_Asset;
import com.tfg.model.DigitalAsset;
import com.tfg.model.Field;
import com.tfg.model.Group;
import com.tfg.model.GroupField;
import com.tfg.model.Valor_Campo;
import com.tfg.pojos.AssetModel;
import com.tfg.pojos.GroupModel;
import com.tfg.pojos.NewAsset;
import com.tfg.services.Ac_AssetService;
import com.tfg.services.DigitalAssetService;
import com.tfg.services.FieldService;
import com.tfg.services.GroupService;
import com.tfg.services.Grupo_campoService;
import com.tfg.services.Valor_CampoService;

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
	Valor_CampoService valueService;
	
	@Autowired
	Grupo_campoService groupFieldService;
	
	@Autowired
	Ac_AssetService acAssetService;
	
	DigitalAssetRepositoryImpl repositoryEM = new DigitalAssetRepositoryImpl();
	
	
	@Override
	public AssetModel add(NewAsset newAsset, DigitalAsset asset) {
		if(repository.findByCodigo(asset.getCodigo())==null) {
			repository.save(asset);
			addAssociations(newAsset, asset);
			return DigitalAssetAdapter.getDigitalAssetModel(asset);
		}else {
			throw ExceptionFactory.getError(Errors.UNIQUE_CODE);
		}
	}

	@Override
	public AssetModel update(String codigo, DigitalAsset asset) {
		DigitalAsset oldAsset = repository.findByCodigo(codigo);
		if(oldAsset!=null) {
			asset.setId(oldAsset.getId());
			String newCode = asset.getCodigo();
			if(!newCode.equals(codigo) && repository.findByCodigo(newCode)!=null) {
				throw ExceptionFactory.getError(Errors.UNIQUE_CODE);
			}else {
				repository.save(asset);
				return DigitalAssetAdapter.getDigitalAssetModel(asset);
			}
		}else {
			throw ExceptionFactory.getError(Errors.ENTITY_NOT_FOUND);
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
	public List<DigitalAsset> getDigitalAssetsByFilters(Map<String, Object> filters) {;
		return repositoryEM.getDigitalAssetsByFilters(filters);
	}

	@Override
	public DigitalAsset create(NewAsset newAsset) {
		return DigitalAssetAdapter.getAssetEntity(newAsset);
	}
	
	private void addAssociations(NewAsset newAsset, DigitalAsset asset) {
		List<Field> listFields = DigitalAssetAdapter.getFieldsAssociatedToAsset(newAsset);
		fieldService.addListCampos(listFields);
		
		List<Group> listGroups = new ArrayList<Group>();
		Group group = new Group("básicos", "Grupo de metadatos básicos");
		listGroups.add(group);
		groupService.addListGrupos(listGroups);
		
		List<Valor_Campo> listValues = (List<Valor_Campo>) valueService.addListValores(newAsset.getValues());
		System.out.println("Grupo: "+group.getCodigo()+group.getId());
		System.out.println("Campos: ");
		listFields.forEach((field)->{
			System.out.println("/t "+field.getCodigo()+field.getId());
		});
		System.out.println("Valores: ");
		listValues.forEach((field)->{
			System.out.println("/t "+field.getValor());
		});
		List<GroupField> listGroupFields = DigitalAssetAdapter.getGroupFieldsAssociatedToAsset(group, listFields, listValues);								
		groupFieldService.addListGrupo_Campo(listGroupFields);
		System.out.println("añadidos grupos campos");
		
		List<Ac_Asset> asociaciones = DigitalAssetAdapter.getAssociationsToAsset(asset, group, listFields);
		acAssetService.addListAc_Asset(asociaciones);
		System.out.println("añadidas asociaciones");
		asset.getAsociaciones_asset().addAll(asociaciones);
		System.out.println("SET: "+asset.getAsociaciones_asset());
	}

}
