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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

/**
 * 
 * @author yeahb
 *
 */
@Service
public class CSVReader {
	
	private class Key{
		String name;
		Type type;		
	}
	
	private static final String SEPARATOR = ";";
	private enum Type{
		TEXT, NUMBER, DATE, UNKNOWN
	}
	private static Pattern ALPHANUMERIC_PATTERN = Pattern.compile(
			"^(([a-zA-Z]+\\d*)||(\\d+[a-zA-Z]+))*$");
	private static Pattern NUMBER_PATTERN = Pattern.compile(
			"^[-+]?\\d+(\\,\\d+)?$");
	private static Pattern DATE_PATTERN = Pattern.compile(
		      "^\\d{2}\\/\\d{2}\\/\\d{4}$");
	int nCol = 0;
	int nRow = 0;
	Map<Key,ArrayList<String>> mapa = new HashMap<>();
	
	public void read(String csvFile) {
		BufferedReader br = null;
		String line = "";
		ArrayList<Key> header= new ArrayList<>();
		try {
			br = new BufferedReader(new FileReader(csvFile));
			while((line = br.readLine()) !=null) {
				if(nRow == 0) {
					String[] names = line.split(SEPARATOR);
					for(int i=0; i<names.length;i++) {
						header.add(new Key());
						header.get(i).name=names[i];
					}
					nRow++;
				}else {
					String[] datos = line.split(SEPARATOR);
					for(int i=0; i<datos.length;i++) {
						ArrayList<String> arrVal = mapa.get(header.get(i));
						if (arrVal == null) {
							arrVal = new ArrayList<String>();
							mapa.put(header.get(i),arrVal);
						}
						arrVal.add(datos[i]);
					}
					nRow++; nCol = datos.length;
				}
			}
			scan(mapa);
			mapa.forEach((k,v) -> System.out.println("Column: "+ k.name + " || Values: "+ v + " || Type: "+k.type));
			System.out.println("Número de Columnas: "+nCol);
			System.out.println("Número de Filas: "+nRow);
		}catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void scan(Map<Key,ArrayList<String>> map) {
		for(Key key: map.keySet()) {
			ArrayList<String> values = map.get(key);
			key.type=Type.valueOf(getType(values.get(0)));
		}	
	}
	
	private String getType(String value) {
		boolean matcher = ALPHANUMERIC_PATTERN.matcher(value).matches();
		if (matcher) {
			return "TEXT";
		} else if(matcher = NUMBER_PATTERN.matcher(value).matches()) {
			return "NUMBER";
		} else if (matcher = DATE_PATTERN.matcher(value).matches()) {
			return "DATE";
		} else {
			return "UNKNOWN";
		}
	}
	
	public Map<String,String> getSimpleContent(String csvFile) {
		BufferedReader br = null;
		String line = "";
		Map<String,String> mapa = new HashMap<>();
		try {
			br = new BufferedReader(new FileReader(csvFile));
			while((line = br.readLine()) !=null) {
				String[] datos = line.split(SEPARATOR);
				mapa.put(datos[0], datos[1]);
			}
		}catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
		return mapa;
	}

}
