package com.tfg.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tfg.dao.DigitalAssetRepository;
import com.tfg.dao.DigitalTwinRepository;
import com.tfg.dao.factory.EntityManagerLoader;
import com.tfg.exceptions.ExceptionFactory;
import com.tfg.exceptions.ExceptionFactory.Errors;
import com.tfg.model.AssociationTwin;
import com.tfg.model.DigitalAsset;
import com.tfg.model.DigitalTwin;
import com.tfg.model.Field;
import com.tfg.model.Group;
import com.tfg.model.GroupField;
import com.tfg.model.Value;
import com.tfg.services.AssociationTwinService;
import com.tfg.services.DigitalAssetService;
import com.tfg.services.DigitalTwinService;
import com.tfg.services.FieldService;
import com.tfg.services.GroupFieldService;
import com.tfg.services.GroupService;
import com.tfg.services.ValueService;
import com.tfg.services.adapters.DigitalAssetAdapter;
import com.tfg.services.adapters.DigitalTwinAdapter;
import com.tfg.services.model.FieldValueModel;
import com.tfg.services.model.GroupFieldModel;
import com.tfg.services.model.NewTwin;
import com.tfg.services.model.SimpleAsset;
import com.tfg.services.model.TwinModel;

/**
 * Implementación de la interfaz de operaciones para DigitalTwins
 * @author gcollada
 *
 */
@Service
@Transactional
public class DigitalTwinServiceImpl implements DigitalTwinService{
	
	@Autowired
	DigitalTwinRepository repository;
	
	@Autowired
	DigitalAssetRepository repositoryAsset;
	
	@Autowired
	FieldService fieldService;
	
	@Autowired
	GroupService groupService;
	
	@Autowired
	ValueService valueService;
	
	@Autowired
	GroupFieldService groupFieldService;
	
	@Autowired
	AssociationTwinService acTwinService;
	
	@Autowired
	DigitalAssetService assetService;
	
	EntityManagerLoader repositoryEM = new EntityManagerLoader();

	@Override
	public void add(NewTwin newTwin, DigitalTwin dt) {
		if(repository.findByCodigo(dt.getCodigo())==null) {
			repository.save(dt);
			addGroupFieldsValuesAssociated(newTwin, dt);
		}else {
			throw ExceptionFactory.getError(Errors.UNIQUE_CODE);
		}
	}

	@Override
	public DigitalTwin create(NewTwin newTwin) {
		List<String> codesAsset = new ArrayList<>();
		codesAsset.addAll(newTwin.getAssetsIn().stream().map(asset -> asset.getCode())
				.collect(Collectors.toList()));
		codesAsset.addAll(newTwin.getAssetsOut().stream().map(asset -> asset.getCode())
				.collect(Collectors.toList()));
		if(!codesAsset.isEmpty())
			assetService.checkListAssets(codesAsset);
		return DigitalTwinAdapter.getTwinEntity(newTwin);
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
	public TwinModel findByCodigo(String codigo) {
		DigitalTwin twin = repository.findByCodigo(codigo);
		if(twin!=null) {
			return DigitalTwinAdapter.getDigitalTwinModel(twin);
		}else {
			throw ExceptionFactory.getError(Errors.ENTITY_NOT_FOUND);
		}
	}
	
	private DigitalTwin findTwinByCode(String codigo) {
		DigitalTwin twin = repository.findByCodigo(codigo);
		if(twin!=null) {
			return twin;
		}else {
			throw ExceptionFactory.getError(Errors.ENTITY_NOT_FOUND);
		}
	}

	@Override
	public List<TwinModel> getDigitalTwins() {
		List<TwinModel> models = new ArrayList<TwinModel>(
				DigitalTwinAdapter.getDigitalTwinModel(repository.findAll()));
		return models;
	}

	@Override
	public List<TwinModel> getDigitalTwinsByFilters(Map<String, Object> filters) {
		if(filters==null || filters.keySet().size()==0)
			return getDigitalTwins();
		else {
			List<TwinModel> list = new ArrayList<TwinModel>();
			List<String> results = repositoryEM.getDigitalTwinsByFilters(filters);
			for(String res : results) {
				TwinModel model = findByCodigo(res);
				list.add(model);
			}
			return list;
		}
	}

	@Override
	public void addMetadata(List<GroupFieldModel> models, String code) {
		DigitalTwin twin = findTwinByCode(code);
		for(GroupFieldModel model : models) {
			List<Group> listGroups = new ArrayList<Group>();
			Group group = new Group(model.getGroupCode(),null);
			listGroups.add(group);
			groupService.addListGroups(listGroups);
			fieldService.addListFieldsCodes(model.getFieldCodes());
			valueService.addListValores(model.getValues());
			
			List<GroupField> listGroupFields = new ArrayList<GroupField>();
			List<AssociationTwin> asociaciones = new ArrayList<AssociationTwin>();
			group = groupService.getGroupByCode(model.getGroupCode());
			for(FieldValueModel fv : model.getFields()) {
				Field field = fieldService.getFieldByCode(fv.getCode());
				Value value = valueService.getValor(fv.getValue());
				GroupField gf = new GroupField(group,field,value);
				listGroupFields.add(gf);
				asociaciones.add(new AssociationTwin(twin,gf));
			}								
			groupFieldService.addListGroupFields(listGroupFields);
			acTwinService.addListAssociationsTwin(asociaciones);
			twin.getAsociaciones_twin().addAll(asociaciones);
		}
	}

	@Override
	public void deleteMetadata(List<GroupFieldModel> models, String code) {
		DigitalTwin twin = findTwinByCode(code);
		for(GroupFieldModel model : models) {
			Group group = groupService.getGroupByCode(model.getGroupCode());
			List<GroupField> listGroupFields = new ArrayList<GroupField>();
			List<AssociationTwin> asociaciones = new ArrayList<AssociationTwin>();
			for(FieldValueModel fv : model.getFields()) {
				Field field = fieldService.getFieldByCode(fv.getCode());
				Value value = valueService.getValor(fv.getValue());
				GroupField gf = new GroupField(group,field,value);
				listGroupFields.add(gf);
				asociaciones.add(new AssociationTwin(twin,gf));		
			}
			acTwinService.deleteListAssociationsTwin(asociaciones);
			twin.getAsociaciones_twin().removeAll(asociaciones);
			groupFieldService.deleteListGroupFields(listGroupFields);			
		}
	}
	
	/**
	 * Método privado que añade el grupo de metadatos basicos, sus campos y valores,
	 * y las relaciones con el DigitalTwin a la base de datos. Invoca a los servicios de cada una
	 * de estas entidades para primero, crear las instancias e insertarlas en la base de datos y, posteriormente,
	 * recuperarlas para poder crear las relaciones correspondientes.
	 * @param newTwin el modelo JSON del nuevo Digital Twin.
	 * @param twin el Digital Twin creado.
	 */
	private void addGroupFieldsValuesAssociated(NewTwin newTwin, DigitalTwin twin) {
		List<Field> listFields = DigitalTwinAdapter.getFieldsAssociatedToTwin(newTwin);
		fieldService.addListFields(listFields);
		
		List<Group> listGroups = new ArrayList<Group>();
		Group group = new Group("basicos", "Grupo de metadatos básicos");
		listGroups.add(group);
		groupService.addListGroups(listGroups);
		
		valueService.addListValores(newTwin.getValues());
		
		List<GroupField> listGroupFields = new ArrayList<GroupField>();
		List<AssociationTwin> asociaciones = new ArrayList<AssociationTwin>();
		group = groupService.getGroupByCode("basicos");
		for(int i=0; i<newTwin.getAttributes().size();i++) {
			Field field = fieldService.getFieldByCode(newTwin.getAttributes().get(i));
			Value value = valueService.getValor(newTwin.getValues().get(i));
			GroupField gf = new GroupField(group,field,value);
			listGroupFields.add(gf);
			asociaciones.add(new AssociationTwin(twin,gf));
		}								
		groupFieldService.addListGroupFields(listGroupFields);

		acTwinService.addListAssociationsTwin(asociaciones);
		twin.getAsociaciones_twin().addAll(asociaciones);
		addDigitalAssetsAssociated(newTwin,twin);
	}
	
	/**
	 * Método privado que comprueba si existen en el sistema los Digital Assets de entrada y de salida
	 * proporcionados a la hora de crear un DigitalTwin. En caso de exisitir se crean las relaciones entre
	 * el Digital Twin y los activos.
	 * @param newTwin
	 * @param twin
	 */
	private void addDigitalAssetsAssociated(NewTwin newTwin, DigitalTwin twin) {
		List<DigitalAsset> assetsIn = new ArrayList<DigitalAsset>();
		List<DigitalAsset> assetsOut = new ArrayList<DigitalAsset>();
		for(SimpleAsset asset : newTwin.getAssetsIn()) {
			DigitalAsset da = DigitalAssetAdapter.getAssetEntity(assetService.findByCodigo(asset.getCode()));
			assetsIn.add(da);
			da.setAssets_in(twin);
			repositoryAsset.save(da);
		}
		twin.getAsociaciones_assets_in().addAll(assetsIn);
		for(SimpleAsset asset : newTwin.getAssetsOut()) {
			DigitalAsset da = DigitalAssetAdapter.getAssetEntity(assetService.findByCodigo(asset.getCode()));
			assetsOut.add(da);
			da.setAssets_out(twin);
			repositoryAsset.save(da);
		}
		twin.getAsociaciones_assets_out().addAll(assetsOut);	
	}	

}
