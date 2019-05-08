package com.tfg.services;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

/**
 * 
 * @author yeahb
 *
 */
@Service
public class CSVReader {
	
	public static final String SEPARATOR = ";";
	
	public void read(String csvFile) {
		BufferedReader br = null;
		String line = "";
		Map<String,String> mapa = new HashMap<>();
		try {
			br = new BufferedReader(new FileReader(csvFile));
			while((line = br.readLine()) !=null) {
				String[] datos = line.split(SEPARATOR);
				System.out.println(Arrays.toString(datos));
				
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
	}

}
