package com.stackroute.swisit.documentparser.repository;

/*----------------- Importing Libraries ----------------*/
import com.stackroute.swisit.documentparser.domain.Term;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface to retrieve terms from neo4j
 */
@Repository
public interface Neo4jParserRepository extends GraphRepository {
	/*--- Method used to get terms from neo4j database ---*/
    @Query("Match (t:Term) Return t")
    public List<Term> getTerms();
}
