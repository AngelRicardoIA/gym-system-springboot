package com.angel.GymSystem;

import com.angel.GymSystem.service.IClienteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GymSystemApplication {

	@Autowired
	private IClienteService clienteService;

	private static final Logger logger =
			LoggerFactory.getLogger(GymSystemApplication.class);

	public static void main(String[] args){
		logger.info("Starting Application");
		SpringApplication.run(GymSystemApplication.class, args);
		logger.info("Aplication completed");
	}

}
