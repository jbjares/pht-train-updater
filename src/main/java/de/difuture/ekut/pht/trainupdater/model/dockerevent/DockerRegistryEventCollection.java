package de.difuture.ekut.pht.trainupdater.model.dockerevent;

import java.util.Iterator;

import com.fasterxml.jackson.annotation.JsonProperty;

public final class DockerRegistryEventCollection implements Iterable<DockerRegistryEvent> {

	@JsonProperty("events")
	private Iterable<DockerRegistryEvent> events;

	@Override
	public Iterator<DockerRegistryEvent> iterator() {
	
		return this.events.iterator();
	}
}
