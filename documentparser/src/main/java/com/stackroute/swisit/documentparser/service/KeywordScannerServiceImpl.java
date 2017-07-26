package com.stackroute.swisit.documentparser.service;
/*---------- Importing Libraries ---------*/

import com.stackroute.swisit.documentparser.exception.DocumentNotScannedException;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

/**
 * Class that implements KeywordScannerService to scan the document and select texts of each tag
 */
@Service
public class KeywordScannerServiceImpl implements KeywordScannerService {

    @Autowired
    ObjectMapperService objectMapperService;

    /*
    * Method definition to scan the document and select texts within the tag
    * Argument- document from crawler
    * Return- hashmap of tag and text of the tag
    * */
    public HashMap<String, String> scanDocument(Document document){

        HashMap<String, String> resultMap = new HashMap<>();
        List<LinkedHashMap<String,String>>  titleList = objectMapperService.objectMapping("./src/main/resources/common/intensity.json");

        List<String> tagList = new ArrayList<>();
        StringTokenizer stringTokenizer = null;

        try {
            if (document == null) {
                throw new DocumentNotScannedException("Document scanning failed");
            }
            for (int i = 0; i < titleList.size(); i++) {
                tagList.add(titleList.get(i).get("title"));
            }
            for (String tag : tagList) {
                String tagText = null;
                Elements elements = document.select(tag);
                for (Element element : elements) {
                    tagText = element.text();
                }
                resultMap.put(tag, tagText);
            }
        }
        catch (DocumentNotScannedException e){
            e.printStackTrace();
        }
        return resultMap;
    }
}
