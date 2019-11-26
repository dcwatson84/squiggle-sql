package com.truemesh.squiggle;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.truemesh.squiggle.output.Output;
import com.truemesh.squiggle.parameters.Parameters;

/**
 * 
 * 
 *
 */
public class Case extends AliasedSelectable implements Selectable,Matchable,HasParameters  {

	Selectable check;
	List<When> whens;
	Selectable elsee;
	
	public Case(Selectable check){
		this.check = check;
		this.whens = new ArrayList<When>();
	}
	
	public void addWhen(Selectable match,Selectable output){
		whens.add(new When(match,output));
	}
	
	public void setElse(Selectable output){
		this.elsee = output;
	}
	
	@Override
	public void addReferencedTablesTo(Set<Tabular> tables) {
		check.addReferencedTablesTo(tables);
	}

	@Override
	public void writeContent(Output out) {
		out.print("case ");
		out.print(check);
		for(When when : whens){
			out.print(" ");
			when.write(out);
		}
		if(elsee != null){
			out.print(" else ");
			out.print(elsee);
		}
		out.print(" end");
	}

	@Override
	public void addParameterValues(List<Object> parameters) {
		Parameters.addParameterValues(parameters, check);
		Parameters.addParameterValues(parameters, whens);
		Parameters.addParameterValues(parameters, elsee);
	}

}
