package com.stackroute.swisit.documentparser.service;

/*----------------- Importing Libraries ----------------*/
import java.util.HashMap;
import java.util.List;

/**
 * Interface that declares method to give the result of document parser
 */
public interface ConceptNetService {
    /*--- Method that creates the final document model ---*/
    public HashMap<String,HashMap<String,Integer>> createDocumentModel(HashMap<String,List<String>> input);
}
