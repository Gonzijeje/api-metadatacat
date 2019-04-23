package com.tfg;
import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.tfg.es.Client;
import com.tfg.es.Search;

/**
 * 
 * @author gcollada
 *
 */

@SpringBootApplication
public class Application {
	
	static Client es = new Client();
	static Search se = new Search();

	public static void main(String[] args) throws IOException {
		SpringApplication.run(Application.class, args);
		es.connect();
		//se.matchAll();
		es.indexApi();
		es.disconnect();
	}

}
