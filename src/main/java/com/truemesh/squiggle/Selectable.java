package com.truemesh.squiggle;

import java.util.Set;

import com.truemesh.squiggle.output.Outputable;

/**
 * Something that can be returned from a select query
 * 
 * 
 * 
 */
public interface Selectable extends Outputable {
	void addReferencedTablesTo(Set<Tabular> tables);
}
