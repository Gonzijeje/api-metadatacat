package com.tfg.controller;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tfg.model.Greeting;
import com.tfg.model.Metadato;
import com.tfg.services.MetadatoService;

@RestController
public class MetadatoController {
	
	@Autowired
	MetadatoService service;
	
	 private static final String template = "Hello, %s!";
	 private final AtomicLong counter = new AtomicLong();
	
	@RequestMapping("/greeting")
    public Greeting greeting(@RequestParam(value="name", defaultValue="World") String name) {
		System.out.println("cacaaaaa");
        return new Greeting(counter.incrementAndGet(),
                            String.format(template, name));
    }
	
	@RequestMapping(value = "/register", method = RequestMethod.POST, consumes = "application/json",
			produces = "application/json")
	public ResponseEntity<String> registerMetadata(@RequestBody Map<String, Object> payload ) {
		/*if (!isPayloadCorrect( payload )) {
			// Not valid data.
			log.warn( "Not valid register attemp: " + new JSONObject( payload ).toString() );
			return new ResponseEntity<String>( "{\"response\":\"agent not registered\"}",
					HttpStatus.BAD_REQUEST );
		}*/

		Metadato metadato = new Metadato( payload.get( "nombre" ).toString(),
				payload.get( "descripcion" ).toString());
		metadato.setCreateUser("gonzi");
		metadato.setCreateDate(new Date());
		
		service.add(metadato);

		System.out.print("Agent registered: " + new JSONObject( payload ).toString());
		
		return new ResponseEntity<String>( "{\"response\":\"metadata registered\"}",
				HttpStatus.CREATED );
	}

}
