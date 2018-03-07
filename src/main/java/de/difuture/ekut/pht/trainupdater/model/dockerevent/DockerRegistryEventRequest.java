package de.difuture.ekut.pht.trainupdater.model.dockerevent;

import java.net.URI;

import org.springframework.web.bind.annotation.RequestMethod;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public final class DockerRegistryEventRequest {

	@JsonProperty("id")
	private String id;
	
	@JsonProperty("addr")
	private String addr;
	
	@JsonProperty("host")
	private URI host;
	
	@JsonProperty("method")
	private RequestMethod method;
	
	@JsonProperty("useragent")
	private String useragent;
}
