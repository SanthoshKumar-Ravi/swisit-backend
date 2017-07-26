package com.stackroute.swisit.termbank.service;

/*-------Importing Libraries------*/
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackroute.swisit.termbank.domain.Adjectives;
import com.stackroute.swisit.termbank.domain.Input;
import com.stackroute.swisit.termbank.domain.Nouns;
import com.stackroute.swisit.termbank.domain.ResponsiveBean;
import com.stackroute.swisit.termbank.domain.Term;
import com.stackroute.swisit.termbank.domain.TermToIntentRelation;
import com.stackroute.swisit.termbank.repository.Neo4jRepository;
import com.stackroute.swisit.termbank.repository.TermRepository;

@Service
public class TermBankServiceImpl implements TermBankService {
	
    /*------- Mapping the url value from config server-------*/
	@Value("${apiurl}")
	String apiurl;

    /*-------Autowired Repositories and Bean classes------*/
	@Autowired
	ResponsiveBean responsiveBean;

	@Autowired
	Neo4jRepository neo4jRepository;
	
	@Autowired
	TermRepository termRepository;

	@Autowired
	TermToIntentRelation words;
	
    /*--------TermBankServiceImpl Properties-------*/
	String[] adjSynList;
	String[] adjAntList;
	String[] nounSynList;
	String[] nounAntList;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	Nouns noun =new Nouns();
	Adjectives adjective = new Adjectives();
	Term term = new Term();
	
	/*---------------------- To get the terms from neo4j database -----------------*/
	@Override
	public List<TermToIntentRelation> getTerms() {
		
		List<TermToIntentRelation> termlist= new ArrayList<TermToIntentRelation>();
		List<HashMap<String, String>> listIndicator =neo4jRepository.fetchIndicatorOf();
		for(Map<String,String> map : listIndicator){
			TermToIntentRelation words = new TermToIntentRelation();
			logger.debug("Intent: " + map.get("IntentName")+" Term: "+map.get("TermName")+" Relation: "+map.get("Relation")+" "+map.get("Weight"));
			words.setIntentName(map.get("IntentName"));
			words.setTermName(map.get("TermName"));
			words.setRelName(map.get("Relation"));
			words.setWeight(Integer.parseInt(map.get("Weight")));
			termlist.add(words);
		}
		return termlist;
	}
	/*---------------------- To get words from the Api ------------------------*/
	@Override
	public List<ResponsiveBean> getWords(String terms) {
		RestTemplate restTemplate = new RestTemplate();
		List<HttpMessageConverter<?>> msgConverters = new ArrayList<HttpMessageConverter<?>>(1);
		msgConverters.add(new MappingAnyJsonHttpMessageConverter());
		restTemplate.setMessageConverters(msgConverters);
		String finalurl = apiurl + terms + "/json";
		/* Get the data from the Api and save as responsiveBean object */
		responsiveBean = restTemplate.getForObject(finalurl,ResponsiveBean.class);
		List<ResponsiveBean> wordlist= new ArrayList<ResponsiveBean>();
		wordlist.add(responsiveBean);
		if(responsiveBean.getNounBean()!=null)
		{
			if(responsiveBean.getNounBean().getSyn()!=null)
			{
				nounSynList = responsiveBean.getNounBean().getSyn();
				
				for(int i=0;i<nounSynList.length;i++){
					noun.setSyn(nounSynList);
					logger.debug("Synonyms for noun are: "+ nounSynList[i]);
				}
			}
			else
			{
				logger.debug("nounsynlist is empty");
			}

			if(responsiveBean.getNounBean().getAnt()!=null)
			{
				nounAntList = responsiveBean.getNounBean().getAnt();
				for(int i=0;i<nounAntList.length;i++){
					noun.setAnt(nounAntList);
					System.out.println("noun1 "+nounAntList[i]);
					logger.debug("Antonyms for noun are: "+ nounAntList[i]);
				}
			}
			else
			{
				logger.debug("nounantlist is empty");
			}
		}
		else
		{
			logger.debug("inside noun");
		}

		if(responsiveBean.getAdjectiveBean()!=null)
		{
			if(responsiveBean.getAdjectiveBean().getSyn()!=null)
			{
				adjSynList = responsiveBean.getAdjectiveBean().getSyn();
				for(int i=0;i<adjSynList.length;i++){
					adjective.setSyn(adjSynList);
					logger.debug("Synonyms for adjective are: "+ adjSynList[i]);
				}
			}
			else
			{
				logger.debug("adjsynlist is empty");
			}
			if(responsiveBean.getAdjectiveBean().getAnt()!=null)
			{
				adjAntList = responsiveBean.getAdjectiveBean().getAnt();
				for(int i=0;i<adjAntList.length;i++){
					adjective.setAnt(adjAntList);
					logger.debug("Antonyms for adjective are: "+ adjAntList[i]);
				}
			}
			else
			{
				logger.debug("adjantlist is empty");
			}
		}
		else
		{
			logger.debug("inside adjective");
		}

		return wordlist;
	}

	/* To insert term with intent into Neo4j with respective weight and relation */
	@Override
	public void insertData(Input input) {
		int i=0;
		int j=0;
		int flag=0;
		int k=0;
		int l=0;
		int flag1=0;
		Input inputRef = new Input();
		inputRef.setSynonyms(input.getSynonyms());
		inputRef.setAntonyms(input.getAntonyms());
		inputRef.setSynweight(input.getSynweight());
		inputRef.setAntweight(input.getAntweight());
		inputRef.setIntent(input.getIntent());
		inputRef.setRelation(input.getRelation());
		/* To check the synonyms are present or Not */
		if(inputRef.getSynonyms()!=null)
		{
			for(String termName:inputRef.getSynonyms())
			{
				for(String intentName:inputRef.getIntent())
				{
					if(inputRef.getRelation()[i].equals("indicatorOf"))
					{
						logger.info("inside syn indicatorOF");
						if(flag==0)
						{
							/* method to insert the synonym term for indicatorOf relationship first time into Neo4j */
							termRepository.insertIndicatorOf(termName, intentName, inputRef.getSynweight()[j]);
							i=i+1;
							flag++;
						}
						else
						{
							/* method to insert the synonym term for indicatorOf relationship into Neo4j */
							termRepository.insertIndicatorOfRef(termName, intentName, inputRef.getSynweight()[j]);
							i=i+1;
						}
					}
					else
					{
						logger.info("inside syn counterIndicatorOf");
						if(flag==0)
						{
							/* method to insert the synonym term for counterIndicatorOf relationship first time into Neo4j */
							termRepository.insertCounterIndicatorOf(termName, intentName, inputRef.getSynweight()[j]);
							i=i+1;
							flag++;
						}
						else
						{
							/* method to insert the synonym term for counterIndicatorOf relationship into Neo4j */
							termRepository.insertCounterIndicatorOfRef(termName, intentName, inputRef.getSynweight()[j]);
							i=i+1;
						}
					}
				}
				j=j+1;
			}
		}
		else
		{
			logger.error("There is No synonyms");
		}
		/* To check Antonyms are available or Not */
		if(inputRef.getAntonyms()!=null)
		{
			for(String termName:inputRef.getAntonyms())
			{
				for(String intentName:inputRef.getIntent())
				{
					if(inputRef.getRelation()[k].equals("indicatorOf"))
					{
						logger.info("inside Ant indicatorOF");
						if(flag1==0)
						{
							/* method to insert the synonym term for indicatorOf relationship first time into Neo4j */
							termRepository.insertIndicatorOf(termName, intentName, inputRef.getAntweight()[l]);
							k=k+1;
							flag1++;
						}
						else
						{
							/* method to insert the synonym term for indicatorOf relationship into Neo4j */
							termRepository.insertIndicatorOfRef(termName, intentName, inputRef.getAntweight()[l]);
							k=k+1;
						}
					}
					else
					{
						logger.info("inside Ant counterIndicatorOf");
						if(flag1==0)
						{
							/* method to insert the Antonyms term for counterIndicatorOf relationship first time into Neo4j */
							termRepository.insertCounterIndicatorOf(termName, intentName, inputRef.getAntweight()[l]);
							k=k+1;
							flag1++;
						}
						else
						{
							/* method to insert the Antonyms term for counterIndicatorOf relationship into Neo4j */
							termRepository.insertCounterIndicatorOfRef(termName, intentName, inputRef.getAntweight()[l]);
							k=k+1;
						}
					}
				}
				l=l+1;
			}
		}
		else
		{
			logger.error("There is No Antonyms");
		}

	}
}