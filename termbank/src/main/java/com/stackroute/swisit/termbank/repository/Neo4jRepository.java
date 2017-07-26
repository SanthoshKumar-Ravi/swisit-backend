package com.stackroute.swisit.termbank.repository;

/*-----------Importing Libraries------------*/
import java.util.HashMap;
import java.util.List;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Repository;

import com.stackroute.swisit.termbank.domain.Term;

/*------------Repository for Intent Graph Data from Neo4j DB-----------*/
@Repository
public interface Neo4jRepository extends GraphRepository<Term>{
	
	/* Query to get intent name and term name having relation indicatorOf*/
	@Query("match (i:Intent)<-[r]-(t:Term) return t.name AS TermName,i.name AS IntentName, type(r) AS Relation,r.weight As Weight")
	List<HashMap<String,String>> fetchIndicatorOf();
	
	/* Query to get intent name and term name having relation counterIndicatorOf */
	@Query("match (i:Intent)<-[r:counterIndicatorOf]-(t:Term) return t.name AS TermName,i.name AS IntentName, type(r) AS Relation,r.weight AS Weight")
	List<HashMap<String,String>> fetchCounterIndicatorOf();
}
