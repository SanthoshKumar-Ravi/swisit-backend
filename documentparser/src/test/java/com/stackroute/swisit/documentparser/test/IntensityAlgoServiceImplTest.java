package com.stackroute.swisit.documentparser.test;

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

import java.util.ArrayList;

/**
 * Created by user on 10/7/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration()
public class IntensityAlgoServiceImplTest {

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
    public void calculateIntensity() {
        ArrayList<ContentSchema> contentSchemas = new ArrayList<>();
        ContentSchema contentSchema = new ContentSchema();
        contentSchema.setWord("basics");
        contentSchema.setIntensity(120);
        contentSchemas.add(contentSchema);
        Assert.assertEquals("basics", contentSchema.getWord());
        Assert.assertEquals(120, contentSchema.getIntensity(),0.001);
        Assert.assertEquals(true, contentSchemas.contains(contentSchema));
    }
}
