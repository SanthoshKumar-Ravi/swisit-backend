package com.stackroute.swisit.documentparser.service;

/*----------------- Importing Libraries ----------------*/
import com.stackroute.swisit.documentparser.domain.Term;
import com.stackroute.swisit.documentparser.exception.DocumentModelNotCreatedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Class that implements ConceptNet interface to create the document model
 * of the tokenized words of the document along with their tags.
 */
@Service
public class ConceptNetServiceImpl implements ConceptNetService {

    @Autowired
    Neo4jParserService neo4jParserService;

    /*
    * Method to create the document model of the list of words of the document received
    * Arguments- hashmap of tag and the list of strings
    * Returns- hashmap of hashmap(word, tag and intensity)
    * */
    public HashMap<String,HashMap<String,Integer>> createDocumentModel(HashMap<String,List<String>> input){

        HashMap<String,HashMap<String,Integer>> resultMap = new HashMap<>();
        /*--- Get the terms from Neo4j and added to termlist ---*/
        List<Term> termsList = neo4jParserService.getTerms();
        try {
            if (input == null) {
                throw new DocumentModelNotCreatedException(" Document model creation failed");
            }
            for (Term term : termsList) {
                HashMap<String, List<String>> map = new HashMap<String, List<String>>();
                Iterator<Map.Entry<String, List<String>>> entries = input.entrySet().iterator();
                HashMap<String, Integer> tagTextMap = new HashMap<>();
                while (entries.hasNext()) {
                    Map.Entry<String, List<String>> entry = entries.next();
                    String tag = entry.getKey();
                    List<String> textValue = entry.getValue();
                    int count = Collections.frequency(textValue, term.getName().toLowerCase());
                    tagTextMap.put(tag, count);
                }
                resultMap.put(term.getName(), tagTextMap);
            }
        }
         catch (DocumentModelNotCreatedException e){
                e.printStackTrace();
            }
        return resultMap;
    }
}
