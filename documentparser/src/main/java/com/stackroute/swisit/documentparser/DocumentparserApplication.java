package com.stackroute.swisit.documentparser;
import java.text.ParseException;
/*--------- Importing Libraries --------*/


import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.ConfigurableApplicationContext;
import com.stackroute.swisit.documentparser.threadconsumer.KafkaConsumer;


@SpringBootApplication
@EnableDiscoveryClient
public class DocumentparserApplication {
	
	public static void main(String[] args) throws JsonProcessingException, ParseException {
		ConfigurableApplicationContext applicationContext=SpringApplication.run(DocumentparserApplication.class, args);
		KafkaConsumer kakfaConsumer = applicationContext.getBean(KafkaConsumer.class);
		kakfaConsumer.consumeMessage();
		
		//MasterParserServiceImpl masterParserServiceImpl = applicationContext.getBean(MasterParserServiceImpl.class);
		//Iterable<DocumentParserResult> documentParserResults = null;
//		try {
//			Iterable<DocumentParserResult> documentParserResults = masterParserServiceImpl.parseDocument();
//			for(DocumentParserResult documentParserResult : documentParserResults){
//				System.out.println(documentParserResult.getConcept());
//				System.out.println(documentParserResult.getLink());
//				System.out.println(documentParserResult.getQuery());
//				System.out.println(documentParserResult.getLastindexedof());
//				System.out.println(documentParserResult.getSnippet());
//				System.out.println(documentParserResult.getTitle());
//				for(ContentSchema cs:documentParserResult.getTerms()){
//					System.out.println("term : "+cs.getWord()+"         intensity : "+cs.getIntensity());
//				}
//				System.out.println(documentParserResult.getTerms().get(0).toString());
//			}
//		}catch (Exception e){e.printStackTrace();}
		//List<CrawlerResult> list =subscriberImpl.receiveMessage("tonewparser");
		//CrawlerResult crawlerResult[] = new CrawlerResult[list.size()];
		//list.toArray(crawlerResult);
		//System.out.println("hi this is "+crawlerResult);
		/*
		Document doc;
		for(CrawlerResult cr : crawlerResult){
		doc = Jsoup.parse(cr.getDocument());
		System.out.println(cr.getLink());
		System.out.println(cr.getQuery());
		System.out.println(cr.getSnippet());
		System.out.println(cr.getLastindexedof());
		}
		*/

	}
}
