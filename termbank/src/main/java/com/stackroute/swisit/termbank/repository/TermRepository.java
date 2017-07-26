package com.stackroute.swisit.termbank.repository;

/*--------------------Importing Libraries------------------------------------*/
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.stackroute.swisit.termbank.domain.Term;
import org.springframework.data.neo4j.annotation.Query;
import java.util.List;

/*---------------------Repository for Terms Data of Neo4j DB-----------------*/
@Repository
public interface TermRepository extends GraphRepository<Term> {

    /*-----------------Query to get all Terms from database------------------*/
    @Query("MATCH (n:`Term`) RETURN n")
    List<Term> findTerms();
    
    /*-----------------Query to insert IndicatorOf Terms in database------------------*/
    @Query("match(i:Intent {name:{intentName}}) create(t:Term {name:{termName}})-[r:indicatorOf{weight: {weight}}]->(i) return *")
    void insertIndicatorOf(@Param("termName") String termName, @Param("intentName") String intentName, @Param("weight") String weight);
    
    /*-----------------Query to insert Counter IndicatorOf Terms in database------------------*/
    @Query("match(i:Intent {name:{intentName}}) create(t:Term {name:{termName}})-[r:counterIndicatorOf{weight: {weight}}]->(i) return *")
    void insertCounterIndicatorOf(@Param("termName") String termName, @Param("intentName") String intentName, @Param("weight") String weight);
    
    /*-----------------Query to insert CounterIndicatorOf Ref in database------------------*/
    @Query("match(t:Term {name:{termName}}),(i:Intent{name:{intentName}}) create(t)-[r:counterIndicatorOf{weight: {weight}}]->(i) return *")
    void insertCounterIndicatorOfRef(@Param("termName") String termName, @Param("intentName") String intentName, @Param("weight") String weight);
    
    /*-----------------Query to insert IndicatorOf Ref in database------------------*/
    @Query("match(t:Term {name:{termName}}),(i:Intent{name:{intentName}}) create(t)-[r:indicatorOf{weight: {weight}}]->(i) return *")
    void insertIndicatorOfRef(@Param("termName") String termName, @Param("intentName") String intentName, @Param("weight") String weight);
}
