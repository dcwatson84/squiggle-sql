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
public class InsertStatement extends InsertUpdateStatement {

	public InsertStatement(TabularObject table) {
		super(table);
	}

	public void write(Output out) {
		out.print("INSERT INTO ");
		table.writeFullName(out);
		out.print(SqlConstants.SUB_CLAUSE_OPEN);
		for (Iterator<Column> it = allColumns.iterator(); it.hasNext();) {
			Column c = it.next();
			c.writeName(out);
			if (it.hasNext())
				out.print(SqlConstants.LIST_SEPARATOR);
		}
		out.print(SqlConstants.SUB_CLAUSE_CLOSE);
		out.print(" VALUES ");
		out.print(SqlConstants.SUB_CLAUSE_OPEN);
		for (Iterator<Column> it = allColumns.iterator(); it.hasNext();) {
			Column c = it.next();
			Object val = columnValues.get(c);
			out.print(val);
			if (it.hasNext())
				out.print(SqlConstants.LIST_SEPARATOR);
		}
		out.print(SqlConstants.SUB_CLAUSE_CLOSE);
	}

	@Override
	public void addParameterValues(List<Object> parameters) {
		for (Iterator<Column> it = allColumns.iterator(); it.hasNext();) {
			Column c = it.next();
			Object val = columnValues.get(c);
			Parameters.addParameterValues(parameters, val);
		}
	}

}
