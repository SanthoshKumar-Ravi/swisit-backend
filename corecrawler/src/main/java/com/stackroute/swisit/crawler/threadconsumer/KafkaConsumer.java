package com.stackroute.swisit.crawler.threadconsumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.stackroute.swisit.crawler.service.MasterScannerService;
@Service
@PropertySource("classpath:application.yml")
public class KafkaConsumer {
	
	@Autowired
	private Environment environment;

	@Autowired
	MasterScannerService masterScannerService;

	public void consumeMessage(){
		String consumertopic = environment.getProperty("topic-fromconsumer");
		String brokerid = environment.getProperty("brokerid");
		KafkaConsumerThread consumerRunnable = new KafkaConsumerThread(consumertopic,masterScannerService,brokerid);
		consumerRunnable.start();
		consumerRunnable.getKafkaConsumer().wakeup();
	}
}
