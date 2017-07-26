package com.stackroute.swisit.documentparser.service;

/*------Importing Libraries-----*/
import ch.qos.logback.core.CoreConstants;
import com.stackroute.swisit.documentparser.domain.CrawlerResult;
import com.stackroute.swisit.documentparser.domain.DocumentModel;
import com.stackroute.swisit.documentparser.domain.DocumentParserResult;
import com.stackroute.swisit.documentparser.publisher.Publisher;
import com.stackroute.swisit.documentparser.repository.MongoParserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.stackroute.swisit.documentparser.domain.ContentSchema;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Class implementing MasterParserService to produce the final output of document parser to messaging service
 */
@Service
@PropertySource("classpath:application.yml")
public class MasterParserServiceImpl implements MasterParserService {

	@Autowired
	KeywordScannerService keywordScannerService;

	@Autowired
	Publisher publisher;

	@Autowired
	IntensityAlgoService intensityAlgoService;

	@Autowired
	ConceptNetService conceptNetService;

	@Autowired
	WordCheckerService wordCheckerService;

	@Autowired
	ObjectMapperService objectMapperService;
	
	@Autowired
	MongoParserRepository mongoRepository;
	
	@Autowired
	private Environment environment;

	/*
	* Method that receives the crawler result and redirects to every service of document parser
	* to produce the final output with a content schema that contains the word and its intensity
	* Argument- Crawler result
	* Return- iterable of document parser result
	* */
	public Iterable<DocumentParserResult> parseDocument(CrawlerResult crawlerResult) throws JsonProcessingException , ParseException{       
		String topicProducer = environment.getProperty("topic-toproducer");
		String brokerid = environment.getProperty("brokerid");
		ArrayList<DocumentParserResult> documentParserResults = new ArrayList<DocumentParserResult>();
		Document document=null;
		document = Jsoup.parse(crawlerResult.getDocument());
		HashMap<String, String> keywordScannerResult = keywordScannerService.scanDocument(document);
		/*Iterator<HashMap.Entry<String,String>> ksritr = keywordScannerResult.entrySet().iterator();
	        while(ksritr.hasNext()){
	        	HashMap.Entry ksrent = ksritr.next();
				System.out.println("ksrent.getKey() :   " + ksrent.getKey() + "      ksrent.getValue()  :  " + ksrent.getValue());
			}*/
		HashMap<String, List<String>> wordCheckerResult = wordCheckerService.getWordCheckerByWord(keywordScannerResult);
		/*System.out.println("Wordchecker result      "+wordCheckerResult.isEmpty());
	        Iterator<HashMap.Entry<String,List<String>>> ksritr = wordCheckerResult.entrySet().iterator();
			while(ksritr.hasNext()){
				HashMap.Entry ksrent = ksritr.next();
				System.out.println("wcent.getKey() :   " + ksrent.getKey() + "      wcent.getValue()  :  " + ksrent.getValue().toString());
			}*/
		HashMap<String,HashMap<String,Integer>> conceptNetResult = conceptNetService.createDocumentModel(wordCheckerResult);
		/*Iterator<HashMap.Entry<String,HashMap<String,Integer>>> ita = conceptNetResult.entrySet().iterator() ;
	        while(ita.hasNext()) {
				HashMap.Entry<String, HashMap<String, Integer>> parentPair = ita.next();
				System.out.println("parentPair.getKey() :   " + parentPair.getKey() + " parentPair.getValue()  :  " + parentPair.getValue());
				Iterator<HashMap.Entry<String, Integer>> child = (parentPair.getValue()).entrySet().iterator();
				while (child.hasNext()) {
					HashMap.Entry childPair = child.next();
					System.out.println("childPair.getKey() :   " + childPair.getKey() + " childPair.getValue()  :  " + childPair.getValue());

				}
			}*/
		DocumentModel documentModel = new DocumentModel(conceptNetResult);
		/*
		*Saving the document model into mongo so that it is not required to create
		*the document model each time request comes for the same domain and concept
		* */
		mongoRepository.save(documentModel);
		
		ArrayList<ContentSchema> contentSchema = intensityAlgoService.calculateIntensity(conceptNetResult);
		DocumentParserResult documentParserResult = new DocumentParserResult();
		documentParserResult.setConcept(crawlerResult.getConcept());
		documentParserResult.setLink(crawlerResult.getLink());
		documentParserResult.setTitle(crawlerResult.getTitle());
		documentParserResult.setTerms(contentSchema);
		documentParserResult.setSnippet(crawlerResult.getSnippet());
		documentParserResult.setLastindexedof(crawlerResult.getLastindexedof());
		/*--- Publishing document parser output to messaging service ---*/
		publisher.publishMessage(brokerid, topicProducer, documentParserResult);
		documentParserResults.add(documentParserResult);
		return documentParserResults;
	}
}
