package com.tfg.services.adapters;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import com.tfg.model.AssociationAsset;
import com.tfg.model.DigitalAsset;
import com.tfg.model.Field;
import com.tfg.model.Group;
import com.tfg.model.GroupField;
import com.tfg.model.Value;
import com.tfg.services.GroupFieldService;
import com.tfg.services.impl.GroupFieldServiceImpl;
import com.tfg.services.model.AssetModel;
import com.tfg.services.model.GroupFieldModel;
import com.tfg.services.model.NewAsset;
import com.tfg.services.model.SimpleAsset;

public class DigitalAssetAdapter {
	
	@Autowired
	static GroupFieldService groupFieldService = new GroupFieldServiceImpl();
	
	public static DigitalAsset getAssetEntity(NewAsset newAsset) {
		DigitalAsset asset = new DigitalAsset();
		asset.setCodigo(newAsset.getCode());
		asset.setDescripcion(newAsset.getDescription());
		return asset;
	}
	
	public static DigitalAsset getAssetEntity(AssetModel assetModel) {
		DigitalAsset asset = new DigitalAsset();
		asset.setId(assetModel.getId());
		asset.setCodigo(assetModel.getCode());
		asset.setDescripcion(assetModel.getDescription());
		return asset;
	}
	
	public static AssetModel getDigitalAssetModel(DigitalAsset asset) {
		AssetModel model = new AssetModel();
		model.setId(asset.getId());
		model.setCode(asset.getCodigo());
		model.setDescription(asset.getDescripcion());
		model.setGrupos(getCaca(asset));
		return model;
	}
	
	public static List<AssetModel> getDigitalAssetModel(Iterable<DigitalAsset> fields) {
		List<AssetModel> models = new ArrayList<AssetModel>();
		fields.forEach((field)->{
			models.add(getDigitalAssetModel(field));
		});
		return models;
	}
	
	public static List<Field> getFieldsAssociatedToAsset(NewAsset newAsset){
		List<Field> listFields = new ArrayList<Field>();
		for(String field : newAsset.getAttributes()) {
			listFields.add(new Field(field,"Metadato "+field+" básico"));
		}
		return listFields;
	}
	
	public static SimpleAsset getSimpleAssetFromEntity(DigitalAsset asset) {
		SimpleAsset simple = new SimpleAsset();
		simple.setId(asset.getId());
		simple.setCode(asset.getCodigo());
		simple.setDescription(asset.getDescripcion());
		return simple;
	}
	
	public static List<SimpleAsset> getSimpleAssetsFromEntities(Set<DigitalAsset> assets){
		List<SimpleAsset> simples = new ArrayList<SimpleAsset>();
		for(DigitalAsset asset : assets) {
			simples.add(getSimpleAssetFromEntity(asset));
		}
		return simples;
	}

	public static List<GroupField> getGroupFieldsAssociatedToAsset(Group group,
			List<Field> listFields, List<Value> listValues){
		List<GroupField> listGroupFields = new ArrayList<GroupField>();
		for(int i=0; i < listFields.size(); i++) {
			listGroupFields.add(new GroupField(group,listFields.get(i),
					listValues.get(i)));
		}
		return listGroupFields;
	}
	
	//Esto va a tener que ir para otra clase
	public static List<GroupFieldModel> getCaca(DigitalAsset asset){
		List<GroupFieldModel> list = new ArrayList<GroupFieldModel>();
		for(AssociationAsset ac : asset.getAsociaciones_asset()) {
			if(list.isEmpty() || !list.stream().anyMatch(grupo -> grupo.getGroupCode().equals(ac.getGrupo().getCodigo()))) {
				GroupFieldModel model = new GroupFieldModel();
				model.setGroupCode(ac.getGrupo().getCodigo());
				list.add(model);
			}		
		}
		list.forEach((grupo)->{
			grupo.setFields(groupFieldService.getFieldsAndValuesByGroup(grupo.getGroupCode(),asset.getCodigo()));
		});
		return list;
	}

}
