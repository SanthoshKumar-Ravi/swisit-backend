package com.stackroute.swisit.intentparser.serialization;

/**
 * Created by user on 21/6/17.
 */import java.util.Map;

import org.apache.kafka.common.serialization.Deserializer;

import com.fasterxml.jackson.databind.ObjectMapper;
//import com.stackroute.swisit.crawler.domain.SwisitBean;

import com.stackroute.swisit.intentparser.domain.CrawlerResult;

public class CrawlerDeserializer implements Deserializer<CrawlerResult>{

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) { }

    /* To deserialize the values from kafka */
    @Override
    public CrawlerResult deserialize(String topic, byte[] data) {
        // TODO Auto-generated method stub
        ObjectMapper objectMapper=new ObjectMapper();
        CrawlerResult crawlerResult=null;
        try{
            crawlerResult=objectMapper.readValue(data,CrawlerResult.class);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return crawlerResult;
    }

    @Override
    public void close() { }

}
