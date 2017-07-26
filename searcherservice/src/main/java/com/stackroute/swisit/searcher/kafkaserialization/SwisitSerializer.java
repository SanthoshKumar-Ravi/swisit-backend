package com.stackroute.swisit.searcher.kafkaserialization;
/*------ Import Libraries -----*/
import java.util.Map;

import org.apache.kafka.common.serialization.Serializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackroute.swisit.searcher.domain.SearcherResult;

/* Serializing the searcherResult bean */
public class SwisitSerializer implements Serializer<SearcherResult> {

	@Override
	public void close() {
		
	}

	@Override
	public void configure(Map<String, ?> arg0, boolean arg1) {
		
	}
	/*---- Serialize message from SearcherResult ----*/
	@Override
	public byte[] serialize(String arg0, SearcherResult arg1) {
		byte[] retVal = null;
	    ObjectMapper objectMapper = new ObjectMapper();
	    try {
	      retVal = objectMapper.writeValueAsString(arg1).getBytes();
	    } catch (Exception e) {
	      e.printStackTrace();
	    }
	    return retVal;
	}
}