package com.truemesh.squiggle;

import java.util.Set;

import com.truemesh.squiggle.output.Output;

/**
 * What can be selected from a table.
 * 
 * 
 * 
 */
public abstract class Projection extends AliasedSelectable implements Selectable,Matchable {
	protected final Tabular table;
	
	public Projection(Tabular table) {
		this.table = table;
	}
	
	public Tabular getTable() {
		return table;
	}


	@Override
	public void writeContent(Output out) {}

	@Override
	public void addReferencedTablesTo(Set<Tabular> tables) {
		tables.add(table);
	}
	
	
}
