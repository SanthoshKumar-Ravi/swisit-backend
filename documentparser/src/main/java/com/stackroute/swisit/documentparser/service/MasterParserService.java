package com.stackroute.swisit.documentparser.service;
/*----------------- Importing Libraries ----------------*/

import java.text.ParseException;
import java.util.ArrayList;

import com.fasterxml.jackson.core.JsonProcessingException;
/*-------- Importing Libraries -------*/
import com.stackroute.swisit.documentparser.domain.CrawlerResult;
import com.stackroute.swisit.documentparser.domain.DocumentParserResult;

/**
 * Interface that routes to all service classes of document parser
 */
public interface MasterParserService {
    /*-- Method declaration to consume input from messaging service and produce the final output --*/
    public Iterable<DocumentParserResult> parseDocument(CrawlerResult crawlerResult) throws JsonProcessingException, ParseException;
}
