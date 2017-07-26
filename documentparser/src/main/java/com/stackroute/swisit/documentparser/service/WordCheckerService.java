package com.stackroute.swisit.documentparser.service;
/*----------------- Importing Libraries ----------------*/

import java.util.*;

/**
 * Interface to tokenize the texts of tags in the document
 */
public interface WordCheckerService {
	/*-- Mthod declaration to tokenize the texts of tags --*/
	public HashMap<String,List<String>> getWordCheckerByWord(HashMap<String,String> input);
}
