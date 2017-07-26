package com.stackroute.swisit.searcher.controller;

/*---------- Import Libraries ----------*/
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.stackroute.swisit.searcher.domain.SavingSearcherResult;
import com.stackroute.swisit.searcher.domain.SearcherJob;
import com.stackroute.swisit.searcher.exception.SearcherServiceException;
import com.stackroute.swisit.searcher.hateoes.HateoesAssembler;
import com.stackroute.swisit.searcher.intialconsumer.IntialConsumer;
import com.stackroute.swisit.searcher.intialproducer.IntialProducer;
import com.stackroute.swisit.searcher.loadbalancing.LoadBalancing;
import com.stackroute.swisit.searcher.repository.SearcherResultRepository;
import com.stackroute.swisit.searcher.searcherservice.SearcherServiceImpl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiResponse;

/*------ Controller class from which all requests are made -----*/
@RestController
@RequestMapping(value="v1/api/swisit/searcher")
@Api(value="SWIS-IT", description="Operations pertaining to the SearcherService")
public class SearcherController {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	MessageSource messageSource;

	@Autowired
	private SearcherServiceImpl searcherServiceImpl;

	@Autowired
	private  IntialProducer intialproducer;

    @Autowired
    private  IntialConsumer intialConsumer;

	@Autowired
	private HateoesAssembler hateoesAssembler;

	@Autowired
	LoadBalancing loadBalancing;
	
	@Autowired
	private SearcherResultRepository searcherResultRepository;

	/*-------- Hateoas Implementation -------*/
	List hateoasLink = null;
	List hateoasLinkRef = null;
	
	@Value("${initialtopic}")
	String initialtopic;
	
	/*------------------------ Swagger Implementation ---------------------------*/
	@ApiOperation(value = "View a list of URLs from Google")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    	}
    )
	/*------------------------To get data from Google API----------------------------------------------*/
	@RequestMapping(value="", method=RequestMethod.GET)
	public ResponseEntity<List<SavingSearcherResult>> getSearcherResult() {
		Locale locale = LocaleContextHolder.getLocale();
		try {
        		/*----- Get all data with hateoas link -----*/
        		List<SavingSearcherResult> searcherResultList = (List<SavingSearcherResult>) searcherServiceImpl.getAllSearcherResult();
        	    hateoasLink = hateoesAssembler.getAllLinks(searcherResultList);
        }
        catch(SearcherServiceException searcherServiceException) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return  new ResponseEntity(hateoasLink ,HttpStatus.OK);
	}

	/*------------------------Posting the data to mongo DB--------------------------*/
	@ApiOperation(value = "Posting the Domain and Concept")
	@RequestMapping(value="", method=RequestMethod.POST)
    @CrossOrigin
	public ResponseEntity saveSearcherJob(@RequestBody SearcherJob produceSearcherJob) throws SearcherServiceException, Exception {
		Locale locale = LocaleContextHolder.getLocale();
		/*----Used for producing dummy messages -----*/
        intialproducer.publishMessage(initialtopic, produceSearcherJob);
        /*------ Get message from kafka -----*/
        SearcherJob consumeSearcherJob = intialConsumer.listenMessage(initialtopic);
        logger.info(consumeSearcherJob.getDomain()+" "+consumeSearcherJob.getConcept());
        String domain = consumeSearcherJob.getDomain();
		List concept = consumeSearcherJob.getConcept();
		List<String> queryList=new ArrayList<String>();
		List<String> conceptList =  new ArrayList<String>();
		int flag = 0;
        try {
        		/*--- To find the given domain and concept already present in DB ----*/
        		if(searcherResultRepository.findAll().isEmpty()) {
        			 searcherServiceImpl.saveAllSearcherResult(consumeSearcherJob);
        			 hateoasLink = hateoesAssembler.getLinksPost();
                     return new ResponseEntity(hateoasLink,HttpStatus.OK);
        		} else {
        			for(SavingSearcherResult savingSearcherResult:searcherResultRepository.findAll()){
            			queryList.add(savingSearcherResult.getQuery());
        			}
        			/*----- Loop for different concept ------*/
        			for(int i=0;i<concept.size();i++) {
            			String query = domain+" "+concept.get(i);
            			/*----- Loop for checking the query with the database ------*/
            			for(int j=0;j<queryList.size();j++) {
            				if(query.equals(queryList.get(j))) {
            					flag++;
            				}
            				else {
            					flag=0;
            				}
            			}
            			/*----- Add concept to the list which is not present in database -----*/
            			if(flag==0) {
            				conceptList.add((String) concept.get(i));
            			}
					}
        			if(flag>0) {
        				hateoasLinkRef = hateoesAssembler.getLinksPostError();
        				return new ResponseEntity(hateoasLinkRef,HttpStatus.OK);
        			}
        			else {
        				consumeSearcherJob.setConcept(conceptList);
        				/*---- To send the details to CoreCrawler service -----*/
        				searcherServiceImpl.saveAllSearcherResult(consumeSearcherJob);
                        hateoasLink = hateoesAssembler.getLinksPost();
                        return new ResponseEntity(hateoasLink,HttpStatus.OK);
        			}
        		}
        }
        catch(SearcherServiceException searcherServiceException) {
			searcherServiceException.printStackTrace();
        }
		return new ResponseEntity(hateoasLink,HttpStatus.OK);
    }

	/*-----------------Produce message through kafka for load balancing------------------- */
	@RequestMapping(value="producer",method=RequestMethod.GET)
    public ResponseEntity producer() {
        logger.debug("load balancer");
		loadBalancing.loadProducer();
        return new ResponseEntity("Loadbalancing success for producer",HttpStatus.OK);
    }

	/*-----------------------consume message from kafka for load balancing------------------------------------ */
    @RequestMapping(value="consumer",method=RequestMethod.GET)
    public ResponseEntity consumer() {
        logger.debug("load consumer");
		loadBalancing.loadConsumer();
        return new ResponseEntity("Loadbalancing success for consumer",HttpStatus.OK);
        
    }
}