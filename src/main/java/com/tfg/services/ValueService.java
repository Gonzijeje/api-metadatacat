package com.tfg.services;

import java.util.List;

import com.tfg.model.Value;

public interface ValueService {

	void add(Value valor);
	
	Iterable<Value> addListValores(List<String> valores);
	
	Value getValor(String valor);
}
