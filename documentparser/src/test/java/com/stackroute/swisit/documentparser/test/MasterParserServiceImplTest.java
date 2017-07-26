package com.stackroute.swisit.documentparser.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.stackroute.swisit.documentparser.domain.ContentSchema;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by user on 10/7/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration()
public class MasterParserServiceImplTest {
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
    public void parseDocument() throws ParseException, JsonProcessingException {

        CrawlerResult crawlerResult = new CrawlerResult();
        crawlerResult.setConcept("Aggregation");
        crawlerResult.setLastindexedof(new SimpleDateFormat("dd/MM/yyyy").parse("10/07/2017"));
        crawlerResult.setLink("http://javarevisited.blogspot.com/2014/02/ifference-between-association-vs-composition-vs-aggregation.html");
        crawlerResult.setSnippet("Both Composition and Aggregation are the form of association between two \n" +
                "objects, but ... Composition vs Association vs Aggregation in Java.");
        crawlerResult.setTitle("Difference between Association, Composition and Aggregation in Java, UML and Object Oriented Programming");
        crawlerResult.setDocument("<html>Difference between Association, Composition and Aggregation in Java, UML and Object Oriented Programming</html>");

        DocumentParserResult documentParserResult = new DocumentParserResult();
        ArrayList<ContentSchema> contentSchemaList = new ArrayList<>();
        ContentSchema contentSchema = new ContentSchema();
        contentSchema.setWord("tutorials");
        contentSchema.setIntensity(100);
        contentSchemaList.add(contentSchema);
        documentParserResult.setConcept("Aggregation");
        documentParserResult.setLink("http://javarevisited.blogspot.com/2014/02/ifference-between-association-vs-composition-vs-aggregation.html");
        documentParserResult.setLastindexedof(new SimpleDateFormat("dd/MM/yyyy").parse("10/07/2017"));
        documentParserResult.setSnippet("Both Composition and Aggregation are the form of association between two \n" +
                "objects, but ... Composition vs Association vs Aggregation in Java.");
        documentParserResult.setTerms(contentSchemaList);

        Iterable<DocumentParserResult> parsedDocument = masterParserService.parseDocument(crawlerResult);
        Assert.assertEquals("Aggregation",documentParserResult.getConcept());
        Assert.assertEquals("http://javarevisited.blogspot.com/2014/02/ifference-between-association-vs-composition-vs-aggregation.html",documentParserResult.getLink());
        Assert.assertEquals(new SimpleDateFormat("dd/MM/yyyy").parse("10/07/2017"),documentParserResult.getLastindexedof());
        Assert.assertEquals("Both Composition and Aggregation are the form of association between two \n" +
                "objects, but ... Composition vs Association vs Aggregation in Java.", documentParserResult.getSnippet());
        Assert.assertEquals(contentSchemaList,documentParserResult.getTerms());

    }

}
