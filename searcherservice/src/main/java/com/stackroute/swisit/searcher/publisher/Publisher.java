package com.stackroute.swisit.searcher.publisher;
/*----- Import Libraries ------*/
import com.fasterxml.jackson.core.JsonProcessingException;
import com.stackroute.swisit.searcher.domain.SearcherResult;
/*----- Interface to publish message via messaging service ------*/
public interface Publisher {
	public void publishMessage(String topic, SearcherResult message) throws JsonProcessingException;
}
