package com.truemesh.squiggle;

import com.truemesh.squiggle.output.Output;

/**
 * Represents a physical named object in the database. i.e. Table,View,Function. Something that can be physically hard referenced by things
 * like create/drop/grant.
 * 
 *
 */
public interface NamedObject {
	
	public void writeName(Output out);
	
	public void writeFullName(Output out);

}
