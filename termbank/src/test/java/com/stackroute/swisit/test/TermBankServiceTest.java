package com.stackroute.swisit.test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.stackroute.swisit.termbank.domain.Input;
import com.stackroute.swisit.termbank.assembler.HateoasLinkAssembler;
import com.stackroute.swisit.termbank.controller.TermBankRestController;
import com.stackroute.swisit.termbank.domain.Adjectives;
import com.stackroute.swisit.termbank.domain.Nouns;
import com.stackroute.swisit.termbank.domain.ResponsiveBean;
import com.stackroute.swisit.termbank.domain.TermToIntentRelation;
import com.stackroute.swisit.termbank.repository.Neo4jRepository;
import com.stackroute.swisit.termbank.repository.TermRepository;
import com.stackroute.swisit.termbank.service.MappingAnyJsonHttpMessageConverter;
import com.stackroute.swisit.termbank.service.TermBankService;
import com.stackroute.swisit.termbank.service.TermBankServiceImpl;

import io.swagger.models.auth.In;
import junit.framework.Assert;



@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = TermBankRestController.class)
@EnableWebMvc
public class TermBankServiceTest {
	

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
	public void  getTerms() throws Exception{
		
		TermToIntentRelation termToIntentRelation = new TermToIntentRelation();
		termToIntentRelation.setIntentName("example");
		termToIntentRelation.setRelName("indicatorOf");
		termToIntentRelation.setTermName("model");
		termToIntentRelation.setWeight(8);
		List<HashMap<String, String>> listIndicator =neo4jRepository.fetchIndicatorOf();
		assertEquals("example", termToIntentRelation.getIntentName());
		assertEquals("indicatorOf",termToIntentRelation.getRelName());
		assertEquals("model",termToIntentRelation.getTermName());
		assertEquals(8,termToIntentRelation.getWeight());
		
	}
	
	@Test
	public void getWords() throws Exception{
	
		Adjectives adjectives = new Adjectives();
		String[] ant = {"secondary"};
		String[] syn = {"main"};		
		adjectives.setAnt(ant);
		adjectives.setSyn(syn);
		Nouns nouns = new Nouns();
		String[] ant1 = {"inessential"};
		String[] syn1 = {"requirement"};
		
		ResponsiveBean responsiveBean = new ResponsiveBean();
		responsiveBean.setAdjectiveBean(adjectives);
		responsiveBean.setNounBean(nouns);
		assertEquals(adjectives,responsiveBean.getAdjectiveBean());
		assertEquals(nouns,responsiveBean.getNounBean());
		
	}

	
@Test

public void insertData()

{
	Input input = new Input();
	String[] antonyms = {"secondary"};
	input.setAntonyms(antonyms);
	String[] synonyms = {"main"};
	input.setSynonyms(synonyms);
	String[] antweight = {"4"};
	input.setAntweight(antweight);
	String[] synweight = {"8"};
	input.setSynweight(synweight);
	String[] intent = {"basics"};
	input.setIntent(intent);
	String[] relation = {"indicatorOf"};
	input.setRelation(relation);
	assertArrayEquals(antonyms,input.getAntonyms());
	assertArrayEquals(synonyms,input.getSynonyms());
	assertArrayEquals(antweight,input.getAntweight());
	assertArrayEquals(synweight,input.getSynweight());
	assertArrayEquals(intent,input.getIntent());
	assertArrayEquals(relation,input.getRelation());
	}
}
