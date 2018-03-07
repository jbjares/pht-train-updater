package de.difuture.ekut.pht.trainupdater.model.dockerevent;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;


@Data
public final class DockerRegistryEvent {
	
	@JsonProperty("id")
	private UUID id;
	
	@JsonProperty("action")
	private DockerRegistryEventAction action;
	
	@JsonProperty("target")
	private DockerRegistryEventTarget target;
	
	@JsonProperty("request")
	private DockerRegistryEventRequest request;
	
	@JsonProperty("source")
	private DockerRegistryEventSource source;
}
