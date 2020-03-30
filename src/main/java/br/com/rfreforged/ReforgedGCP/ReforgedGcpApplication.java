package br.com.rfreforged.ReforgedGCP;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ReforgedGcpApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReforgedGcpApplication.class, args);
	}

}
