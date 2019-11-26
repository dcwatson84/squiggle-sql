package com.truemesh.squiggle.literal;

import com.truemesh.squiggle.Literal;
import com.truemesh.squiggle.output.Output;

/**
 * 
 * 
 */
public abstract class LiteralWithSameRepresentationInJavaAndSql<C> extends Literal<C> {
	//private final Object literalValue;

	protected LiteralWithSameRepresentationInJavaAndSql(C literalValue) {
		this.parameterValue = literalValue;
	}
	
	protected LiteralWithSameRepresentationInJavaAndSql(String alias,C literalValue) {
		super(alias);
		this.parameterValue = literalValue;
	}

	@Override
	public void writeContent(Output out) {
		out.print(parameterValue);
	}
	
}
