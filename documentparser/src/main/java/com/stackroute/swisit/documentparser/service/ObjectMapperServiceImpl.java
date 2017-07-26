package com.stackroute.swisit.documentparser.service;
/*----------------- Importing Libraries ----------------*/

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackroute.swisit.documentparser.exception.FilePathNotFoundException;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Class implementing ObjectMapperService to read files as arraylist
 */
@Service
public class ObjectMapperServiceImpl implements ObjectMapperService {

    /*
    * Method definition to read file using object mapper
    * as array list
    * Argument- file path
    * Return- List of linked hashmap
    * */
    @Override
    public List<LinkedHashMap<String, String>> objectMapping(String filePath) {

        ObjectMapper objectMapper=new ObjectMapper();
        File file= new File(filePath);
        List<LinkedHashMap<String,String>> list = null;
        try {
            if (filePath == null) {
                throw new FilePathNotFoundException("Path not found");
            }

            list = objectMapper.readValue(file, ArrayList.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        for(LinkedHashMap<String,String> linked : list){
        }

        return list;
    }
}
