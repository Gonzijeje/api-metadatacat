package com.tfg;
import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.tfg.controller.elastic.ElasticController;
import com.tfg.services.elastic.ElasticService;

/**
 * 
 * @author gcollada
 *
 */

@SpringBootApplication
public class Application {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(Application.class, args);
	}

}
