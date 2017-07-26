package com.stackroute.swisit.documentparser.domain;


/*--------Importing Liberaries-------*/
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.hibernate.validator.constraints.NotEmpty;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import javax.validation.constraints.NotNull;
/*--------Crawler Result Domain Class which is Input for Intent Parser Service--------*/
@JsonIgnoreProperties(ignoreUnknown = true)
public class DocumentParserResult {

        /*-------------Private variable of domain class------------*/

        @NotEmpty
        @JsonProperty("concept")
        private String concept;
        @NotEmpty
        @JsonProperty("link")
        private String link;
        @NotEmpty
        @JsonProperty("terms")
        private ArrayList<ContentSchema> terms;
        @NotEmpty
        @JsonProperty("title")
        private String title;
        @NotEmpty
        @JsonProperty("snippet")
        private String snippet;
        @NotNull
        @JsonProperty("lastindexedof")
        private Date lastindexedof;

        /*-----------Default Constructor of Crawler Result Class------------*/
        public DocumentParserResult() { }

        /*----------Parameterized Constructor of Crawler Result Class---------*/
        public DocumentParserResult(String concept, String link, ArrayList<ContentSchema> terms, String title, String snippet,Date lastindexof) {
            this.concept = concept;
            this.link = link;
            this.terms = terms;
            this.title = title;
            this.snippet = snippet;
            this.lastindexedof=lastindexof;
        }

        /*---------Overridden ToString Method of Crawler Result Class--------*/
        @Override
        public String toString() {
            return "IntentParserInput{" +
                    "concept='" + concept + '\'' +
                    ", link='" + link + '\'' +
                    ", contentSchema=" + terms.toString() +
                    ", title='" + title + '\'' +
                    ", snippet='" + snippet + '\'' +
                    '}';
        }

        /*-------------Getter and Setter Methods Of Properties------------*/
        public Date getLastindexedof() {
            return lastindexedof;
        }

        public void setLastindexedof(Date lastindexedof) {
            this.lastindexedof = lastindexedof;
        }
        
        public String getConcept() {
            return concept;
        }

        public void setConcept(String concept) {
            this.concept = concept;
        }
        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }


        public ArrayList<ContentSchema> getTerms() {
            return terms;
        }

        public void setTerms(ArrayList<ContentSchema> terms) {
            this.terms = terms;
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
    }

