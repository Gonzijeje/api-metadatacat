package com.tfg.services;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

/**
 * 
 * @author yeahb
 *
 */
@Service
public class CSVReader {
	
	public static final String SEPARATOR = ";";
	private static Pattern ALPHANUMERIC_PATTERN = Pattern.compile(
			"^(([a-zA-Z]+\\d*)||(\\d+[a-zA-Z]+))*$");
	private static Pattern NUMBER_PATTERN = Pattern.compile(
			"^[-+]?\\d+(\\.\\d+)?$");
	private static Pattern DATE_PATTERN = Pattern.compile(
		      "^\\d{4}-\\d{2}-\\d{2}$");
	int nCol = 0;
	int nRow = 0;
	Map<String,ArrayList<String>> mapa = new HashMap<>();
	
	public void read(String csvFile) {
		BufferedReader br = null;
		String line = "";
		String[] header= {};
		try {
			br = new BufferedReader(new FileReader(csvFile));
			while((line = br.readLine()) !=null) {
				if(nRow == 0) {
					header = line.split(SEPARATOR);
					nRow++;
				}else {
					String[] datos = line.split(SEPARATOR);
					for(int i=0; i<datos.length;i++) {
						ArrayList<String> arrVal = mapa.get(header[i]);
						if (arrVal == null) {
							arrVal = new ArrayList<String>();
							mapa.put(header[i],arrVal);
						}
						arrVal.add(datos[i]);
					}
					nRow++; nCol = datos.length;
				}
			}
			mapa.forEach((k,v) -> System.out.println("Column: "+ k + " || Values: "+ v));
			System.out.println("Número de Columnas: "+nCol);
			System.out.println("Número de Filas: "+nRow);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void scan(Map<String,ArrayList<String>> map) {
		String index = "olo";
		
	}

}
