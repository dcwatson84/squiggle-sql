package com.truemesh.squiggle.literal;

import java.math.BigDecimal;

/**
 * 
 * 
 */
public class BigDecimalLiteral extends LiteralWithSameRepresentationInJavaAndSql<BigDecimal> {
	public BigDecimalLiteral(BigDecimal literalValue) {
		super(literalValue);
	}
}
