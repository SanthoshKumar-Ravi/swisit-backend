package com.stackroute.swisit.documentparser.publisher;

/*----------------- Importing Libraries ----------------*/
import com.fasterxml.jackson.core.JsonProcessingException;
import com.stackroute.swisit.documentparser.domain.DocumentParserResult;


/*--- Publisher interface that declares method to publish message via a messaging service ---*/
public interface Publisher {
	
	/*------------- Method to publish message via a messaging service--------------*/
	public void publishMessage(String brokerid, String topicName,DocumentParserResult documentParserResult) throws JsonProcessingException;
}
