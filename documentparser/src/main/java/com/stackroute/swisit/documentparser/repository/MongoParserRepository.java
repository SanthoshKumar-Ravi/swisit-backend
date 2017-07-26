package com.stackroute.swisit.documentparser.repository;

/*----------------- Importing Libraries ----------------*/
import com.stackroute.swisit.documentparser.domain.DocumentModel;
import com.stackroute.swisit.documentparser.domain.DocumentParserResult;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface to save the document model in Mongo DB
 */
@Repository
public interface MongoParserRepository extends MongoRepository<DocumentModel,String> {
}
