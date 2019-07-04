package com.tfg.services.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tfg.dao.FieldRepository;
import com.tfg.exceptions.ExceptionFactory;
import com.tfg.exceptions.ExceptionFactory.Errors;
import com.tfg.model.Field;
import com.tfg.services.FieldService;
import com.tfg.services.adapters.FieldAdapter;
import com.tfg.services.model.FieldModel;
import com.tfg.services.model.NewField;

/**
 * 
 * @author gcollada
 *
 */
@Service
public class FieldServiceImpl implements FieldService{
	
	@Autowired
	FieldRepository repository;
	
	@Override
	public FieldModel add(Field field) {
		if(repository.findByCodigo(field.getCodigo())==null) {
			repository.save(field);
			return FieldAdapter.getFieldModel(field);
		}else {
			throw ExceptionFactory.getError(Errors.UNIQUE_CODE);
		}
	}
	
	@Override
	public FieldModel update(String code, Field field) {
		Field oldField = repository.findByCodigo(code);
		if(oldField!=null) {
			field.setId(oldField.getId());
			String newCode = field.getCodigo();
			if(!newCode.equals(code) && repository.findByCodigo(newCode)!=null) {
				throw ExceptionFactory.getError(Errors.UNIQUE_CODE);
			}else {
				repository.save(field);
				return FieldAdapter.getFieldModel(field);
			}		
		}else {
			throw ExceptionFactory.getError(Errors.ENTITY_NOT_FOUND);
		}
	}
	
	@Override
	public void delete(String codigo) {
		if(repository.findByCodigo(codigo)!=null) {
			repository.deleteByCodigo(codigo);
		}else {
			throw ExceptionFactory.getError(Errors.ENTITY_NOT_FOUND);
		}
	}

	@Override
	public Field getFieldByCode(String nombre) {
		Field field = repository.findByCodigo(nombre);
		if(field!=null) {
			return field;
		}		
		else {
			throw ExceptionFactory.getError(Errors.ENTITY_NOT_FOUND);
		}
	}

	@Override
	public List<FieldModel> getFields() {
		List<FieldModel> models = new ArrayList<FieldModel>(
				FieldAdapter.getFieldModel(repository.findAll()));
		return models;
	}

	public void addListFields(List<Field> campos) {
		List<Field> lista = new ArrayList<Field>();
		campos.forEach((campo)-> {
			if(repository.findByCodigo(campo.getCodigo())==null) {
				lista.add(campo);
			}
		});
		repository.saveAll(lista);
	}

	@Override
	public Field create(NewField newField) {
		return FieldAdapter.getFieldEntity(newField);
	}

	@Override
	public void addListFieldsCodes(List<String> fieldCodes) {
		List<Field> lista = new ArrayList<Field>();
		fieldCodes.forEach((field)-> {
			if(repository.findByCodigo(field)==null)
				lista.add(new Field(field,""));
		});
		repository.saveAll(lista);
	}
}
