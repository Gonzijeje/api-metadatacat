package com.tfg.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tfg.dao.GrupoRepository;
import com.tfg.model.Grupo;
import com.tfg.services.GrupoService;

/**
 * 
 * @author gcollada
 *
 */
@Service
public class GrupoServiceImpl implements GrupoService{
	
	@Autowired
	GrupoRepository repository;

	@Override
	public boolean add(Grupo grupo) {
		if(repository.findByCodigo(grupo.getCodigo())==null) {
			repository.save(grupo);
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
	public Grupo getGrupoByCodigo(String nombre) {		
		return repository.findByCodigo(nombre);
	}

	@Override
	public List<Grupo> getGrupos() {
		return (List<Grupo>) repository.findAll();
	}
	
	@Override
	public void addListGrupos(List<String> grupos) {
		List<Grupo> lista = new ArrayList<Grupo>();
		grupos.forEach((grupo)-> {
			if(repository.findByCodigo(grupo)==null) {
				lista.add(new Grupo(grupo,""));
			}
		});
		repository.saveAll(lista);
	}

	@Override
	public Grupo create(Map<String, Object> payload) {
		if(payload.get("codigo")!=null && payload.get("descripcion")!=null) {
			Grupo grupo = new Grupo( payload.get( "codigo" ).toString(),
					payload.get( "descripcion" ).toString());
			return grupo;
		}
		return null;
	}

	@Override
	public boolean checkListGrupos(List<String> grupos) {
		return (grupos.stream().noneMatch((grupo)-> repository.findByCodigo(grupo) == null));	
	}
}
