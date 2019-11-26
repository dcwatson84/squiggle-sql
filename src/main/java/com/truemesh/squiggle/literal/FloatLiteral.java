package com.truemesh.squiggle.literal;


/**
 * 
 * 
 */
public class FloatLiteral extends LiteralWithSameRepresentationInJavaAndSql<Double> {
	public FloatLiteral(double literalValue) {
		super(new Double(literalValue));
	}
}
