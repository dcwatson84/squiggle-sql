package com.truemesh.squiggle.criteria;

import java.util.List;
import java.util.Set;

import com.truemesh.squiggle.Matchable;
import com.truemesh.squiggle.Tabular;
import com.truemesh.squiggle.output.Output;
import com.truemesh.squiggle.parameters.Parameters;

/**
 * 
 * 
 *
 */
public class IsNotNullCriteria extends BaseCriteria {
	private final Matchable matched;
	
	public IsNotNullCriteria(Matchable matched) {
		this.matched = matched;
	}

	@Override
	public void write(Output out) {
		matched.write(out);
		out.print(" IS NOT NULL");
	}

	public void addReferencedTablesTo(Set<Tabular> tables) {
		matched.addReferencedTablesTo(tables);
	}
	
	@Override
	public void addParameterValues(List<Object> parameters) {
		Parameters.addParameterValues(parameters, matched);
	}
}
