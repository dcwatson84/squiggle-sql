package com.truemesh.squiggle.criteria;

import java.util.List;
import java.util.Set;

import com.truemesh.squiggle.Tabular;
import com.truemesh.squiggle.output.Output;

/**
 * Class NoCriteria is a Criteria that represents an absent operand in an SQL
 * predicate expression so that one may represent a unary operator (for example,
 * {@link NOT}) using a binary operator derived from a {@link BaseLogicGroup}).
 * 
 * 
 * 
 */
public class NoCriteria extends BaseCriteria {
	/**
	 * Writes an empty criteria (single space) to the given output stream.
	 * 
	 * @see com.truemesh.squiggle.Criteria#write(com.truemesh.squiggle.output.Output)
	 */
	public void write(Output out) {
		out.print(' ');
	}

	public void addReferencedTablesTo(Set<Tabular> tables) {
	}
	
	@Override
	public void addParameterValues(List<Object> parameters) {}
}
