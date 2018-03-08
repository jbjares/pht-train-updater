package de.difuture.ekut.pht.trainupdater.controller;


import java.net.URI;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

import de.difuture.ekut.pht.trainupdater.message.TrainAvailableMessage;
import de.difuture.ekut.pht.trainupdater.message.TrainUpdateStreams;
import de.difuture.ekut.pht.trainupdater.model.dockerevent.DockerRegistryEvent;
import de.difuture.ekut.pht.trainupdater.model.dockerevent.DockerRegistryEventAction;
import de.difuture.ekut.pht.trainupdater.model.dockerevent.DockerRegistryEventCollection;
import de.difuture.ekut.pht.trainupdater.model.dockerevent.DockerRegistryEventTarget;
import lombok.NonNull;


@RestController
public class TrainUpdaterController {

	private static final ResponseEntity<?> OK = ResponseEntity.ok().build();

	private final DiscoveryClient discoveryClient;
	private final TrainUpdateStreams trainUpdateStreams;
	
	
	@Autowired
	public TrainUpdaterController(
			@NonNull final DiscoveryClient discoveryClient,
			@NonNull final TrainUpdateStreams trainUpdateStreams) {

		this.discoveryClient = discoveryClient;
		this.trainUpdateStreams = trainUpdateStreams;
	}

	
	private boolean broadcastTrainAvailable(final UUID trainID, final URI host) {
		
		final TrainAvailableMessage payload = new TrainAvailableMessage(trainID, host);
		final Message<TrainAvailableMessage> message = MessageBuilder
				.withPayload(payload)
				.setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON)
				.build();

		return this.trainUpdateStreams.outboundTrainUpdate().send(message);
	}


	@RequestMapping(value = "/listener", method = RequestMethod.POST)	
	public ResponseEntity<?> listener(@RequestBody DockerRegistryEventCollection events) throws JsonProcessingException {

		for (final DockerRegistryEvent event: events) {

			// Sends a train available message if
			// * Docker Registry Event Action is Push
			// * The tag is not null
			final DockerRegistryEventTarget target = event.getTarget();
			
			if (event.getAction() == DockerRegistryEventAction.PUSH && target.getTag() != null) {
			
				this.broadcastTrainAvailable(
						target.getRepository(),
						event.getRequest().getHost());
			}
		}
		return OK;
	}
}
