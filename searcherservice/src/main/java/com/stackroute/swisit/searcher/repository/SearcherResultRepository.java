package com.stackroute.swisit.searcher.repository;
/*------ Import Libraries -----*/
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

import com.stackroute.swisit.searcher.domain.SearcherResponse;
import com.stackroute.swisit.searcher.domain.SavingSearcherResult;
import com.stackroute.swisit.searcher.domain.SearcherJob;
import com.stackroute.swisit.searcher.domain.SearcherResult;

/*---- Saving SearcherResult using MongoRepository -----*/
@Repository
public interface SearcherResultRepository extends MongoRepository<SavingSearcherResult, String>{
}
