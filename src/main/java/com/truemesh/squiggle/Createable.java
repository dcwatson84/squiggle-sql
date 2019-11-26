package com.truemesh.squiggle;

import com.truemesh.squiggle.output.Output;

/**
 * 
 * 
 *
 */
public interface Createable extends NamedObject {
	
	public void writeObjectType(Output out);
	public void writeContentCreate(Output out);

}
