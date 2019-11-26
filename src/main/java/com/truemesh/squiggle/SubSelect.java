package com.truemesh.squiggle;

import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.truemesh.squiggle.output.Output;
import com.truemesh.squiggle.output.ToStringer;
import com.truemesh.squiggle.parameters.Parameters;

/**
 * 
 * 
 *
 */
public class SubSelect extends AbstractTabular implements Selectable,HasParameters {
	
	protected static final Logger LOGGER = LoggerFactory.getLogger(SubSelect.class);

	Tabular query;
	
	public SubSelect(Tabular query,String alias){
		super(alias);
		this.query = query;
	}
	
	public void write(Output out) {
		out.print(SqlConstants.SUB_CLAUSE_OPEN);
		out.print(query);
		out.print(SqlConstants.SUB_CLAUSE_CLOSE);
		if (hasAlias()) {
            out.print(' ');
            out.print(getAlias());
        }
		
	}

	
	 public String toString() {
        return ToStringer.toString(this)+SqlConstants.STATEMENT_TERMINATOR;
    }

	@Override
	public void addReferencedTablesTo(Set<Tabular> tables) {
		
	}

	@Override
	public void addParameterValues(List<Object> parameters) {
		Parameters.addParameterValues(parameters, query);
	}
	
}
