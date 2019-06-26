package com.tfg.adapters;

import java.util.ArrayList;
import java.util.List;

import com.tfg.model.Ac_Asset;
import com.tfg.model.DigitalAsset;
import com.tfg.model.Field;
import com.tfg.model.Group;
import com.tfg.model.GroupField;
import com.tfg.model.Valor_Campo;
import com.tfg.pojos.AssetModel;
import com.tfg.pojos.GroupFieldModel;
import com.tfg.pojos.NewAsset;

public class DigitalAssetAdapter {
	
	public static DigitalAsset getAssetEntity(NewAsset newAsset) {
		DigitalAsset asset = new DigitalAsset();
		asset.setCodigo(newAsset.getCode());
		asset.setDescripcion(newAsset.getDescription());
		return asset;
	}
	
	public static AssetModel getDigitalAssetModel(DigitalAsset asset) {
		AssetModel model = new AssetModel();
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

	public static List<GroupField> getGroupFieldsAssociatedToAsset(Group group,
			List<Field> listFields, List<Valor_Campo> listValues){
		List<GroupField> listGroupFields = new ArrayList<GroupField>();
		for(int i=0; i < listFields.size(); i++) {
			listGroupFields.add(new GroupField(group,listFields.get(i),
					listValues.get(i)));
		}
		return listGroupFields;
	}
	
	public static List<Ac_Asset> getAssociationsToAsset(DigitalAsset asset,
			Group group, List<Field> listFields){
		List<Ac_Asset> asociaciones = new ArrayList<Ac_Asset>();
		for(int i=0; i < listFields.size(); i++) {
			asociaciones.add(new Ac_Asset(asset,group,
					listFields.get(i)));
		}
		return asociaciones;
	}
	
	//Esto va a tener que ir para otra clase
	public static List<GroupFieldModel> getCaca(DigitalAsset asset){
		List<GroupFieldModel> list = new ArrayList<GroupFieldModel>();
		for(Ac_Asset ac : asset.getAsociaciones_asset()) {
			GroupFieldModel model = new GroupFieldModel();
			model.setGroupCode(ac.getGrupo().getCodigo());
			list.add(model);
		}
		return list;
	}

}
