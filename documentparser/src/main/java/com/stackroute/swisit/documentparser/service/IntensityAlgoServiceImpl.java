package com.stackroute.swisit.documentparser.service;

/*----------------- Importing Libraries ----------------*/
import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;

import com.stackroute.swisit.documentparser.exception.TitleIntensityCalculationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stackroute.swisit.documentparser.domain.ContentSchema;


/**
 * Class to implement IntensityAlgoService that defines a method to calculate the intensity of each
 * term of neo4j by using the frequency of the term occurrence and count for each tag
 */
@Service
public class IntensityAlgoServiceImpl implements IntensityAlgoService {

    float count;

    @Autowired
    ObjectMapperService objectMapperService;

    /*
    * Method to calculate the intensity of each term of neo4j
    * Arguments- hashmap of hashmap
    * Returns- ArrayList of contentSchema consisting of term and intensity
    * */
    public ArrayList<ContentSchema> calculateIntensity(HashMap<String, HashMap<String, Integer>> parsedDocumentMap) {
            ArrayList<ContentSchema> contentSchemas = new ArrayList<>();
            List<LinkedHashMap<String, String>> list = objectMapperService.objectMapping("./src/main/resources/common/termintensity.json");
            HashMap<String, String> map = new HashMap<>();
            String wordKey = "";
        try {
            if (parsedDocumentMap == null) {
                throw new TitleIntensityCalculationException("Intensity not calculated");
            }
            for (LinkedHashMap<String, String> linked : list) {
                Iterator<Map.Entry<String, String>> entries = linked.entrySet().iterator();
                Map.Entry<String, String> entry = entries.next();
                map.put(entry.getKey(), entry.getValue());
            }
            for (Entry<String, HashMap<String, Integer>> parsedDocumentMapRef : parsedDocumentMap.entrySet()) {
                wordKey = parsedDocumentMapRef.getKey();
                for (Entry<String, Integer> wordKeyValueRef : parsedDocumentMapRef.getValue().entrySet()) {
                    String tagKey = wordKeyValueRef.getKey();
                    Integer tagFrequency = wordKeyValueRef.getValue();
                    float intensity = Float.parseFloat(map.get(tagKey));
                    count += tagFrequency * intensity;

                }
                ContentSchema contentSchema = new ContentSchema(wordKey, count);
                contentSchemas.add(contentSchema);
                count = 0;
            }
        }
        catch (TitleIntensityCalculationException e){
            e.printStackTrace();

        }
        return contentSchemas;
    }
}

