package de.difuture.ekut.pht.trainupdater.model.dockerevent;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum DockerRegistryEventAction {

	PULL,
	PUSH,
	MOUNT;

	@JsonCreator
	public static DockerRegistryEventAction forValue(final String value) {
		return DockerRegistryEventAction.valueOf(value.toUpperCase());
	}
}
