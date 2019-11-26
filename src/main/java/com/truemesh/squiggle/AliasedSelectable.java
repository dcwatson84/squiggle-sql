package com.truemesh.squiggle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.truemesh.squiggle.SelectQuery.Clause;
import com.truemesh.squiggle.output.Output;

/**
 * 
 * 
 *
 */
public abstract class AliasedSelectable implements Selectable,Matchable {
	
	protected static final Logger LOGGER = LoggerFactory.getLogger(AliasedSelectable.class);

	protected String alias;
	
	public AliasedSelectable(){}

	public AliasedSelectable(String alias){
		this.alias = alias;
	}
	
	@Override
	public void write(Output out) {
		boolean contentAndAlias = out.currentClause == Clause.Select || out.currentClause == Clause.From;
		write(out,contentAndAlias);
	}
	
	public void write(Output out,boolean contentAndAlias) {
		if(contentAndAlias){
			writeContent(out);
			if(alias != null){
				out.print(" ");
				out.print(alias);
			}
		}
		else {
			if(alias == null) writeContent(out);
			else out.print(alias);
		}
	}

	public abstract void writeContent(Output out);
	
	
	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}
	
	
	
}
