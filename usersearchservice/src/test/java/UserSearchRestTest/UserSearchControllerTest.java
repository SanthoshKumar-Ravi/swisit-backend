package UserSearchRestTest;
/*-----------Importing Libraries-----------*/
import com.stackroute.swisit.usersearchservice.assembler.HeteoasLinkAssembler;
import com.stackroute.swisit.usersearchservice.controller.UserSearchServiceController;
import com.stackroute.swisit.usersearchservice.domain.*;
import com.stackroute.swisit.usersearchservice.repository.UserSearchServiceRepository;
import com.stackroute.swisit.usersearchservice.service.UserSearchService;
import io.swagger.models.auth.In;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import static javafx.beans.binding.Bindings.when;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/*-----------Test Class for UserSearchController Class-----------*/
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = UserSearchServiceController.class)
@WebMvcTest(controllers= UserSearchServiceController.class)
public class UserSearchControllerTest {

    /*-------------MockBeans and Properties for UserSearchController test Class------------*/
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

    /*--------------MockMVC SetUp method ---------------*/
    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

    }

    /*-------------method to Test the usersearch path--------------*/
    @Test
    public void getUserSearch() throws Exception
    {

        mockMvc.perform(post("/v1/api/swisit/usersearch"))
                .andExpect(status().is4xxClientError());
    }


    /*-----------test method to test getTerms Path------------*/
    @Test
    public void getTerms() throws Exception
    {
        mockMvc.perform(get("/v1/api/swisit/usersearch/getTerms"))
                .andExpect(status().isOk());
    }

}