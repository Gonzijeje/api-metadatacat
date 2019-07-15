package com.tfg.services.readers;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tfg.model.AssociationAsset;
import com.tfg.model.DigitalAsset;
import com.tfg.model.Field;
import com.tfg.model.Group;
import com.tfg.model.GroupField;
import com.tfg.model.Value;
import com.tfg.services.AssociationAssetService;
import com.tfg.services.DigitalAssetService;
import com.tfg.services.FieldService;
import com.tfg.services.GroupFieldService;
import com.tfg.services.GroupService;
import com.tfg.services.ValueService;
import com.tfg.services.adapters.DigitalAssetAdapter;
import com.tfg.services.elastic.ElasticService;

/**
 * Servicio encargado de obtener metadatos específicos de los archivos TXT
 * @author gcollada
 *
 */
@Service
@Transactional
public class TXTReader {
	
	/**
	 * Servicio para realizar operaciones lógicas sobre grupos
	 */
	@Autowired
	GroupService groupService;
	
	/**
	 * Servicio para realizar operaciones lógicas sobre campos
	 */
	@Autowired
	FieldService fieldService;
	
	/**
	 * Servicio para realizar operaciones lógicas sobre valores
	 */
	@Autowired
	ValueService valueService;
	
	/**
	 * Servicio para realizar operaciones lógicas sobre GroupFields (agrupaciones de metadatos)
	 */
	@Autowired
	GroupFieldService groupFieldService;
	
	/**
	 * Servicio para realizar operaciones lógicas sobre AssociationAssets
	 */
	@Autowired
	AssociationAssetService ac_assetService;
	
	/**
	 * Servicio para realizar operaciones lógicas sobre DigitalAssets
	 */
	@Autowired
	DigitalAssetService assetService;
	
	/**
	 * Servicio para realizar operaciones lógicas sobre Elasticsearch
	 */
	@Autowired
	ElasticService elasticService;

	int nCaracteres = 0;
	int nWords = 0;
	int nLines = 0;
	private String content = "";
	private Map<String, Object> map = new HashMap<>();
	
	private static final String defaultPath = "src/main/resources/";

	/**
	 * Método que se encarga de ir leyendo el archivo TXT y rellenando el atributo contenido
	 * para posteriormente indexarlo en Elasticsearch. También cuenta el número de palabra, caracteres y lineas.
	 * @param session
	 * @param txtFile Nombre del archivo TXT
	 */
	public void read(HttpSession session, String txtFile){
		BufferedReader br = null;
		String line = "";
		try {
			br = new BufferedReader(new FileReader(defaultPath+txtFile));
			while((line = br.readLine()) !=null) {
				setContent(getContent() + line); setContent(getContent());
				nLines++;
				String[] words = line.split(" ");
				for(String word : words) {
					nCaracteres+=word.length();
					if(word.length()>0)
						nWords++;
				}		
			}
			fillMap();
			br.close();	
			getMetadata(session, txtFile);		
		}catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Método que se encarga de añadir los metadatos obtenidos a la base de datos invocando
	 * a los distintos servicios de las entidades
	 * @param session
	 * @param txtFile Nombre del archivo TXT
	 */
	private void getMetadata(HttpSession session, String txtFile) {
		DigitalAsset asset = DigitalAssetAdapter.getAssetEntity(assetService.findByCodigo(txtFile));
		List<Group> listGroups = new ArrayList<Group>();
		Group group = new Group("TXT_basics", "Basic metadata associated to TXT files");
		listGroups.add(group);
		groupService.addListGroups(listGroups);
		fieldService.addListFieldsCodes(getFieldsMetadata());
		valueService.addListValores(getValuesMetadata());
		List<GroupField> listGroupFields = new ArrayList<GroupField>();
		List<AssociationAsset> asociaciones = new ArrayList<AssociationAsset>();
		group = groupService.getGroupByCode("TXT_basics");
		for(String key: map.keySet()) {
			Field field = fieldService.getFieldByCode(key);
			Value value = valueService.getValor(map.get(key).toString());
			GroupField gf = new GroupField(group,field,value);
			listGroupFields.add(gf);
			asociaciones.add(new AssociationAsset(asset,gf));
		}
		groupFieldService.addListGroupFields(listGroupFields);
		ac_assetService.addListAssociationsAsset(asociaciones);
		asset.getAsociaciones_asset().addAll(asociaciones);
		elasticService.index(session, asset.getCodigo(),asset.getDescripcion(),getContent());
	}
	
	private void fillMap() {
		map.clear();
		map.put("nCharacters", nCaracteres);
		map.put("nWords", nWords);
		map.put("nLines", nLines);
	}
	
	private List<String> getFieldsMetadata(){
		List<String> list = new ArrayList<String>();
		list.add("nCharacters");
		list.add("nWords");
		list.add("nLines");
		return list;
	}
	
	private List<String> getValuesMetadata(){
		List<String> list = new ArrayList<String>();
		list.add(String.valueOf(nCaracteres));
		list.add(String.valueOf(nWords));
		list.add(String.valueOf(nLines));
		return list;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
