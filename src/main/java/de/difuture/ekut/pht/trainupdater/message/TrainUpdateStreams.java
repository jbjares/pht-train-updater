package de.difuture.ekut.pht.trainupdater.message;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface TrainUpdateStreams {

	// TODO Move to centralized configuration
	String TRAIN_AVAILABLE = "trainavailable";

	@Output(TRAIN_AVAILABLE)
	MessageChannel outboundTrainAvailable();
}
