package com.stackroute.swisit.documentparser.service;
/*----------------- Importing Libraries ----------------*/
import com.uttesh.exude.ExudeData;

import com.uttesh.exude.exception.InvalidDataException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.*;
import java.util.Map;


/**
 * Class implementing WordCheckerService to tokenize the texts of tags of the document
 */
@Service
public class WordCheckerServiceImpl implements WordCheckerService{

	/*
	* Method to tokenize the texts of tags using exude methods
	* Argument- hashmap of tag and its texts
	* Return- hashmap of tag and tokenized words
	* */
	public HashMap<String,List<String>> getWordCheckerByWord(HashMap<String,String> input){
		HashMap<String,List<String>> tockenizedWords = new HashMap<>();
		HashMap<String,String> map = new HashMap<>();
		Iterator<Map.Entry<String, String>> entries = input.entrySet().iterator();
		while(entries.hasNext()) {
			Map.Entry<String, String> entry =  entries.next();
			String key = entry.getKey();
			String inputData = entry.getValue();
			String filteredWords = "";
			String swearWords = "";
			try {

					if (inputData == null ){
						throw new InvalidDataException("Empty input data");
					}
                /*-- Removing the stop words --*/
				filteredWords = ExudeData.getInstance().filterStoppings(inputData);
				/*-- Removed stop words --*/
				swearWords = ExudeData.getInstance().getSwearWords(inputData);
			} catch (InvalidDataException e) {
                e.printStackTrace();
			}
			String filteredSpecialChar = filteredWords.replaceAll("[$_&+,:;=?@#|'<>.-^*()%!]", "");
			List<String> result = new ArrayList<>();
			for (String string : filteredSpecialChar.split(" ")) {
				if(string.isEmpty())
					continue;
				/*-- Result of tokenized words --*/
					result.add(string.trim());
			}
			tockenizedWords.put(key,result);
		}
		return tockenizedWords;
	}
}
