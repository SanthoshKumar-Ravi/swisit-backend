package com.stackroute.swisit.termbank.domain;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@Component
@JsonIgnoreProperties(ignoreUnknown = true)
public class TermToIntentRelation {
@JsonProperty("TermName")
String termName;
@JsonProperty("intentName")
String IntentName;
@JsonProperty("Relation")
String relName;
@JsonProperty("Weight")
int weight;

public int getWeight() {
	return weight;
}
public void setWeight(int weight) {
	this.weight = weight;
}
public String getTermName() {
	return termName;
}
public void setTermName(String termName) {
	this.termName = termName;
}
public String getIntentName() {
	return IntentName;
}
public void setIntentName(String intentName) {
	IntentName = intentName;
}
public String getRelName() {
	return relName;
}
public void setRelName(String relName) {
	this.relName = relName;
}
}
