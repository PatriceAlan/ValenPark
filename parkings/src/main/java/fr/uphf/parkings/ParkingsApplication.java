package fr.uphf.placeparking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ParkingsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ParkingsApplication.class, args);
	}

}
