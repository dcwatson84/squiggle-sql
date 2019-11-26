package com.truemesh.squiggle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.truemesh.squiggle.output.Output;

/**
 * Special column to represent For SELECT * FROM ...
 * 
 * 
 * 
 * 
 */
public class WildCardColumn extends Projection {
	
	protected static final Logger LOGGER = LoggerFactory.getLogger(WildCardColumn.class);
	
    public WildCardColumn(Tabular table) {
        super(table);
    }

	public void write(Output out) {
		String alias = getTable().getAlias();
		if(alias != null) out.print(alias).print(".");
        out.print("*");
	}

	@Override
	public void writeContent(Output out) {
		
	}
}
