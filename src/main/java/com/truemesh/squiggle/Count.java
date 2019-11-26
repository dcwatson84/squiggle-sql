package com.truemesh.squiggle;

import java.util.List;


import com.truemesh.squiggle.output.Output;
import com.truemesh.squiggle.parameters.Parameters;

/**
 * 
 * 
 *
 */
public class Count extends Projection implements HasParameters  {

	protected Selectable column;
	
	public Count(Column column) {
		this(column,null);
	}
	
	public Count(Column column,String alias) {
		super(column.getTable());
		this.column = column;
		this.alias = alias;
	}


	@Override
	public void writeContent(Output out) {
		out.print("COUNT");
		out.print(SqlConstants.SUB_CLAUSE_OPEN);
		column.write(out);
		out.print(SqlConstants.SUB_CLAUSE_CLOSE);
	}

	@Override
	public void addParameterValues(List<Object> parameters) {
		Parameters.addParameterValues(parameters, column);
	}

}
