package com.stackroute.swisit.searcher.intialproducer;
/*------ Import Libraries ------*/
import com.fasterxml.jackson.core.JsonProcessingException;
import com.stackroute.swisit.searcher.domain.SearcherJob;
/*--- Kafka producer for searcherJob ----*/
public interface IntialProducer {
	public void publishMessage(String topic,SearcherJob message) throws JsonProcessingException;
}
