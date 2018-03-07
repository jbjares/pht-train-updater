package de.difuture.ekut.pht.trainupdater.model.api;

import java.util.Set;
import java.util.UUID;

import lombok.Data;

@Data
public final class Train {

	private UUID id;
	
	private String sparql;
	
	private TrainState trainState;
	
	private Set<UUID> stations_visited;
}
