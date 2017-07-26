package com.stackroute.swisit.searcher.messageservice;
/*----- Import Libraries ------*/
import com.fasterxml.jackson.core.JsonProcessingException;
import com.stackroute.swisit.searcher.domain.SearcherResult;
/*---- Interface to listen and publish message via messaging service ----*/
public interface MessageService {
	public void publishMessage(String topic,SearcherResult message) throws JsonProcessingException;
	public void listenMessage(String topic);
}
