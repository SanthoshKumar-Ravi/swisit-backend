package com.stackroute.swisit.termbank.controller;

import com.stackroute.swisit.termbank.assembler.HateoasLinkAssembler;
import com.stackroute.swisit.termbank.domain.Input;
import com.stackroute.swisit.termbank.domain.ResponsiveBean;
import com.stackroute.swisit.termbank.domain.TermToIntentRelation;

/*--------- Importing Libraries---------------*/

import com.stackroute.swisit.termbank.service.TermBankService;
import com.stackroute.swisit.termbank.service.TermBankServiceImpl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*----------------------Rest API Controller Class------------------------*/
@RestController
@RequestMapping(value="/v1/api/termbank/")
@Api(value="TermBankRestController", description="Operations pertaining to the TermBankService")
public class TermBankRestController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	/*----------Autowired Instances of Classes----------*/

	@Autowired
	private HateoasLinkAssembler hateoasLinkAssembler;

	@Autowired
	TermBankServiceImpl termBankService;

	@Autowired
	ResponsiveBean responsiveBean;


	/*----------------Swagger API Operations-----------------*/
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "The resource you were trying to reach is not found")
	})



	/*---------------REST Controller to getTerms from the api -----------------*/
	@CrossOrigin(origins = "http://localhost:4200")
	@RequestMapping(value="terms" , method=RequestMethod.GET)
	public ResponseEntity<List> getTerms(){
		
		List termList= termBankService.getTerms();
		List list=hateoasLinkAssembler.getTerms(termList);
		return new ResponseEntity(termList,HttpStatus.OK);
	}


	/*----------------Swagger API Operations-----------------*/
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successfully retrieved Crawler"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
	})
	/*---------------REST Controller to return the wordlist to angular-----------------*/
	@CrossOrigin(origins = "http://localhost:4200")
	@RequestMapping(value="{terms}" , method=RequestMethod.GET)
	public List<ResponsiveBean> getAllWords(@PathVariable String terms){
		List<ResponsiveBean> wordsList = null;
		try
		{
			wordsList =  (List<ResponsiveBean>) termBankService.getWords(terms);
			
		}
		catch(Exception e)
		{
			return null;
		}
		return wordsList;

	}

	/* To post the synonyms and antonyms with respective relationship and weight into Neo4j database */
	@CrossOrigin(origins = "http://localhost:4200")
	@RequestMapping(value="" , method=RequestMethod.POST)
	public ResponseEntity saveNewTerms(@RequestBody Input input)
	{
		termBankService.insertData(input);
		hateoasLinkAssembler.getWords(input);
		return new ResponseEntity("inserted",HttpStatus.OK);
		
	}

}
