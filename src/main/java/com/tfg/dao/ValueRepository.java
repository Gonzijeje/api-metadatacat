package com.tfg.dao;

import org.springframework.data.repository.CrudRepository;

import com.tfg.model.Value;

public interface ValueRepository extends CrudRepository<Value, Long>{
	
	Value findByValor(Object valor);

}
