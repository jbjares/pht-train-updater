package de.difuture.ekut.pht.trainupdater.message;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface TrainUpdateStreams {

	String OUTPUT = "trainupdate-out";

	@Output(OUTPUT)
	MessageChannel outboundTrainUpdate();
}
