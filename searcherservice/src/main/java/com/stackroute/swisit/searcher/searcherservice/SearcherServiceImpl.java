package com.stackroute.swisit.searcher.searcherservice;
/*------ Import Libaraies -------*/
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackroute.swisit.searcher.domain.SearcherResponse;
import com.stackroute.swisit.searcher.domain.SavingSearcherResult;
import com.stackroute.swisit.searcher.domain.SearcherJob;
import com.stackroute.swisit.searcher.domain.SearcherResult;
import com.stackroute.swisit.searcher.exception.SearcherServiceException;
import com.stackroute.swisit.searcher.messageservice.MessageService;
import com.stackroute.swisit.searcher.messageservice.MessageServiceImpl;
import com.stackroute.swisit.searcher.publisher.Publisher;
import com.stackroute.swisit.searcher.repository.SearcherResultRepository;

/*------- Class to implement searcher service interface to get and save searcher service result -------*/
@Service
public class SearcherServiceImpl implements SearcherService {
	
	@Autowired
	private SearcherResultRepository searcherResultRepository;

	@Autowired
	Publisher publisher;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/*----- Used to get the values from application.yml -----*/
	@Value("${url}")
	String url;

	@Value("${key}")
	String key;
	
	@Value("${publishertopic}")
	String publishertopic;

	String domain="";
	String url1;
	String url2;
	List<String> concept;
	List<LinkedHashMap<String,String>> engineid = new ArrayList<LinkedHashMap<String,String>>();

	/*----- To Make the Google Api Async -----*/
	SearcherServiceAsync searchServiceAsync = new SearcherServiceAsync();
	
	/*----- Save the search result for the query into SearchResult class -----*/
	@Override
	public List saveAllSearcherResult(SearcherJob searcherJob) throws JsonProcessingException, InterruptedException, ExecutionException {
		
		CompletableFuture<SearcherResponse> searcherResponse=null;
		List<SearcherResult> searcherResultList = new ArrayList<SearcherResult>();
		ObjectMapper mapper = new ObjectMapper();
		File file = new File("./src/main/resources/common/googleEngine.json");
		List<LinkedHashMap<String, String>> endigeIdList;
		try {
			endigeIdList = (List<LinkedHashMap<String,String>>) mapper.readValue(file, ArrayList.class);
			searcherJob.setEngineId(endigeIdList);
		} catch (JsonMappingException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		/* Get the data from the SearchJob class */
		try {
			if(searcherJob.getDomain()==null || searcherJob.getConcept()==null) {
				throw new SearcherServiceException("SearcherJob has no value");
			}
			else {
				domain = searcherJob.getDomain();
				concept = searcherJob.getConcept();
				for(int k=0;k<concept.size();k++) {
					String query = domain+" "+concept.get(k);
					engineid = searcherJob.getEngineId();
					for(Map<String, String> map : engineid){
						url1=map.get(key);
						break;
					}
					SavingSearcherResult savingSearcherResult =  new SavingSearcherResult();
					for(int i=1;i<=1;i=i+10) {
						url2 = query+"&start="+i+url1;
						String finalUrl = url+url2;
						searcherResponse = (CompletableFuture<SearcherResponse>) searchServiceAsync.getSearchResult(finalUrl);
						/*---- Setting the query from the Google Api ----*/
						savingSearcherResult.setQuery(searcherResponse.get().getQueries());
						/* --Set the values to SearcherResult class and send the object to crawlerService via Kafka --*/
							try {
								for(SearcherResult searcherResultRef:searcherResponse.get().getS()) {
									SearcherResult searcherResult =  new SearcherResult();
									searcherResult.setUrl(searcherResultRef.getUrl());
									searcherResult.setTitle(searcherResultRef.getTitle());
									searcherResult.setDescription(searcherResultRef.getDescription());
									searcherResult.setConcept(concept.get(k));
									searcherResultList.add(searcherResult);
									try {
										/*--- Publish the searcherResult object to kafka ---*/
										publisher.publishMessage(publishertopic, searcherResult);
									} 
									catch (JsonProcessingException e) {
										e.printStackTrace();
									}
								}
							}
							catch(SearcherServiceException searcherServiceException) {
                                searcherServiceException.printStackTrace();
							}
						}
						savingSearcherResult.setSearcherResults(searcherResultList);
						/*--- Save SearcherResult to mongoDB ---*/
						searcherResultRepository.save(savingSearcherResult);
						searcherResultList.clear();
					}
				}
		}
		catch(SearcherServiceException searcherServiceException) {
			logger.error("Exception "+searcherServiceException.getMessage());
		}
	return searcherResultRepository.findAll();
	}

	/*--- Get all data from the SearchResult class ----*/
	@Override
	public List getAllSearcherResult() {
		try {
			if(searcherResultRepository.findAll()==null) {
				throw new SearcherServiceException("No data available");
			}
			else if(domain==null) {
				throw new SearcherServiceException("Domain was not Found");
			}
			else {
				/*--- Method to get all data from the database ---*/
				searcherResultRepository.findAll();
			}
		}
		catch(SearcherServiceException searcherServiceException) {
			logger.error("Exception"+searcherServiceException.getMessage());
		}
	return searcherResultRepository.findAll();
	}
}