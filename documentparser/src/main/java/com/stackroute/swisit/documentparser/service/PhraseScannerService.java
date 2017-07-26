package com.stackroute.swisit.documentparser.service;
/*----------------- Importing Libraries ----------------*/

import org.jsoup.nodes.Document;

import java.util.HashMap;
import java.util.List;

/**
 * Interface to split the document into phrases.
 */
public interface PhraseScannerService {
    public HashMap<String,List<String>> scanDocument(Document document);
    public List<String> ngrams(int n, String str);
    public String concat(String[] words, int start, int end);
}
