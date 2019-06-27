package com.tfg.dao;

import org.springframework.data.repository.CrudRepository;

import com.tfg.model.Value;

public interface Valor_CampoRepository extends CrudRepository<Value, Long>{
	
	Value findByValor(Object valor);

}
