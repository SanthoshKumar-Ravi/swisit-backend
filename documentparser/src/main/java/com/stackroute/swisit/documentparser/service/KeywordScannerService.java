package com.stackroute.swisit.documentparser.service;

/*-------- Importing Libraries --------*/
import org.jsoup.nodes.Document;

import java.util.HashMap;

/**
 * Interface to scan document
 */
public interface KeywordScannerService {
    /*---- Method declaration to scan document ---*/
    public HashMap<String, String> scanDocument(Document document);
}
