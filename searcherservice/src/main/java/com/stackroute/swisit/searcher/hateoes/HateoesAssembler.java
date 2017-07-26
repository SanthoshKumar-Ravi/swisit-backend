package com.stackroute.swisit.searcher.hateoes;

/*----- Import Libraries -----*/
import java.util.List;

import com.stackroute.swisit.searcher.domain.SavingSearcherResult;
import com.stackroute.swisit.searcher.domain.SearcherJob;
import com.stackroute.swisit.searcher.domain.SearcherResult;
/*--- Hateoes assembler interface ---*/
public interface HateoesAssembler {
	public List<SavingSearcherResult> getAllLinks(List<SavingSearcherResult> all);
	public List getLinksPost();
	public List getLinksPostError();
}
