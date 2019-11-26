package com.truemesh.squiggle.literal;


/**
 * 
 * 
 */
public class IntegerLiteral extends LiteralWithSameRepresentationInJavaAndSql<Long> {
	public IntegerLiteral(long literalValue) {
		super(new Long(literalValue));
	}
	public IntegerLiteral(long literalValue,String alias) {
		super(alias,new Long(literalValue));
	}
}
