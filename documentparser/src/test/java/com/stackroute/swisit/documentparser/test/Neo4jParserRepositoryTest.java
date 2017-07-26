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

import java.util.List;

/**
 * Created by user on 11/7/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration()
public class Neo4jParserRepositoryTest {

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
    public void getTerms() {
        Term term = new Term();
        term.setName("tutorials");
        term.setNodeid("24");https://www.youtube.com/watwathttpshttps://www.youtube.com/watch?v=AjDWc-vl5OYhttps://www.youtube.com/watch?v=AjDWc-vl5OYch?v=AjDWc-vl5OY
        neo4jParserRepository.save(term);
        List<Term> termList = neo4jParserRepository.getTerms();
        Assert.assertEquals("tutorials", term.getName());
        Assert.assertEquals("24", term.getNodeid());
    }
}
