package de.difuture.ekut.pht.trainupdater.controller;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;

import de.difuture.ekut.pht.trainupdater.model.api.Train;
import de.difuture.ekut.pht.trainupdater.model.api.TrainStateRequest;
import de.difuture.ekut.pht.trainupdater.model.dockerevent.DockerRegistryEvent;
import de.difuture.ekut.pht.trainupdater.model.dockerevent.DockerRegistryEventAction;
import de.difuture.ekut.pht.trainupdater.model.dockerevent.DockerRegistryEventCollection;
import lombok.NonNull;


@RestController
public class TrainUpdaterController {

	private static final ResponseEntity<?> OK = ResponseEntity.ok().build();

	private final DiscoveryClient discoveryClient;

	@Autowired
	public TrainUpdaterController(
			@NonNull final DiscoveryClient discoveryClient) {

		this.discoveryClient = discoveryClient;
	}

	// Contact train central to set the train state to updated
	private Optional<Train> updateTrain(DockerRegistryEvent event) {

		final Optional<Train> empty = Optional.empty();
		final TrainStateRequest request = new TrainStateRequest(event.getTarget().getRepository());

		// TODO Singleton service
		final List<ServiceInstance> trainCentrals = this.discoveryClient.getInstances("traincentral");
		
		if (trainCentrals.isEmpty()) {
			
			return empty;
		}
		final ServiceInstance trainCentral = trainCentrals.get(0);
		
		final Train response =  (new RestTemplate())
				.postForObject(trainCentral.getUri() + "/train/upload", request, Train.class);
		return Optional.of(response);
	}



	@RequestMapping(value = "/listener", method = RequestMethod.POST)	
	public ResponseEntity<?> listener(@RequestBody DockerRegistryEventCollection events) throws JsonProcessingException {

		for (final DockerRegistryEvent event: events) {

			// A push event will update the train in traincentral
			if (event.getAction() == DockerRegistryEventAction.PUSH) {
				
				this.updateTrain(event);
			}
			
		}
		return OK;
	}
}
