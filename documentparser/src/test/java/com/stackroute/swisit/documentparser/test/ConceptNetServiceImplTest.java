package com.stackroute.swisit.documentparser.test;

import com.stackroute.swisit.documentparser.domain.CrawlerResult;
import com.stackroute.swisit.documentparser.domain.DocumentParserResult;
import com.stackroute.swisit.documentparser.domain.Term;
import com.stackroute.swisit.documentparser.repository.MongoParserRepository;
import com.stackroute.swisit.documentparser.repository.Neo4jParserRepository;
import com.stackroute.swisit.documentparser.service.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by user on 10/7/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration()
public class ConceptNetServiceImplTest {

    @MockBean
    private MasterParserService masterParserService;

    @MockBean
    private KeywordScannerService keywordScannerService;

    @MockBean
    private PhraseScannerService phraseScannerService;

    @MockBean
    private ConceptNetService conceptNetService;

    @MockBean
    private IntensityAlgoService intensityAlgoService;

    @MockBean
    private Neo4jParserService neo4jParserService;

    @MockBean
    private ObjectMapperService objectMapperService;

    @MockBean
    private WordCheckerService wordCheckerService;

    @MockBean
    private Neo4jParserRepository neo4jParserRepository;

    @MockBean
    private MongoParserRepository mongoParserRepository;

    @MockBean
    private Term term;

    @MockBean
    private CrawlerResult crawlerResult;

    @MockBean
    private DocumentParserResult documentParserResult;

    @Test
    public void createDocumentModel(){
        HashMap<String,List<String>> tockenizedWords = new HashMap<>();
        List<String> listOfStrings = Arrays.asList("Object","oriented","programming","one","object","related");
        tockenizedWords.put("p", listOfStrings);
        HashMap<String,HashMap<String,Integer>> wordAndFreqMap = new HashMap<>();
        HashMap<String,Integer> tagAndFreqMap = new HashMap<>();
        tagAndFreqMap.put("body", 6);
        tagAndFreqMap.put("p", 5);
        wordAndFreqMap.put("basics", tagAndFreqMap);
        HashMap<String,HashMap<String,Integer>> documentModel = conceptNetService.createDocumentModel(tockenizedWords);
        Assert.assertEquals(true,wordAndFreqMap.containsKey("basics"));
        Assert.assertEquals(true,wordAndFreqMap.containsValue(tagAndFreqMap));
        Assert.assertEquals(true,tagAndFreqMap.containsKey("body"));
        Assert.assertEquals(true,tagAndFreqMap.containsValue(6));
    }
}
