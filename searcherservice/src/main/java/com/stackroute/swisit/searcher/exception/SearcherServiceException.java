package com.stackroute.swisit.searcher.exception;

/*------ User defined Exception class for searcher service -----*/
public class SearcherServiceException extends RuntimeException{
	public SearcherServiceException(String message){
		super(message);	
	}
}
