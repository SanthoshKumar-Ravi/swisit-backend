/*******This Class is used for Testing the Service only,
 * will finally be removed from the Final product******/
package com.stackroute.swisit.usersearchservice.controller;

/*-----Importing Libraries-----*/
import com.stackroute.swisit.usersearchservice.assembler.HeteoasLinkAssembler;
import com.stackroute.swisit.usersearchservice.domain.*;
import com.stackroute.swisit.usersearchservice.service.UserSearchService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;

/*----------------------Rest API Controller Class------------------------*/
@RestController
//@CrossOrigin
@RequestMapping(value = "v1/api/swisit/usersearch")
public class UserSearchServiceController {
    /*----------Autowired Instances of Classes----------*/
    @Autowired
    UserSearchService userSearchService;
    @Autowired
    HeteoasLinkAssembler heteoasLinkAssembler;
    @Autowired
    private MessageSource messageSource;

    /*----------------Swagger API Operations-----------------*/
    @ApiOperation(value="Fetch Neo4j Data",response =UserInput.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved Crawler"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    }
    )
/*----------- REST Controller To Show Data From Neo4j Database------------*/
    
    @CrossOrigin
    @RequestMapping(value="", method = RequestMethod.POST)
    public ResponseEntity getUserSearch(@RequestBody UserInput userInput) {
        /*------Locale which identify a specific language and geographic region, used for Internationalization-----*/
        Locale locale = LocaleContextHolder.getLocale();
        /*-------Resulted List from User Search Service-------*/
        List<UserSearchResult> userSearchResults = userSearchService.fetchNeoData(userInput);
        for(UserSearchResult userSearchResult:userSearchResults){
        	System.out.println(userSearchResult.getConfidenceScore());
        	System.out.println(userSearchResult.getUrl());
        }
        /*-------Hateoas Link Assembling to Response-------*/
        List<UserSearchResult> userSearchResultList=heteoasLinkAssembler.fetchNeoData(userSearchResults);
       /*------Try Catch block for Handling Exceptions-----*/
        try {
            if (userInput == null) {
                String message = messageSource.getMessage("user.excep.data", null, locale);
                return new ResponseEntity(message, HttpStatus.OK);
            }
        }
        catch(Exception e){
                e.printStackTrace();
            }
            return new ResponseEntity(userSearchResults, HttpStatus.OK);
        }

    @CrossOrigin
    @RequestMapping(value="/getConcepts", method = RequestMethod.POST)
    public ResponseEntity getConcept(@RequestBody String s) {
        /*------Locale which identify a specific language and geographic region, used for Internationalization-----*/
        Locale locale = LocaleContextHolder.getLocale();
        /*-------Resulted List from User Search Service-------*/
        List<String> conceptResults = userSearchService.fetchConcept();

        return new ResponseEntity(conceptResults, HttpStatus.OK);
    }
    
    @CrossOrigin
    @RequestMapping(value="/getTerms", method = RequestMethod.GET)
    public ResponseEntity getTerms() {
        /*------Locale which identify a specific language and geographic region, used for Internationalization-----*/
        Locale locale = LocaleContextHolder.getLocale();
        /*-------Resulted List from User Search Service-------*/
        List<String> termResults = new ArrayList<String>();
        termResults = userSearchService.fetchTerm();

        return new ResponseEntity(termResults, HttpStatus.OK);
    }
}


