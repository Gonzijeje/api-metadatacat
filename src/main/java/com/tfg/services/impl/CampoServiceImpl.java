package com.tfg.services.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tfg.dao.CampoRepository;
import com.tfg.model.Campo;
import com.tfg.services.CampoService;

/**
 * 
 * @author gcollada
 *
 */
@Service
public class CampoServiceImpl implements CampoService{
	
	@Autowired
	CampoRepository repository;
	
	@Override
	public boolean add(Campo metadato) {
		if(repository.findByCodigo(metadato.getCodigo())==null) {
			repository.save(metadato);
			return true;
		}
		return false;
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
	public Campo getCampoByCodigo(String nombre) {
		return repository.findByCodigo(nombre);
	}

	@Override
	public List<Campo> getCampos() {
		return (List<Campo>) repository.findAll();
	}

	/*@Override
	public void addListCampos(List<String> campos) {
		campos.forEach((campo)-> {
			if(repository.findByCodigo(campo)==null) {
				repository.save(new Campo(campo,"Campo básico", "gonzi", new Date()));
			}
			});	
	}*/
	public void addListCampos(List<String> campos) {
		campos.forEach((campo)-> {
			if(repository.findByCodigo(campo)==null) {
				repository.save(new Campo(campo,"", "gonzi", new Date()));
			}
		});	
	}

	@Override
	public Campo create(Map<String, Object> payload) {
		Campo campo = new Campo( payload.get( "codigo" ).toString(),
				payload.get( "descripcion" ).toString());
		campo.setCreateUser("gonzi");
		campo.setCreateDate(new Date());
		return campo;
	}
}
