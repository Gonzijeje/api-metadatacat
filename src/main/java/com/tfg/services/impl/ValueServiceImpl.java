package com.tfg.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tfg.dao.ValueRepository;
import com.tfg.model.Value;
import com.tfg.services.ValueService;

@Service
public class ValueServiceImpl implements ValueService{
	
	@Autowired
	ValueRepository repository;

	@Override
	public void add(Value valor) {
		repository.save(valor);
	}

	@Override
	public Value getValor(String valor) {
		return repository.findByValor(valor);
	}

	@Override
	public Iterable<Value> addListValores(List<String> valores) {
		List<Value> lista = new ArrayList<Value>();
		valores.forEach((valor)-> {
			if(repository.findByValor(valor) == null && lista.stream().noneMatch(c -> c.getValor().equals(valor))) {
				lista.add(new Value(valor));
			}
		});
		return repository.saveAll(lista);
	}

}
