package com.stackroute.swisit.intentparser.repository;
/*-----------Importing Libraries------------*/
import com.stackroute.swisit.intentparser.domain.Relationships;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/*------------Repository for Relationship Data of Neo4j DB-----------*/
@Repository
public interface RelationshipRepository extends GraphRepository<Relationships> {

    /*----------Query to get all Relation between Terms and Intents----------*/
    @Query("Match p=(t:Term)-[r]->(i:Intent) RETURN r")
    Iterable<Map<String,Object>> getBothRelationships();

    /*----------Query to get all Relations between Terms and Intents as IntentParserResult----------*/
    @Query("Match (t:Term)-[r]->(i:Intent) return t.name AS termName,i.name AS intentName,type(r) AS relName,r.weight AS weight")
    List<Map<String,String>> fetchAllRelationships();

    /*----------Query to get Terms Relation between Terms and an Intent----------*/
    @Query("Match (t:Term)-[r]->(i:Intent {name: {intentName}}) return t.name AS termName,i.name AS intentName,type(r) AS relName,r.weight AS weight")
    List<Map<String,String>> getAllTermsRelationOfIntent(@Param("intentName") String intentName);
}

