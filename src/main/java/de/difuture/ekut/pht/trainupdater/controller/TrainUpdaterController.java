package de.difuture.ekut.pht.trainupdater.controller;


import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;

import de.difuture.ekut.pht.lib.core.dockerevent.DockerRegistryEvent;
import de.difuture.ekut.pht.lib.core.dockerevent.DockerRegistryEventIterable;
import de.difuture.ekut.pht.lib.core.messages.TrainAvailable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

import de.difuture.ekut.pht.trainupdater.message.TrainUpdateStreams;
import lombok.NonNull;


@RestController
public class TrainUpdaterController {

	private static final ResponseEntity<?> OK = ResponseEntity.ok().build();
	private final TrainUpdateStreams trainUpdateStreams;
	
	@Autowired
	public TrainUpdaterController(
			@NonNull final TrainUpdateStreams trainUpdateStreams) {

		this.trainUpdateStreams = trainUpdateStreams;
	}
	
	private boolean broadcastTrainAvailable(
			final UUID trainID,
			final URI trainRegistryURI,
			final URI pusherURI) {

		return this.trainUpdateStreams.outboundTrainAvailable().send(
				MessageBuilder
					.withPayload(new TrainAvailable(trainID, trainRegistryURI, pusherURI))
					.setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON)
					.build());
	}


	@RequestMapping(value = "/listener", method = RequestMethod.POST)	
	public ResponseEntity<?> listener(@RequestBody DockerRegistryEventIterable events) throws JsonProcessingException {

		for (final DockerRegistryEvent event: events) {

			final DockerRegistryEvent.Target target = event.getTarget();

			try {
                // Sends a train available message if
                // * Docker Registry Event Action is Push
                // * The tag is not null
                if (event.getAction() == DockerRegistryEvent.Action.PUSH && target.getTag() != null) {

                    this.broadcastTrainAvailable(
                            UUID.fromString(target.getRepository()),
                            event.getRequest().getHost(),
							new URI(event.getRequest().getAddr()));
                }

			} catch(final IllegalArgumentException | URISyntaxException e) {

				e.printStackTrace();
			    // TODO Currently ignore DockerRegistry events that do not belong to trains
                // TODO There should be some logging going on
			}
		}
		return OK;
	}
}
