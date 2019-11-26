package com.truemesh.squiggle;

import java.util.List;
import java.util.Set;

import com.truemesh.squiggle.output.Output;
import com.truemesh.squiggle.parameters.Parameters;

/**
 * 
 * 
 *
 */
public class MathCall extends AliasedSelectable implements HasParameters {
	 Selectable left;
	 Selectable right;
	 String operator;
	 public MathCall(String operator, Selectable left, Selectable right) {
	    	super();
	    	this.operator = operator;
	    	this.left = left;
	    	this.right = right;
	    }

	@Override
	public void addReferencedTablesTo(Set<Tabular> tables) {
		left.addReferencedTablesTo(tables);
		right.addReferencedTablesTo(tables);
	}

	@Override
	public void writeContent(Output out) {
		out.print(SqlConstants.SUB_CLAUSE_OPEN);
		out.print(left);
		out.print(" ");
		out.print(operator);
		out.print(" ");
		out.print(right);
		out.print(SqlConstants.SUB_CLAUSE_CLOSE);
	}

	@Override
	public void addParameterValues(List<Object> parameters) {
		Parameters.addParameterValues(parameters, left);
		Parameters.addParameterValues(parameters, right);
	}
}
