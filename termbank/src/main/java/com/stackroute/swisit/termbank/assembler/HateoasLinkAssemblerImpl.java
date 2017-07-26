/*******This Class is used for Testing the Service only,
 * will finally be removed from the Final product******/
package com.stackroute.swisit.termbank.assembler;

/*-----Importing Libraries-----*/
import java.util.ArrayList;
import java.util.List;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;
import org.springframework.stereotype.Service;
import com.stackroute.swisit.termbank.controller.TermBankRestController;
import com.stackroute.swisit.termbank.domain.Input;


/*-------Implementation to HateoasLinkAssembler interface------*/
@Service
public class HateoasLinkAssemblerImpl implements HateoasLinkAssembler {

	/*------Overriding getTerms method of Interface Class------*/

	@Override
	public List getWords(Input input) {
		List list=new ArrayList();
		Link postQuery = linkTo(TermBankRestController.class).slash("/terms").withSelfRel();
		list.add(postQuery);		
		return list;
	}

	@Override
	public List getTerms(List input) {
		List list=new ArrayList();
		Link postQuery = linkTo(TermBankRestController.class).slash("/good").withSelfRel();
		list.add(postQuery);
		Link postQuery1 = linkTo(TermBankRestController.class).slash("/terms").withRel("terms");
		list.add(postQuery1);
		Link postQuery2 = linkTo(TermBankRestController.class).slash("").withRel("to post data");
		list.add(postQuery2);
		return list;
	}
}
