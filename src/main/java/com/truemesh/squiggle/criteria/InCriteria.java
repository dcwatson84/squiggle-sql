package com.truemesh.squiggle.criteria;

import java.util.List;
import java.util.Set;

import com.truemesh.squiggle.LiteralValueSet;
import com.truemesh.squiggle.Matchable;
import com.truemesh.squiggle.Table;
import com.truemesh.squiggle.Tabular;
import com.truemesh.squiggle.ValueSet;
import com.truemesh.squiggle.output.Output;
import com.truemesh.squiggle.parameters.Parameters;

/**
 * 
 * 
 * 
 */
public class InCriteria extends BaseCriteria {
    private final Matchable matched;
    private final ValueSet valueSet;

    public InCriteria(Matchable matchable, ValueSet valueSet) {
        this.matched = matchable;
        this.valueSet = valueSet;
    }

    public InCriteria(Matchable column, String... values) {
        this.matched = column;
        this.valueSet = new LiteralValueSet(values);
    }

    public InCriteria(Matchable column, long... values) {
        this.matched = column;
        this.valueSet = new LiteralValueSet(values);
    }

    public InCriteria(Matchable column, double... values) {
        this.matched = column;
        this.valueSet = new LiteralValueSet(values);
    }

    public InCriteria(Table table, String columnname, ValueSet valueSet) {
        this(table.getColumn(columnname), valueSet);
    }

    public InCriteria(Table table, String columnname, String[] values) {
        this(table.getColumn(columnname), values);
    }

    public InCriteria(Table table, String columnname, double[] values) {
        this(table.getColumn(columnname), values);
    }

    public InCriteria(Table table, String columnname, long[] values) {
        this(table.getColumn(columnname), values);
    }

    public Matchable getMatched() {
        return matched;
    }

    public void write(Output out) {
        matched.write(out);
        out.println(" IN (");
        out.indent();
        valueSet.write(out);
        out.println();
        out.unindent();
        out.print(")");
    }

    public void addReferencedTablesTo(Set<Tabular> tables) {
        matched.addReferencedTablesTo(tables);
    }

	@Override
	public void addParameterValues(List<Object> parameters) {
		Parameters.addParameterValues(parameters, matched);
		Parameters.addParameterValues(parameters, valueSet);
	}
}
