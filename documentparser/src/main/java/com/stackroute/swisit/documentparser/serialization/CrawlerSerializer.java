package com.stackroute.swisit.documentparser.serialization;

/*----------------- Importing Libraries ----------------*/
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackroute.swisit.documentparser.domain.DocumentParserResult;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

/**
 * Class to serialize document parser result to be published in Kafka
 */
public class CrawlerSerializer implements Serializer<DocumentParserResult> {

    /*-- Overriding configure method of serializer --*/
    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {

    }
    /*---- To serialize data from kafka topic ---*/
    @Override
    public byte[] serialize(String topic, DocumentParserResult data) {
       byte[] byteArray=null;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            byteArray = objectMapper.writeValueAsString(data).getBytes();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return byteArray;
    }

    /*-- Overriding close method of serializer --*/
    @Override
    public void close() {

    }
}
