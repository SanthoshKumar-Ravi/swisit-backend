package com.stackroute.swisit.documentparser.domain;

import com.couchbase.client.deps.com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.couchbase.client.deps.com.fasterxml.jackson.annotation.JsonProperty;

/*--------WordChecker Class --------*/

@JsonIgnoreProperties(ignoreUnknown = true)
public class WordChecker {

	/*-------------Private variable of domain class------------*/

	@JsonProperty("word")
	private String word;

	/*-----------Default Constructor of Crawler Result Class------------*/
	public WordChecker(){
		super();
	}
	/*----------Parameterized Constructor of Crawler Result Class---------*/
	public WordChecker(String word) {
		super();
		this.word = word;
	}

	/*------------- Getters and setters for fields -----------*/

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		word = word;
	}
	

}
