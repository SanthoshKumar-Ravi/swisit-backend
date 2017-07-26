package com.stackroute.swisit.searcher.intialconsumer;
/*---- Import Libraries ----*/
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.stackroute.swisit.searcher.domain.SearcherJob;
@Service
public class IntialConsumerImpl implements IntialConsumer{
	@Value("${brokerid}")
	String brokerid;
	/*--- Kafka consumer for searcherJob ---*/
	@Override
	public SearcherJob listenMessage(String topic) {
			Properties props = new Properties();
			/*--- Configuring the properties for kafka ----*/
			props.put("group.id", "group-1");
		    props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,brokerid);
			props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		    props.put("value.deserializer", "com.stackroute.swisit.searcher.kafkaserialization.QueryDeserializer");
		    List<SearcherJob> l=new ArrayList<SearcherJob>();
		    /*--- Initial consumer is used to consume data from kafka ---*/
		    KafkaConsumer<String, SearcherJob> kafkaConsumer = new KafkaConsumer<>(props);
		    kafkaConsumer.subscribe(Arrays.asList(topic));
		    SearcherJob searcherJob=new SearcherJob();
		    while (true) {
		      ConsumerRecords<String, SearcherJob> records = kafkaConsumer.poll(10000);
		      for (ConsumerRecord<String, SearcherJob> record : records) {
				  searcherJob.setDomain(record.value().getDomain());
				  searcherJob.setResults(record.value().getResults());
				  searcherJob.setConcept(record.value().getConcept());
				  searcherJob.setSitesearch(record.value().getSitesearch());
		      }
		return searcherJob;
		}
	}
}