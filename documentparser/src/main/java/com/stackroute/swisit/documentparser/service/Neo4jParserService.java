package com.stackroute.swisit.documentparser.service;
/*----------------- Importing Libraries ----------------*/

import com.stackroute.swisit.documentparser.domain.Term;

import java.util.List;

/**
 * Interface to fetch neo4j terms
 */
public interface Neo4jParserService {
    /*-- Method to fetch terms from neo4j --*/
    public List<Term> getTerms();
}
