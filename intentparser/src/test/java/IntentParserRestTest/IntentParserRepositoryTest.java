package IntentParserRestTest;
/*-------Importing Libraries--------*/

import com.stackroute.swisit.intentparser.assembler.HeteoasLinkAssembler;
import com.stackroute.swisit.intentparser.domain.*;
import com.stackroute.swisit.intentparser.repository.IntentRepository;
import com.stackroute.swisit.intentparser.repository.RelationshipRepository;
import com.stackroute.swisit.intentparser.repository.TermRepository;
import com.stackroute.swisit.intentparser.service.IntentParseAlgo;
import com.stackroute.swisit.intentparser.subscriber.SubscriberImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static junit.framework.TestCase.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration

/*---------IntentParserRepositoryTest Class--------*/
public class IntentParserRepositoryTest {


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



    /*****Test case for Term Repository*****/

    @Test
    public void equalGraphsWhichMatchTerms(){
        Term term = new Term();
        term.setName("tutorials");
        term.setNodeid("24");
        termRepository.save(term);
        List<Term> termList = termRepository.findTerms();
        Assert.assertEquals("tutorials", term.getName());
        Assert.assertEquals("24", term.getNodeid());
    }

    /*****Test case for Intent Repository*****/

    @Test
    public void equalGraphsWhichMatchIntent() {

        Intent intent= new Intent();
        intent.setName("basics");
        intent.setNodeid("2");
        List<Intent> intent1= intentRepository.findIntents();
        Assert.assertEquals("basics", intent.getName());
        Assert.assertEquals("2", intent.getNodeid());
    }
    /*****Test case for relationship Repository*****/
    @Test
    public void equalGraphsWithAllTermsRelationOfIntent(){
        Relationships relationships = new Relationships();
        relationships.setTermName("essential");
        relationships.setIntentName("example");
        relationships.setRelName("intentOf");
        relationships.setWeight(3f);
        List<Map<String,String>> allTermsRelationOfIntent = relationshipRepository.getAllTermsRelationOfIntent("example");
        assertEquals("essential",relationships.getTermName());
        assertEquals("example",relationships.getIntentName());
        assertEquals("intentOf",relationships.getRelName());
        assertEquals(3f,relationships.getWeight());

    }
    @Test
    public void equalGraphsWhichFetchAllRelationship()
    {
        Relationships relationships = new Relationships();
        relationships.setTermName("helloworld");
        relationships.setIntentName("getting started");
        relationships.setRelName("indicatorOf");
        relationships.setWeight(7f);
        Relationships relationships1 = new Relationships();
        relationships1.setIntentName("complete reference");
        relationships1.setTermName("sample");
        relationships1.setRelName("counterIndicatorOf");
        relationships1.setWeight(6f);
        Arrays.asList(relationships);
        List<Map<String,String>> fetchAllRelationships = relationshipRepository.fetchAllRelationships();
        assertEquals("getting started",relationships.getIntentName());
        assertEquals("helloworld", relationships.getTermName());
        assertEquals("indicatorOf",relationships.getRelName());
        assertEquals(7f,relationships.getWeight());
        assertEquals("complete reference",relationships1.getIntentName());
        assertEquals("sample",relationships1.getTermName());
        assertEquals("counterIndicatorOf",relationships1.getRelName());
        assertEquals(6f,relationships1.getWeight());

    }

}

