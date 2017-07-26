package com.stackroute.swisit.test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.netflix.governator.annotations.binding.Input;
import com.stackroute.swisit.termbank.assembler.HateoasLinkAssembler;
import com.stackroute.swisit.termbank.controller.TermBankRestController;
import com.stackroute.swisit.termbank.domain.TermToIntentRelation;
import com.stackroute.swisit.termbank.repository.Neo4jRepository;
import com.stackroute.swisit.termbank.repository.TermRepository;
import com.stackroute.swisit.termbank.service.MappingAnyJsonHttpMessageConverter;
import com.stackroute.swisit.termbank.service.TermBankService;
import com.stackroute.swisit.termbank.service.TermBankServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = TermBankRestController.class)
@EnableWebMvc
public class TermBankApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;	

	@Autowired
	private TermBankService termBankService;

	@Autowired
	private TermBankServiceImpl termBankServiceImpl;

	@MockBean
	private MappingAnyJsonHttpMessageConverter  mappingAnyJsonHttpMessageConverter;

	@MockBean
	private Neo4jRepository neo4jRepository;

	@MockBean
	private TermRepository termRepository;

	@MockBean
	private TermToIntentRelation termToIntentRelation;

	@MockBean
	private Input input;

	@MockBean
	private HateoasLinkAssembler hateoasLinkAssembler; 

	@InjectMocks
	private TermBankRestController termBankRestController;

	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

	}

	@Test
	public void getTerms() throws Exception
	{


		mockMvc.perform(get("/v1/api/termbank/terms/"))
		.andExpect(status().isOk());
	}




	@Test

	public void getAllWords() throws Exception
	{
		String terms = "how to code";

		mockMvc.perform(get("/v1/api/termbank/terms/{terms}"))
		.andExpect(status().isOk());
	}

	@Test

	public void saveNewTerms() throws Exception
	{


		mockMvc.perform(post("/v1/api/termbank/terms/"))
		.andExpect(status().isOk());
	}


}