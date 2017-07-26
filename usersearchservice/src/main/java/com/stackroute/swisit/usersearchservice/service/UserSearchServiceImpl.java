package com.stackroute.swisit.usersearchservice.service;
/*-----------Importing Libraries------------*/
import com.stackroute.swisit.usersearchservice.domain.*;
import com.stackroute.swisit.usersearchservice.exception.NeoDataNotFetchedException;
import com.stackroute.swisit.usersearchservice.repository.UserSearchServiceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.SynchronousQueue;

/*-------Implementation of UserSearchService Interface class------*/
@Service
public class UserSearchServiceImpl implements UserSearchService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    /*-------Autowired Repositories-------*/
    @Autowired
    UserSearchServiceRepository userSearchServiceRepository;


    UserInput userInput = new UserInput();

    ArrayList<UserSearchResult> userSearchResults = new ArrayList<UserSearchResult>();


    /*------------fetchConcept method for getting List of ConceptResult-----------*/
    @Override
    public List<String> fetchConcept() {

        List<String> conceptResult = userSearchServiceRepository.findConcepts();


        return conceptResult;
    }


    /*------------fetchTerm method for getting List of TermResult-----------*/
    @Override
    public List<String> fetchTerm() {
        List<String> termResults = userSearchServiceRepository.findTerms();

        return termResults;
    }

    /*------------fetchNeoData method for getting List of UserSearchResult-----------*/
    @Override
    public List<UserSearchResult> fetchNeoData(UserInput userInputRef) {
        Set<UserSearchResult> set = new HashSet<UserSearchResult>();
        ArrayList<UserSearchResult> userSearchResultArrayList = new ArrayList<UserSearchResult>();
        userSearchResultArrayList.clear();


        userInput.setConcept(userInputRef.getConcept());
        userInput.setDomain(userInputRef.getDomain());
        userInput.setTerm(userInputRef.getTerm());

        logger.debug(userInput.getConcept());
        List<Map<String, Object>> intentResultIndicatorOfRelation = userSearchServiceRepository.getAllIndicatorRelation(userInput.getTerm());

        userSearchResults.clear();

        for (Map<String, Object> result1 : intentResultIndicatorOfRelation) {

            List<Map<String, Object>> intentResultRelatesRelation = userSearchServiceRepository.getAllRelatesRelation(userInput.getConcept(), "" + result1.get("intentName"));
            for (Map<String, Object> relatesRelation : intentResultRelatesRelation) {

                UserSearchResult userSearchResult = new UserSearchResult();
                userSearchResult.setUrl("" + relatesRelation.get("url"));
                userSearchResult.setDescription("" + relatesRelation.get("description"));
                userSearchResult.setConfidenceScore((float) Double.parseDouble("" + relatesRelation.get("confidenceScore")));
                userSearchResult.setTitle("" + relatesRelation.get("title"));
                userSearchResults.add(userSearchResult);

            }
        }
        Set<String> titles = new HashSet<String>();

        userSearchResultArrayList.clear();

        for (UserSearchResult userSearchResult : userSearchResults) {
            if (titles.add(userSearchResult.getUrl())) {
                userSearchResultArrayList.add(userSearchResult);
            }
        }


        return userSearchResultArrayList;
    }

}






