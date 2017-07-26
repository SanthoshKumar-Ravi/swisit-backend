package com.stackroute.swisit.documentparser.service;
/*----------------- Importing Libraries ----------------*/

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Interface to read file
 */
public interface ObjectMapperService {
    /*-- Method declaration to read files --*/
    public List<LinkedHashMap<String, String>> objectMapping(String filePath) ;
}
