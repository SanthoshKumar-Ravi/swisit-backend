package com.stackroute.swisit.searcher.domain;
/*------ Import Libraries ------*/
import java.util.Map;
import org.apache.kafka.common.serialization.Serializer;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.hateoas.ResourceSupport;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

@JsonIgnoreProperties(ignoreUnknown=true)
@Document
/* To Send the result for the searcherJob */
public class SearcherResult extends ResourceSupport implements Serializer<SearcherResult>{
	@JsonProperty("link")
	private String url;

	@JsonProperty("title")
	private String title;

	@JsonProperty("snippet")
	private String description;

	@JsonProperty("concept")
	private String concept;

    /*------- Getters and Setters for properties ------*/
	public String getConcept() {
		return concept;
	}
	public void setConcept(String concept) {
		this.concept = concept;
	}
	private String encoding="UTF-8";
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	/*--------- Serializing searcher result to be produced to kafka --------*/
	@Override
	public byte[] serialize(String arg0, SearcherResult arg1) {
		byte[] retVal = null;
	    ObjectMapper objectMapper = new ObjectMapper();
	    try {
	      retVal = objectMapper.writeValueAsString(arg1).getBytes();
	    } 
	    catch (Exception e) {
	      e.printStackTrace();
	    }
	    return retVal;
	}
	@Override
	public void close() {
		
	}
	@Override
	public void configure(Map<String, ?> arg0, boolean arg1) {
		
	}
}