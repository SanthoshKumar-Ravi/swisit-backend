/*******This Class is used for Testing the Service only,
 * will finally be removed from the Final product******/

package com.stackroute.swisit.termbank.assembler;

/*------Importing Liberaries------*/
import java.util.List;
import com.stackroute.swisit.termbank.domain.Input;


/*------Interface Class for Assembling Links-----*/
public interface HateoasLinkAssembler{
	public List getWords(Input input);
	public List getTerms(List input);
}