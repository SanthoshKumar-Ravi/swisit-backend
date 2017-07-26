package com.stackroute.swisit.searcher.searcherservice;
/*------ Import Libraries ------*/
import java.util.concurrent.ExecutionException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.stackroute.swisit.searcher.domain.SearcherJob;
import com.stackroute.swisit.searcher.domain.SearcherResult;
import com.stackroute.swisit.searcher.exception.SearcherServiceException;
/*------- Interface that has method declaration for getting and saving searcher result ------*/
public interface SearcherService {
	public Iterable<SearcherResult> saveAllSearcherResult(SearcherJob searcherJob) throws JsonProcessingException, InterruptedException, ExecutionException;
	public Iterable<SearcherResult> getAllSearcherResult();
}
