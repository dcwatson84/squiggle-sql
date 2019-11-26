package com.truemesh.squiggle;

import com.truemesh.squiggle.output.Output;
import com.truemesh.squiggle.output.Outputable;
import com.truemesh.squiggle.output.ToStringer;

/**
 * 
 * 
 *
 */
public class DropStatement implements Outputable {

	protected Createable object;
	
	public DropStatement(Createable t){
		object = t;
	}
	
	public String toString() {
	        return ToStringer.toString(this)+SqlConstants.STATEMENT_TERMINATOR;
	}
	
	@Override
	public void write(Output out) {
		out.print("DROP ");
		object.writeObjectType(out);
		out.print(" ");
		object.writeFullName(out);
	}

	
}
