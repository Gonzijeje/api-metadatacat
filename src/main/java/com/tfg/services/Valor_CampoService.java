package com.tfg.services;

import java.util.List;

import com.tfg.model.Valor_Campo;

public interface Valor_CampoService {

	void add(Valor_Campo valor);
	
	Iterable<Valor_Campo> addListValores(List<Object> valores);
	
	Valor_Campo getValor(Object valor);
}
