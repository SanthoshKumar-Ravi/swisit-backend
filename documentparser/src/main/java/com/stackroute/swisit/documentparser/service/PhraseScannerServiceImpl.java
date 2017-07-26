package com.stackroute.swisit.documentparser.service;
/*----------------- Importing Libraries ----------------*/

import com.stackroute.swisit.documentparser.exception.DocumentNotScannedException;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Class that implements PhraseScannerService to split the document text into phrases using n-gram algorithm
 */
@Service
public class PhraseScannerServiceImpl implements PhraseScannerService{

    private ObjectMapperService objectMapperService;

    /*
    * Method definition to select the texts of each tag
    * Argument- Document
    * Return- hashmap of tag and list of tokenized string
    * */
    public HashMap<String,List<String>> scanDocument(Document document){

        HashMap<String,List<String>> resultMap = new HashMap<>();
        List<LinkedHashMap<String,String>> titleList = objectMapperService.objectMapping("./src/main/resources/common/intensity.json");
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
                List<String> strings = null;
                Elements elements = document.select(tag);
                for (Element element : elements) {
                    String tagText = element.text();
                    strings = ngrams(6, tagText);
                }
                resultMap.put(tag, strings);
            }
        }
        catch (DocumentNotScannedException e){
            e.printStackTrace();
        }
        return resultMap;
    }

    /*-- Method to produce n-grams of tokenized words of document --*/
    public List<String> ngrams(int n, String str) {
        List<String> ngrams = new ArrayList<String>();
        String[] words = str.split("[$&+,:;=?@#|'<>.^*()%!-]");
        for (int i = 0; i < words.length - n + 1; i++)
            ngrams.add(concat(words, i, i+n));
        return ngrams;
    }

    /*-- Method to concat the strings tokenized before producing n-grams --*/
    public String concat(String[] words, int start, int end) {
        StringBuilder sb = new StringBuilder();
        for (int i = start; i < end; i++)
            sb.append((i > start ? " " : "") + words[i]);
        return sb.toString();
    }
}
