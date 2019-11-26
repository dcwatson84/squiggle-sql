package com.truemesh.squiggle;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.truemesh.squiggle.literal.BigDecimalLiteral;
import com.truemesh.squiggle.literal.DateTimeLiteral;
import com.truemesh.squiggle.literal.FloatLiteral;
import com.truemesh.squiggle.literal.IntegerLiteral;
import com.truemesh.squiggle.literal.StringLiteral;
import com.truemesh.squiggle.output.Output;
import com.truemesh.squiggle.parameters.Parameters;

/**
 * 
 * 
 *
 */
@SuppressWarnings("rawtypes")
public class LiteralValueSet implements ValueSet {
	
	private final Collection<Literal> literals;

    public LiteralValueSet(Collection<Literal> literals) {
        this.literals = literals;
    }

    public LiteralValueSet(String... values) {
        this.literals = new ArrayList<Literal>(values.length);
        for (String value : values) literals.add(new StringLiteral(value));
    }

    public LiteralValueSet(long... values) {
        this.literals = new ArrayList<Literal>(values.length);
        for (long value : values) literals.add(new IntegerLiteral(value));
    }

    public LiteralValueSet(double... values) {
        this.literals = new ArrayList<Literal>(values.length);
        for (double value : values) literals.add(new FloatLiteral(value));
    }

    public LiteralValueSet(BigDecimal... values) {
        this.literals = new ArrayList<Literal>(values.length);
        for (BigDecimal value : values) literals.add(new BigDecimalLiteral(value));
    }

    public LiteralValueSet(Date... values) {
        this.literals = new ArrayList<Literal>(values.length);
        for (Date value : values) literals.add(new DateTimeLiteral(value));
    }

    public void write(Output out) {
    	Output.appendList(out, literals, SqlConstants.LIST_SEPARATOR_WITH_SPACE);
    }

	@Override
	public void addParameterValues(List<Object> parameters) {
		Parameters.addParameterValues(parameters, literals);
	}
}
