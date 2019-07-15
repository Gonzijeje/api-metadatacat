package com.tfg.services;

import java.util.List;

import com.tfg.model.Value;

/**
 * Interfaz de operaciones definidas relacionadas con el recurso Value
 * @author gcollada
 *
 */
public interface ValueService {

	void add(Value valor);
	
	/**
	 * Método para añadir una lista de Values al sistema
	 * @param valores Lista de los valores (String) de Values a insertar
	 * @return Iterable de los objetos Values insertados
	 */
	Iterable<Value> addListValores(List<String> valores);
	
	/**
	 * Método para recuperar un Value a partir de su valor
	 * @param valor Valor del Value a recuperar
	 * @return la entidad Value recuperada.
	 */
	Value getValor(String valor);
}
