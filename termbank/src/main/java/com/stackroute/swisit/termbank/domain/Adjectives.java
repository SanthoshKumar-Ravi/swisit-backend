package com.stackroute.swisit.termbank.domain;

/*-------Importing Libraries--------*/
import java.util.Arrays;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/*---------Adjectives Domain Class--------*/
@Component
@JsonIgnoreProperties(ignoreUnknown = true)
public class Adjectives {
    /*--------Adjectives  Properties-------*/
	@JsonProperty("syn")
	String[] syn;
	
	@JsonProperty("ant")
	String[] ant;

    /*------------Setter and Getter methods for Properties-----------*/
	public String[] getSyn() {
		return syn;
	}

	public void setSyn(String[] syn) {
		this.syn = syn;
	}

	public String[] getAnt() {
		return ant;
	}

	public void setAnt(String[] ant) {
		this.ant = ant;
	}

    /*---------Overridden ToString Method of Adjectives Class--------*/
	@Override
	public String toString() {
		return "Adjective [syn=" + Arrays.toString(syn) + ", ant=" + Arrays.toString(ant) + "]";
	}

	
	
}
