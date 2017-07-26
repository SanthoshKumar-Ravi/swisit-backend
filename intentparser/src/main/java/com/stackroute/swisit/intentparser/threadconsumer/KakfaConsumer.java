package com.stackroute.swisit.intentparser.threadconsumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

//import com.stackroute.swisit.crawler.service.MasterScannerService;
import com.stackroute.swisit.intentparser.service.IntentParseAlgo;
@Service
public class KakfaConsumer {

	@Autowired
	IntentParseAlgo intentParseAlgo;
	@Autowired
	private Environment environment;

	public void consumeMessage() {
		String topic = environment.getProperty("topic-fromconsumer");
		String brokerid = environment.getProperty("brokerid");

		KafkaConsumerThread consumerRunnable = new KafkaConsumerThread(brokerid,topic, intentParseAlgo);
		consumerRunnable.start();
		consumerRunnable.getKafkaConsumer().wakeup();
	}
}
