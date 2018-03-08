package de.difuture.ekut.pht.trainupdater;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;

import de.difuture.ekut.pht.trainupdater.message.TrainUpdateStreams;

@SpringBootApplication
@EnableBinding(TrainUpdateStreams.class)
public class TrainupdaterApplication {

	public static void main(String[] args) {
		SpringApplication.run(TrainupdaterApplication.class, args);
	}
}
