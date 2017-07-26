package IntentParserRestTest;
/*-------Importing Libraries--------*/

import com.stackroute.swisit.intentparser.assembler.HeteoasLinkAssembler;
import com.stackroute.swisit.intentparser.domain.ContentSchema;
import com.stackroute.swisit.intentparser.domain.CrawlerResult;
import com.stackroute.swisit.intentparser.domain.IntentParserResult;
import com.stackroute.swisit.intentparser.repository.IntentRepository;
import com.stackroute.swisit.intentparser.repository.RelationshipRepository;
import com.stackroute.swisit.intentparser.repository.TermRepository;
import com.stackroute.swisit.intentparser.service.IntentParseAlgo;
import com.stackroute.swisit.intentparser.subscriber.SubscriberImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;
import java.util.Date;

import static junit.framework.TestCase.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration

/*---------IntentParserServiceTest Class--------*/
public class IntentParserServiceTest {



    @MockBean
    private IntentParseAlgo intentParseAlgo;

    @MockBean
    private RelationshipRepository relationshipRepository;

    @MockBean
    private IntentRepository intentRepository;

    @MockBean
    private TermRepository termRepository;

    @MockBean
    private IntentParserResult intentParserResult;

    @MockBean
    private CrawlerResult crawlerResult;

    @MockBean
    private HeteoasLinkAssembler heteoasLinkAssembler;

    @MockBean
    private SubscriberImpl subscriberImpl;

    /*****Test case for IntentParseAlgoImpl service class*****/
    @Test
    public void calculateConfidenceScore() throws Exception {

        IntentParserResult intentParserResult = new IntentParserResult();
        intentParserResult.setUrl("https://www.mbusa.com/");
        intentParserResult.setIntent("example");
        intentParserResult.setConfidenceScore(15f);
        intentParserResult.setConcept("car benz");
        assertEquals("https://www.mbusa.com/", intentParserResult.getUrl());
        assertEquals("example", intentParserResult.getIntent());
        assertEquals(15f, intentParserResult.getConfidenceScore());
        assertEquals("car benz", intentParserResult.getConcept());

    }
    @Test
    public void toCalculateConfidenceScore()
    {
        ContentSchema contentSchema = new ContentSchema();
        contentSchema.setWord("how to code");
        contentSchema.setIntensity(2f);
        CrawlerResult crawlerResult = new CrawlerResult();
        crawlerResult.setLink("https://docs.angularjs.org/guide/component");
        ArrayList<ContentSchema> contentSchemaList= new ArrayList<ContentSchema>();
        contentSchemaList.add(contentSchema);
        crawlerResult.setTerms(contentSchemaList);
        crawlerResult.setSnippet("In AngularJS, a Component is a special kind of directive that uses a simpler configuration which is suitable for a component-based application structure.");
        crawlerResult.setTitle("AngularJS Documentation for component");
        crawlerResult.setLastindexedof(new Date());
        IntentParserResult intentParserResult = new IntentParserResult();
        intentParserResult.setUrl("https://www.mbusa.com/");
        intentParserResult.setIntent("example");
        intentParserResult.setConfidenceScore(15f);
        intentParserResult.setConcept("car benz");
        ArrayList<IntentParserResult> calculateConfidence = new ArrayList<IntentParserResult> ();
        assertEquals("https://www.mbusa.com/",intentParserResult.getUrl());
        assertEquals("example",intentParserResult.getIntent());
        assertEquals(15f,intentParserResult.getConfidenceScore());
        assertEquals("car benz",intentParserResult.getConcept());
    }

}
