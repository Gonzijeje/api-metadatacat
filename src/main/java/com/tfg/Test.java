package com.tfg;

import java.io.IOException;
import java.text.ParseException;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.tfg.services.CSVReader;
import com.tfg.services.TXTReader;

@SpringBootApplication
public class Test {

	private static CSVReader csv = new CSVReader();
	private static TXTReader txt = new TXTReader();
	
	public static void main(String[] args) throws IOException, ParseException {
		//csv.read("src/main/resources/CSV_prueba.csv");
		txt.getMetadata("src/main/resources/datos_adf.txt");
	}

}
