package com.stackroute.swisit.documentparser.service;
/*----------------- Importing Libraries ----------------*/

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import com.stackroute.swisit.documentparser.domain.ContentSchema;

/**
 * Interface that declares method to calculate the intensity of the words of document
 */
public interface IntensityAlgoService {
	/*-- Method declaration to calculate the intensity of each term --*/
	public ArrayList<ContentSchema> calculateIntensity(HashMap<String,HashMap<String,Integer>> parsedDocumentMap) ;
}
