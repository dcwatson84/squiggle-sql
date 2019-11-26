package com.truemesh.squiggle;

import java.util.List;

import com.truemesh.squiggle.output.Output;
import com.truemesh.squiggle.output.Outputable;
import com.truemesh.squiggle.parameters.Parameters;

/**
 * 
 * 
 *
 */
public class JoinedTable implements Outputable,HasParameters {
	
	public static enum JoinType {
		Inner,Outer,Left;
		
	}
	
	protected Tabular table;
	protected JoinType type;
	protected Criteria criteria;
	public JoinedTable(Tabular table, JoinType type, Criteria criteria) {
		super();
		this.table = table;
		this.type = type;
		this.criteria = criteria;
	}
	public Tabular getTable() {
		return table;
	}
	public JoinType getType() {
		return type;
	}
	public Criteria getCriteria() {
		return criteria;
	}
	@Override
	public void write(Output out) {
		out.print(type.name().toUpperCase());
		out.print(" JOIN ");
		table.write(out);
		//out.print(table);
		out.print(" on ");
		criteria.write(out);
		//out.print(criteria);
	}
	@Override
	public void addParameterValues(List<Object> parameters) {
		Parameters.addParameterValues(parameters, table);
		Parameters.addParameterValues(parameters, criteria);
		
	}
	
	

}
