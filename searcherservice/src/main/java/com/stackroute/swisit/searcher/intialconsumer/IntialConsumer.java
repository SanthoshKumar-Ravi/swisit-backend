package com.stackroute.swisit.searcher.intialconsumer;
/*---- Import Libraries ----*/
import com.stackroute.swisit.searcher.domain.SearcherJob;
/*---- Kafka consumer used for SearcherJob ----*/
public interface IntialConsumer {
	public SearcherJob listenMessage(String topic);
}
