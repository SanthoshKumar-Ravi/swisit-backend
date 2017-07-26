package com.stackroute.swisit.searcher.hateoes;
/*----- Import Libraries ----*/
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;

import com.stackroute.swisit.searcher.controller.SearcherController;
import com.stackroute.swisit.searcher.domain.SavingSearcherResult;
import com.stackroute.swisit.searcher.domain.SearcherJob;
import com.stackroute.swisit.searcher.domain.SearcherResult;
/*----- Class that implements hateoes links ----*/
@Service
public class HateoesAssemblerImpl implements HateoesAssembler{
	/*--- Used to return link when the post method is called ----*/
	@Override
	public List getLinksPost() {
		List sb=new ArrayList();
		Link postQuery = linkTo(SearcherController.class).slash("").withSelfRel();
		sb.add(postQuery);
		Link getAll = linkTo(SearcherController.class).slash("").withRel("GetSearcherResult");
		sb.add(getAll);
		return sb;
	}
	/*---- Used to return if domain and concept is already present ----*/
	@Override
	public List getLinksPostError() {
		List sb=new ArrayList();
		sb.add("Document Already Inserted");
		Link postQuery = linkTo(SearcherController.class).slash("").withSelfRel();
		sb.add(postQuery);
		Link getAll = linkTo(SearcherController.class).slash("").withRel("GetSearcherResult");
		sb.add(getAll);
		return sb;
	}
	/*---- Used to get all the SearcherResult ----*/
	@Override
	public List<SavingSearcherResult> getAllLinks(List<SavingSearcherResult> all) {
		for ( SavingSearcherResult sb : all) {
	        Link postQuery = linkTo(SearcherController.class).slash("").withRel("PostJob");
	        sb.add(postQuery);
	        Link getAll = linkTo(SearcherController.class).slash("").withSelfRel();
	        sb.add(getAll);
	    }
	 return all;
	}
}