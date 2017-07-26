package UserSearchRestTest;
/*-----------Importing Libraries-----------*/
import com.stackroute.swisit.usersearchservice.assembler.HeteoasLinkAssembler;
import com.stackroute.swisit.usersearchservice.controller.UserSearchServiceController;
import com.stackroute.swisit.usersearchservice.domain.Concept;
import com.stackroute.swisit.usersearchservice.domain.Term;
import com.stackroute.swisit.usersearchservice.domain.UserInput;
import com.stackroute.swisit.usersearchservice.domain.UserSearchResult;
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

import static org.junit.Assert.assertEquals;

/*-----------Test Class for UserSearchService Class-----------*/
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = UserSearchServiceController.class)
@WebMvcTest(controllers= UserSearchServiceController.class)
public class UserSearchServiceTest {

    /*--------------MockBeans and Properties of UserSearchService Class---------------*/
    @Autowired
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

    /*------------Test Method to Test getter and setter methods of UserSearchResult Class-------------*/
    @Test
    public  void fetchNeoData() throws Exception
    {
        UserSearchResult userSearchResult = new UserSearchResult();
        userSearchResult.setUrl("http://localhost:8090/javaworld/jw-05-2002/jw-0503-java101.html");
        userSearchResult.setDescription("null");
        userSearchResult.setConfidenceScore(9f);
        assertEquals("http://localhost:8090/javaworld/jw-05-2002/jw-0503-java101.html",userSearchResult.getUrl());
        assertEquals("null",userSearchResult.getDescription());
        assertEquals(9f,userSearchResult.getConfidenceScore(),9);
    }

    /*------------Test Method to Test getter and setter methods of Concept Class -------------*/
    @Test
    public void fetchConcept() throws Exception
    {
        Concept concept = new Concept();
        concept.setName("Singleton");
        userSearchServiceRepository.save(concept);
        //Assert.assertNotNull(userSearchServiceRepository.findOne(concept.getName()));
        assertEquals("Singleton",concept.getName());
    }

    /*------------Test Method to Test getter and setter methods of Terms Class -------------*/
    @Test
    public void fetchTerm() throws Exception
    {
        Term term = new Term();
        term.setName("tutorials");
        userSearchServiceRepository.save(term);
        List<String> termList = userSearchServiceRepository.findTerms();
        //Assert.assertNotNull(userSearchServiceRepository.findOne(term.getName()));
        Assert.assertEquals("tutorials", term.getName());

    }
}
