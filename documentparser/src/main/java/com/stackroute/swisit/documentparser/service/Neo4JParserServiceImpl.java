package com.stackroute.swisit.documentparser.service;
/*----------------- Importing Libraries ----------------*/

import com.stackroute.swisit.documentparser.domain.Term;
import com.stackroute.swisit.documentparser.repository.Neo4jParserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Class implementing Neo4jParserService to get the terms from neo4j
 */
@Service
public class Neo4JParserServiceImpl implements Neo4jParserService {

    @Autowired
    Neo4jParserRepository neo4jParserRepository;

    /*-- Method definition to fetch terms from neo4j graph database --*/
    public List<Term> getTerms(){
        List<Term> termsList = neo4jParserRepository.getTerms();
        return termsList;
    }
}
