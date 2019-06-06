package com.tfg.azure;

import java.io.IOException;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tfg.services.CSVReader;

@RestController
public class DataLake {
	
	@Autowired
	DataLakeService dlService;
	
	@Autowired
	CSVReader csvReader;
	
	@RequestMapping(value = "/azure/download", method = RequestMethod.GET)
	public void download(HttpSession session, @RequestParam String FileName) throws IOException {
		dlService.download(session, FileName);
		csvReader.read("src/main/resources/"+FileName);
	}

    

}
