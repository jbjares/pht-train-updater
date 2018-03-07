package de.difuture.ekut.pht.trainupdater;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class TrainupdaterApplication {

	public static void main(String[] args) {
		SpringApplication.run(TrainupdaterApplication.class, args);
	}
}
