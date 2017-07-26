package com.stackroute.swisit.searcher.domain;

/*------ Import Libraries ------*/
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/*-------- Domain class to store searched result in mongo -------*/
@Document
@JsonIgnoreProperties(ignoreUnknown=true)
public class SavingSearcherResult extends ResourceSupport {

    @Id
    @JsonProperty("query")
    private String query;

    private List searcherResults;

    /*------- Getters and Setters for properties ------*/
    public String getQuery() {
        return query;
    }
    public void setQuery(String query) {
        this.query = query;
    }
    public List getSearcherResults() {
        return searcherResults;
    }
    public void setSearcherResults(List searcherResults) {
        this.searcherResults = searcherResults;
    }

}
