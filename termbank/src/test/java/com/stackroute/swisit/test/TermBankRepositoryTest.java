package com.stackroute.swisit.test;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.stackroute.swisit.termbank.assembler.HateoasLinkAssembler;
import com.stackroute.swisit.termbank.controller.TermBankRestController;
import com.stackroute.swisit.termbank.domain.Input;
import com.stackroute.swisit.termbank.domain.ResponsiveBean;
import com.stackroute.swisit.termbank.domain.TermToIntentRelation;
import com.stackroute.swisit.termbank.repository.Neo4jRepository;
import com.stackroute.swisit.termbank.repository.TermRepository;
import com.stackroute.swisit.termbank.service.MappingAnyJsonHttpMessageConverter;
import com.stackroute.swisit.termbank.service.TermBankService;
import com.stackroute.swisit.termbank.service.TermBankServiceImpl;
import  com.stackroute.swisit.termbank.domain.Term;
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = TermBankRestController.class)
@EnableWebMvc
public class TermBankRepositoryTest {
	
	@MockBean
	private TermBankService termBankService;
	
	@MockBean
	private MappingAnyJsonHttpMessageConverter  mappingAnyJsonHttpMessageConverter;
		
	@MockBean
	private Neo4jRepository neo4jRepository;
	
	@MockBean
	private TermRepository termRepository;
	
	@MockBean
	private TermBankServiceImpl termBankServiceImpl; 
	
	
	@MockBean
	private ResponsiveBean responsiveBean;
	
	@MockBean
	private TermToIntentRelation termToIntentRelation;
	
	@MockBean
	private Input input;
	
	@MockBean
	private HateoasLinkAssembler hateoasLinkAssembler; 
	
	@InjectMocks
	private TermBankRestController termBankRestController;
	
	@Test
	public void fetchIndicatorOf()
	{
		TermToIntentRelation termToIntentRelation = new TermToIntentRelation();
		termToIntentRelation.setIntentName("basics");
		termToIntentRelation.setRelName("indicatorOf");
		termToIntentRelation.setTermName("helloworld");
		termToIntentRelation.setWeight(7);
		List<HashMap<String,String>> map = neo4jRepository.fetchIndicatorOf();
		assertEquals("basics",termToIntentRelation.getIntentName());
		assertEquals("indicatorOf",termToIntentRelation.getRelName());
		assertEquals("helloworld",termToIntentRelation.getTermName());
		assertEquals(7,termToIntentRelation.getWeight());
		
	}
	
	@Test
	public void fetchCounterIndicatorOf()
	{
		TermToIntentRelation termToIntentRelation = new TermToIntentRelation();
		termToIntentRelation.setIntentName("basics");
		termToIntentRelation.setRelName("counterIndicatorOf");
		termToIntentRelation.setTermName("snip");
		termToIntentRelation.setWeight(2);
		List<HashMap<String,String>> map = neo4jRepository.fetchCounterIndicatorOf();
		assertEquals("basics",termToIntentRelation.getIntentName());
		assertEquals("counterIndicatorOf",termToIntentRelation.getRelName());
		assertEquals("snip",termToIntentRelation.getTermName());
		assertEquals(2,termToIntentRelation.getWeight());
		
	}
	
	   @Test
	    public void equalGraphsWhichMatchTerms(){

	        Term term = new Term();
	        term.setName("tutorials");
	        List<Term> termList = termRepository.findTerms();
	        Assert.assertEquals("tutorials", term.getName());
	    }
	   
	   @Test
	   public void insertIndicatorOf()
	   {
		   TermToIntentRelation termToIntentRelation = new TermToIntentRelation();
			termToIntentRelation.setIntentName("basics");
			termToIntentRelation.setRelName("indicatorOf");
			termToIntentRelation.setTermName("helloworld");
			termToIntentRelation.setWeight(7);
		    termRepository.insertIndicatorOf("helloworld","basics","7");
			assertEquals("basics",termToIntentRelation.getIntentName());
			assertEquals("indicatorOf",termToIntentRelation.getRelName());
			assertEquals("helloworld",termToIntentRelation.getTermName());
			assertEquals(7,termToIntentRelation.getWeight());
	   }
	   
	   @Test
	   public void insertCounterIndicatorOf()
	   {
		   TermToIntentRelation termToIntentRelation = new TermToIntentRelation();
			termToIntentRelation.setIntentName("basics");
			termToIntentRelation.setRelName("counterIndicatorOf");
			termToIntentRelation.setTermName("snip");
			termToIntentRelation.setWeight(2);
			termRepository.insertCounterIndicatorOf("snip","basics","2");
			assertEquals("basics",termToIntentRelation.getIntentName());
			assertEquals("counterIndicatorOf",termToIntentRelation.getRelName());
			assertEquals("snip",termToIntentRelation.getTermName());
			assertEquals(2,termToIntentRelation.getWeight());
			
	   }

}

