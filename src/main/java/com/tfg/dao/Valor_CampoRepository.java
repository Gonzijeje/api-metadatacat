package com.tfg.dao;

import org.springframework.data.repository.CrudRepository;

import com.tfg.model.Valor_Campo;

public interface Valor_CampoRepository extends CrudRepository<Valor_Campo, Long>{
	
	Valor_Campo findByValor(Object valor);

}
