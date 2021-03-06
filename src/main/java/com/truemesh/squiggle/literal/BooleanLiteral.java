package com.truemesh.squiggle.literal;

/**
 * 
 * 
 */
public class BooleanLiteral extends LiteralWithSameRepresentationInJavaAndSql<Boolean> {
	public static BooleanLiteral TRUE = new BooleanLiteral(true);
	public static BooleanLiteral FALSE = new BooleanLiteral(false);
	
	public BooleanLiteral(boolean literalValue) {
		this(Boolean.valueOf(literalValue));
	}
	
	public BooleanLiteral(Boolean literalValue) {
		super(literalValue);
	}
}
