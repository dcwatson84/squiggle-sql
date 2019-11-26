package com.truemesh.squiggle;

import java.util.Iterator;
import java.util.List;

import com.truemesh.squiggle.output.Output;
import com.truemesh.squiggle.parameters.Parameters;

/**
 * 
 * 
 *
 */
public class UpdateStatement extends InsertUpdateStatement {

	public UpdateStatement(Table table) {
		super(table);
	}

	public void write(Output out) {
		out.print("UPDATE ");
		table.writeFullName(out);
		out.print(" SET ");
		
		for (Iterator<Column> it = allColumns.iterator(); it.hasNext();) {
			Column c = it.next();
			c.writeName(out);
			out.print(" = ");
			Object val = columnValues.get(c);
			out.print(val);
			if (it.hasNext()) out.print(SqlConstants.LIST_SEPARATOR);
		}
		
		writeCriteria(out);
	}

	@Override
	public void addParameterValues(List<Object> parameters) {
		for (Iterator<Column> it = allColumns.iterator(); it.hasNext();) {
			Column c = it.next();
			Object val = columnValues.get(c);
			Parameters.addParameterValues(parameters, val);
		}
		Parameters.addParameterValues(parameters, criteria);
	}
}
