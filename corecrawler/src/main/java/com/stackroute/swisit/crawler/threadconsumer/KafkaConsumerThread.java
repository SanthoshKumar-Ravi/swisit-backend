package com.stackroute.swisit.crawler.threadconsumer;

import java.util.Arrays;

import java.util.Properties;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.stackroute.swisit.crawler.domain.SearcherResult;
import com.stackroute.swisit.crawler.service.MasterScannerService;

public class KafkaConsumerThread extends Thread {
	
	private String topicName;
	private String brokerid;
	private KafkaConsumer<String, SearcherResult> kafkaConsumer;
	private MasterScannerService masterScannerService;

	public KafkaConsumerThread(String topicName, MasterScannerService masterScannerService,String brokerid ){
		this.topicName = topicName;
		this.masterScannerService = masterScannerService;
		this.brokerid = brokerid;
	}

	public void run() {
		Properties configProperties = new Properties();
		configProperties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,brokerid);
		configProperties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
		configProperties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "com.stackroute.swisit.crawler.domain.SearcherResult");
		configProperties.put("group.id", "group-1");

		/*Figure out where to start processing messages from*/

		kafkaConsumer = new KafkaConsumer<String, SearcherResult>(configProperties);
		kafkaConsumer.subscribe(Arrays.asList(topicName));

		/*Start processing messages*/

		while (true) {
			ConsumerRecords<String, SearcherResult> records = kafkaConsumer.poll(10000);
			for (ConsumerRecord<String, SearcherResult> record : records) {
				SearcherResult searcherResult = new SearcherResult();
				searcherResult = record.value(); 
				try {
					masterScannerService.scanDocument(searcherResult);
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		}

	}
	public KafkaConsumer<String,SearcherResult> getKafkaConsumer() {
		return this.kafkaConsumer;
	}
}
