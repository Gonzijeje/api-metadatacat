package com.tfg;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.tfg.services.CSVReader;

@SpringBootApplication
public class Test {

	private static CSVReader csv = new CSVReader();
	
	public static void main(String[] args) {
		csv.read("src/main/resources/CSV_prueba.csv");
	}

}
