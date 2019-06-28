package com.tfg.services;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tfg.adapters.DigitalAssetAdapter;
import com.tfg.model.Ac_Asset;
import com.tfg.model.DigitalAsset;
import com.tfg.model.Field;
import com.tfg.model.Group;
import com.tfg.model.GroupField;
import com.tfg.model.Value;

/**
 * 
 * @author yeahb
 *
 */
@Service
@Transactional
public class CSVReader {
	
	@Autowired
	GroupService groupService;
	
	@Autowired
	FieldService fieldService;
	
	@Autowired
	ValueService valueService;
	
	@Autowired
	GroupFieldService groupFieldService;
	
	@Autowired
	Ac_AssetService ac_assetService;
	
	@Autowired
	DigitalAssetService assetService;
	
	
	private class Key{
		String name;
		Type type;
		
	}
	
	private static final String SEPARATOR = ";";
	private static final String defaultPath = "src/main/resources/";
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
			br = new BufferedReader(new FileReader(defaultPath+csvFile));
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
			scan(mapa,csvFile);
			mapa.forEach((k,v) -> System.out.println("Column: "+ k.name + " || Values: "+ v + " || Type: "+k.type));
			System.out.println("Número de Columnas: "+nCol);
			System.out.println("Número de Filas: "+nRow);
		}catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void scan(Map<Key,ArrayList<String>> map, String csvFile) {
		DigitalAsset asset = DigitalAssetAdapter.getAssetEntity(assetService.findByCodigo(csvFile));
		List<Group> listGroups = new ArrayList<Group>();
		Group group = new Group("CSV_basics", "Basic metadata associated to CSV files");
		listGroups.add(group);
		groupService.addListGrupos(listGroups);
		List<String> listFields = new ArrayList<String>();
		listFields.addAll(map.keySet().stream().map(entry -> "csv_column_"+entry.name).collect(Collectors.toList()));
		listFields.addAll(map.keySet().stream().map(entry -> "csv_column_"+entry.name+"_type").collect(Collectors.toList()));
		listFields.add("nColumns");
		listFields.add("nRows");
		fieldService.addListCamposCodes(listFields);
		List<String> listValues = new ArrayList<String>();
		listValues.add(String.valueOf(nCol));
		listValues.add(String.valueOf(nRow));
		List<GroupField> listGroupFields = new ArrayList<GroupField>();
		List<Ac_Asset> asociaciones = new ArrayList<Ac_Asset>();
		group = groupService.getGrupoByCodigo("CSV_basics");
		for(Key key: map.keySet()) {
			listValues.add(map.get(key).stream().map(Object::toString).collect(Collectors.joining(",")));		
			Type t = key.type=Type.valueOf(getType(map.get(key).get(0)));
			listValues.add(t.name());
		}
		valueService.addListValores(listValues);
		for(Key key: map.keySet()) {
			Field field = fieldService.getCampoByCodigo("csv_column_"+key.name);
			Value value = valueService.getValor(map.get(key).stream().map(Object::toString).collect(Collectors.joining(",")));
			listGroupFields.add(new GroupField(group,field,value));
			asociaciones.add(new Ac_Asset(asset,group,field,value));
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
