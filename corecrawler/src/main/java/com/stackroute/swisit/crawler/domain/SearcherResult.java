package com.stackroute.swisit.crawler.domain;

/*---------------Importing Libraries--------------*/
import java.util.Map;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.apache.kafka.common.serialization.Deserializer;
import org.slf4j.LoggerFactory;
import org.slf4j.*;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.stereotype.Component;

import com.couchbase.client.deps.com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;

import ch.qos.logback.classic.Logger;

/*------- SearcherResult domain class which is the input for crawler service-----*/
@Component
@JsonIgnoreProperties(ignoreUnknown=true)
public class SearcherResult  implements Deserializer<SearcherResult> {
	private final Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());
	/*-------------Private variables of domain class------------*/
    
    @JsonProperty("link")
    @Pattern(regexp="(@)?(href=')?(HREF=')?(HREF=\")?(href=\")?(http://)?[a-zA-Z_0-9\\-]+(\\.\\w[a-zA-Z_0-9\\-]+)+(/[#&\\n\\-=?\\+\\%/\\.\\w]+)?")
	private String link;

	@JsonProperty("title")
	@NotNull
	private String title;
    
	@NotNull
    @JsonProperty("snippet")
	private String snippet;
	
	@JsonProperty("concept")
	private String concept;

	public String getConcept() {
		return concept;
	}

	public void setConcept(String concept) {
		this.concept = concept;
	}

	/*-----------Default Constructor of Crawler Result Class------------*/
	public SearcherResult() {
		super();
	}

	/*----------Parameterized Constructor of Crawler Result Class---------*/
	public SearcherResult(String link, String title, String snippet) {
		super();
		this.link = link;
		this.title = title;
		this.snippet = snippet;
	}

	/*------------- Getters and setters for fields -----------*/
	

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSnippet() {
		return snippet;
	}

	public void setSnippet(String snippet) {
		this.snippet = snippet;
	}

	/*----------Overriding methods of Deserializer----------*/

	@Override
	public void configure(Map<String, ?> arg0, boolean arg1) {
		
	}

	/*-------Deserializing the data received from searcher service------*/
	@Override
	public SearcherResult deserialize(String arg0, byte[] arg1) {
		//return (CrawlerBean) SerializationUtils.deserialize(arg1);
		ObjectMapper objectMapper=new ObjectMapper();		
		SearcherResult searcherResult=null;
		try{
			searcherResult=objectMapper.readValue(arg1,SearcherResult.class);
			}
		catch(Exception e){
			logger.error("Cannot Deserialize"+e);
		}
		return searcherResult;
	}

	@Override
	public void close() {
		
	}
	
	
}
