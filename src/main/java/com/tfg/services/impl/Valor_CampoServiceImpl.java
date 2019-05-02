package com.tfg.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tfg.dao.Valor_CampoRepository;
import com.tfg.model.Valor_Campo;
import com.tfg.services.Valor_CampoService;

@Service
public class Valor_CampoServiceImpl implements Valor_CampoService{
	
	@Autowired
	Valor_CampoRepository repository;

	@Override
	public void add(Valor_Campo valor) {
		repository.save(valor);
	}

	@Override
	public Valor_Campo getValor(Object valor) {
		return repository.findByValor(valor);
	}

	@Override
	public void addListValores(List<Object> valores) {
		List<Valor_Campo> lista = new ArrayList<Valor_Campo>();
		valores.forEach((valor)-> {
			if(repository.findByValor(valor)==null) {
				lista.add(new Valor_Campo(valor.toString()));
			}
		});
		repository.saveAll(lista);
	}

}
