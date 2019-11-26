package com.truemesh.squiggle.literal;

import com.truemesh.squiggle.Literal;
import com.truemesh.squiggle.output.Output;

/**
 * 
 * 
 *
 */
@SuppressWarnings("rawtypes")
public class NullLiteral extends Literal {

	@Override
	public void writeContent(Output out) {
		out.print("NULL");
	}
	
}
