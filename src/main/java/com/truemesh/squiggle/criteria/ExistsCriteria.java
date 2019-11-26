package com.truemesh.squiggle.criteria;

import java.util.List;

import java.util.Set;

import com.truemesh.squiggle.Selectable;
import com.truemesh.squiggle.Tabular;
import com.truemesh.squiggle.output.Output;
import com.truemesh.squiggle.parameters.Parameters;

/**
 * 
 * 
 *
 */
public class ExistsCriteria extends BaseCriteria {

	Selectable selectable;
	
	public ExistsCriteria(Selectable selectable) {
		this.selectable = selectable;
	}
	
	@Override
	public void write(Output out) {
        out.println("exists (");
        out.print(selectable);
        out.println();
        out.print(")");
		
	}

	@Override
	public void addReferencedTablesTo(Set<Tabular> tables) {
		selectable.addReferencedTablesTo(tables);
	}

	@Override
	public void addParameterValues(List<Object> parameters) {
		Parameters.addParameterValues(parameters, selectable);
	}

}
