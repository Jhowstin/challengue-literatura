package com.challengue.literatura.challengue_literatura;

import com.challengue.literatura.challengue_literatura.principal.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ChallengueLiteraturaApplication implements CommandLineRunner {

	@Autowired
	private Principal principal;

	public static void main(String[] args) {
		SpringApplication.run(ChallengueLiteraturaApplication.class, args);
	}

	@Override
	public void run(String... args) {
		principal.muestraMenu();
	}
}

