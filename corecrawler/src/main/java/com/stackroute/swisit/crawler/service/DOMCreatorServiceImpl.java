package com.stackroute.swisit.crawler.service;

import java.io.IOException;
/*-------Importing Libraries------*/
import java.util.*;

import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.stackroute.swisit.crawler.exception.DOMNotCreatedException;

/*-- DOMCreatorServiceImpl implements DOMCreatorService interface to construct DOM --*/
@Service
public class DOMCreatorServiceImpl implements DOMCreatorService{

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    HashSet<String> links=new HashSet<>();

    /* Method implementation to construct DOMs
     * argument- link
     * returns- Document
     * */
    @Override
    public Document constructDOM(String link) throws DOMNotCreatedException, IOException {
        logger.info(link);
        String html = "<html><head><title>First parse</title></head>"
                  + "<body><p>Parsed HTML into a doc.</p></body></html>";
                Document doc = Jsoup.parse(html);

        
        //Document doc = "<html></html>";
        Response res=null;
        //res= Jsoup.connect(link).userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/535.21 (KHTML, like Gecko) Chrome/19.0.1042.0 Safari/535.21").timeout(50000).execute(); 
        res=Jsoup.connect(link).timeout(50000).ignoreHttpErrors(true).followRedirects(true).execute();
        logger.info(""+res.statusCode());
        if(res.statusCode() == 200)
        {
        
        doc=Jsoup.connect(link).get();
        return doc;
    }
        return doc;
    }
}