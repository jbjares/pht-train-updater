package de.difuture.ekut.pht.trainupdater.message;

import java.net.URI;
import java.util.UUID;

import lombok.Value;

@Value
public class TrainAvailableMessage {

	// ID of the available train
	UUID trainID;
	
	// Registry host
	URI host;	
}
