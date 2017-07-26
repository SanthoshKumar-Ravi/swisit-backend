package com.stackroute.swisit.intentparser.threadconsumer;

import com.stackroute.swisit.intentparser.domain.CrawlerResult;
import com.stackroute.swisit.intentparser.service.IntentParseAlgo;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.core.env.Environment;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

public class KafkaConsumerThread extends Thread {
	private String topicName;
	private String groupId;
	private String Brokerid;
	private KafkaConsumer<String, CrawlerResult> kafkaConsumer;
	private IntentParseAlgo intentParseAlgo;
	private Environment environment;
	//----------------------------------logger implementation----------------------------------

	public KafkaConsumerThread(String Brokerid,String topicName, IntentParseAlgo intentParseAlgo ){
		this.Brokerid=Brokerid;
		this.topicName = topicName;
		this.intentParseAlgo = intentParseAlgo;
		}

	public void run() {
		Properties configProperties = new Properties();
		configProperties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,Brokerid);
		configProperties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
		configProperties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "com.stackroute.swisit.intentparser.serialization.CrawlerDeserializer");
		configProperties.put(ConsumerConfig.GROUP_ID_CONFIG, "group-1");
		Set<CrawlerResult> final_kafka=new HashSet<>();
		//Figure out where to start processing messages from
		kafkaConsumer = new KafkaConsumer<String, CrawlerResult>(configProperties);
		kafkaConsumer.subscribe(Arrays.asList(topicName));

		//Start processing messages
		while (true) {
			ConsumerRecords<String, CrawlerResult> records = kafkaConsumer.poll(10000);
			for (ConsumerRecord<String, CrawlerResult> record : records) {
				int count=0;
				try {
					final_kafka.add(record.value());
					intentParseAlgo.calculateConfidenceScore(record.value());
					count++;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

	}
	public KafkaConsumer<String,CrawlerResult> getKafkaConsumer()
	{
		return this.kafkaConsumer;
	}
}
