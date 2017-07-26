package com.stackroute.swisit.searcher.publisher;
/*----- Import Libraries ------*/
import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.stackroute.swisit.searcher.domain.SearcherResult;
/*----- Class to publish message via kafka ------*/
@Service
public class PublisherImpl implements Publisher {

	@Value("${brokerid}")
	String brokerid;
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public void publishMessage(String topic, SearcherResult message) throws JsonProcessingException {
		Properties configProperties = new Properties();
		/*---- Configure properties for Kafka -----*/
		configProperties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,brokerid);
		configProperties.put("key.serializer","org.apache.kafka.common.serialization.ByteArraySerializer");
		configProperties.put("value.serializer","com.stackroute.swisit.searcher.kafkaserialization.SwisitSerializer");
		Producer producer = new KafkaProducer(configProperties);
		ProducerRecord<String, SearcherResult> producerRecord = new ProducerRecord<String, SearcherResult>(topic,message);
		producer.send(producerRecord);
		producer.close();
	}
}