package com.tfg.services.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tfg.adapters.FieldAdapter;
import com.tfg.dao.CampoRepository;
import com.tfg.model.Field;
import com.tfg.pojos.FieldModel;
import com.tfg.pojos.NewField;
import com.tfg.services.FieldService;

/**
 * 
 * @author gcollada
 *
 */
@Service
public class FieldServiceImpl implements FieldService{
	
	@Autowired
	CampoRepository repository;
	
	@Override
	public FieldModel add(Field field) {
		if(repository.findByCodigo(field.getCodigo())==null) {
			repository.save(field);
			return FieldAdapter.getFieldModel(field);
		}
		return null;
	}
	
	@Override
	public boolean delete(String codigo) {
		if(repository.findByCodigo(codigo)!=null) {
			repository.deleteByCodigo(codigo);
			return true;
		}
		return false;
	}

	@Override
	public FieldModel getCampoByCodigo(String nombre) {
		Field field = repository.findByCodigo(nombre);
		return FieldAdapter.getFieldModel(field);
	}

	@Override
	public List<FieldModel> getCampos() {
		List<FieldModel> models = new ArrayList<FieldModel>(
				FieldAdapter.getFieldModel(repository.findAll()));
		return models;
	}

	public void addListCampos(List<String> campos) {
		List<Field> lista = new ArrayList<Field>();
		campos.forEach((campo)-> {
			if(repository.findByCodigo(campo)==null) {
				lista.add(new Field(campo,""));
			}
		});
		repository.saveAll(lista);
	}

	@Override
	public Field create(NewField newField) {
		return FieldAdapter.getFieldEntity(newField);
	}

	@Override
	public FieldModel update(String code, Field field) {
		Field oldField = repository.findByCodigo(code);
		if(oldField!=null) {
			Long id = oldField.getId();
			field.setId(id);
			repository.save(field);
			return FieldAdapter.getFieldModel(field);
		}
		return null;
	}
}
