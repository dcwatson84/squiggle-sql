package com.truemesh.squiggle;

import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.truemesh.squiggle.output.Output;
import com.truemesh.squiggle.output.Outputable;

/**
 * A literal value, such as a number, string or boolean.
 * 
 * 
 * 
 */
public abstract class Literal<C> extends AliasedSelectable implements Outputable, Matchable, Selectable, Parameter, HasParameters {
	
	protected static final Logger LOGGER = LoggerFactory.getLogger(Literal.class);
	
	protected String parameterName;
	protected C parameterValue;
	
	public Literal() {
		super();
	}
	
	public Literal(String alias) {
		super(alias);
	}

	public void addReferencedTablesTo(Set<Tabular> tables) {
	}

	public String getParameterName() {
		return parameterName;
	}

	public void setParameterName(String parameterName) {
		this.parameterName = parameterName;
	}

	public C getParameterValue() {
		return parameterValue;
	}

	public void setParameterValue(C parameterValue) {
		this.parameterValue = parameterValue;
	}

	@Override
	public void addParameterValues(List<Object> parameters) {
		LOGGER.trace("adding: {}",parameterValue);
		parameters.add(parameterValue);
	}

	@Override
	public void writeContent(Output out) {}
	
	
}
