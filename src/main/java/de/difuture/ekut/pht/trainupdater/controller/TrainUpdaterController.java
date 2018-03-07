package de.difuture.ekut.pht.trainupdater.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

import de.difuture.ekut.pht.trainupdater.model.dockerevent.DockerRegistryEvent;
import de.difuture.ekut.pht.trainupdater.model.dockerevent.DockerRegistryEventCollection;


@RestController
public class TrainUpdaterController {

	private static final ResponseEntity<?> OK = ResponseEntity.ok().build();
	
	@RequestMapping(value = "/listener", method = RequestMethod.POST)	
	public ResponseEntity<?> listener(@RequestBody DockerRegistryEventCollection events) throws JsonProcessingException {
				
		for (final DockerRegistryEvent event: events) {
			
			System.out.println(event);
		}
		return OK;
	}
}
