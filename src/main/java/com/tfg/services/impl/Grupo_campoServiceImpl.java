package com.tfg.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tfg.dao.Grupo_campoRepository;
import com.tfg.model.GroupField;
import com.tfg.model.id.Grupo_Campo_Id;
import com.tfg.services.Grupo_campoService;

/**
 * 
 * @author gcollada
 *
 */
@Service
public class Grupo_campoServiceImpl implements Grupo_campoService{
	
	@Autowired
	Grupo_campoRepository repository;
	

	@Override
	public void add(GroupField gc) {
		repository.save(gc);
	}


	@Override
	public void addListGrupo_Campo(List<GroupField> grcampos) {
		List<GroupField> lista = new ArrayList<GroupField>();
		Optional<GroupField> opt = Optional.empty();
		grcampos.forEach((gr)-> {
			if(repository.findById(new Grupo_Campo_Id(gr.getGrupo().getId(),
					gr.getCampo().getId(),gr.getValue().getId()))==opt) {
				lista.add(gr);
			}
		});
		repository.saveAll(lista);
	}
	

}
