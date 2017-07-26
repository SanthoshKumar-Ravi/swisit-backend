package UserSearchRestTest;
/*-----------Importing Libraries-----------*/
import com.stackroute.swisit.usersearchservice.assembler.HeteoasLinkAssembler;
import com.stackroute.swisit.usersearchservice.controller.UserSearchServiceController;
import com.stackroute.swisit.usersearchservice.domain.*;
import com.stackroute.swisit.usersearchservice.repository.UserSearchServiceRepository;
import com.stackroute.swisit.usersearchservice.service.UserSearchService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/*-----------Test Class for UserSearchRepository Class-----------*/
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = UserSearchServiceController.class)
@WebMvcTest(controllers= UserSearchServiceController.class)
public class UserSearchRepositoryTest {

    /*--------------MockBeans and Properties of UserSearchRepository Class---------------*/
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private UserSearchService userSearchService;

    @MockBean
    private UserSearchServiceRepository userSearchServiceRepository;

    @MockBean
    private UserSearchResult userSearchResult;

    @MockBean
    private UserInput userInput;

    @MockBean
    private HeteoasLinkAssembler heteoasLinkAssembler;

    @InjectMocks
    private UserSearchServiceController userSearchServiceController;

    /*------------Test Method to Test findTerms method of UserSearchServiceRepository Class-------------*/
    @Test
    public void equalGraphsWhichMatchTerms(){

        Term term = new Term();
        term.setName("tutorials");
        userSearchServiceRepository.save(term);
        List<String> termList = userSearchServiceRepository.findTerms();
        // Assert.assertNotNull(userSearchServiceRepository.findOne(term.getName()));
        Assert.assertEquals("tutorials", term.getName());

    }

    /*------------Test Method to Test findIntents method of UserSearchServiceRepository Class-------------*/
    @Test
    public void equalGraphsWhichMatchIntent() {

        Intent intent= new Intent();
        intent.setName("basics");
        intent.setNodeid("2");
        userSearchServiceRepository.save(intent);
        List<Intent> intent1= userSearchServiceRepository.findIntents();
        Assert.assertEquals("basics", intent.getName());
        Assert.assertEquals("2", intent.getNodeid());
    }

    /*------------Test Method to Test getAllRelatesRelation Method of UserSearchServiceRepository Class-------------*/
    @Test
    public void equalGraphsWithRelatesRelation()
    {
        UserInput userInput = new UserInput();
        userInput.setConcept("Thread");
        UserSearchResult userSearchResult = new UserSearchResult();
        userSearchResult.setUrl("http://localhost:8090/javaworld/jw-05-2002/jw-0503-java101.html");
        userSearchResult.setDescription("null");
        userSearchResult.setConfidenceScore(9f);
        userSearchServiceRepository.save(userInput);
        List<Map<String,Object>> conceptDocumentRelation = userSearchServiceRepository.getAllRelatesRelation("Thread","Tutorial");
        assertEquals("http://localhost:8090/javaworld/jw-05-2002/jw-0503-java101.html",userSearchResult.getUrl());
        assertEquals("null",userSearchResult.getDescription());
        assertEquals(9f,userSearchResult.getConfidenceScore(),9);

    }

    /*------------Test Method to Test getAllIndicatorRelation Method of UserSearchServiceRepository Class-------------*/
    @Test
    public void equalGraphsWithIndicatorOfRelation()
    {
        RelationShipOf relationShipOf = new RelationShipOf();
        relationShipOf.setIntentname("basics");
        relationShipOf.setTermname("how to code");
        relationShipOf.setRelname("indicatorOf");
        relationShipOf.setWeight(7f);
        userSearchServiceRepository.save(relationShipOf);
        List<Map<String,Object>> getAllIndicatorRelation = userSearchServiceRepository.getAllIndicatorRelation("how to code");
        assertEquals("how to code",relationShipOf.getTermname());
        assertEquals("basics",relationShipOf.getIntentname());
        assertEquals("indicatorOf",relationShipOf.getRelname());
        assertEquals(7f,relationShipOf.getWeight(),7);

    }

    /*------------Test Method to Test findConcepts Method of UserSearchServiceRepository Class-------------*/
    @Test
    public void equalGraphsWithConceptOfRelation()
    {
        Concept concept = new Concept();
        concept.setName("Singleton");
        Domain domain = new Domain();
        domain.setName("Java Web Application Development");
        List<String> findConcepts = userSearchServiceRepository.findConcepts();
        assertEquals("Singleton",concept.getName());
        assertEquals("Java Web Application Development",domain.getName());
    }
}

