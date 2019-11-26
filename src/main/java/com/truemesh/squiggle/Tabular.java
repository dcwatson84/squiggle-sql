package com.truemesh.squiggle;

import com.truemesh.squiggle.output.Outputable;

/**
 * Represents anything tabular, even things that dont physically exist like select statements
 * 
 *
 */
public interface Tabular extends Outputable  {

	 public boolean hasAlias();
	 public String getAlias();
	
}
