package com.tfg.services;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import org.springframework.stereotype.Service;

import com.tfg.services.impl.FileInfo;


@Service
public class TXTReader {

	int nCaracteres = 0;
	int nWords = 0;
	int nLines = 0;
	private FileInfo file;
	private String content = "";

	public void getMetadata(String path) throws IOException, ParseException {
		file = new FileInfo(new File(path));
		System.out.println("Name: " + file.getName());
		System.out.println("Absolute path: " + file.getAbsolutePath());
		System.out.println("Size: " + file.getSize());
		System.out.println("Last modified: " + file.getLastModified());
		System.out.println("Owner: " + file.getOwner());
		System.out.println("Created: " + file.getCreated());
		System.out.println("Accessed: " + file.getAccessed());
		System.out.println("Written: " + file.getWritten());
		read(path);
	}

	public void read(String txtFile){
		BufferedReader br = null;
		String line = "";
		try {
			br = new BufferedReader(new FileReader(txtFile));
			while((line = br.readLine()) !=null) {
				content+=line; content+="\n";
				nLines++;
				String[] words = line.split(" ");
				for(String word : words) {
					nCaracteres+=word.length();
					if(word.length()>0)
						nWords++;
				}		
			}
			System.out.println("Número de Caracteres: "+nCaracteres);
			System.out.println("Número de Palabras: "+nWords);
			System.out.println("Número de Lineas: "+nLines);
			System.out.println("CONTENIDO: "+content);
		}catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
	}


}
