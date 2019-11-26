package com.truemesh.squiggle;

import java.util.Set;

/**
 * 
 * 
 *
 */
public class Utils {
	
	public static Set<Tabular> combine(Set<Tabular>... tabulars){
		Set<Tabular> base = null;
		for(Set<Tabular> tabular : tabulars){
			if(tabular != null){
				if(base == null) base = tabular;
				else base.addAll(tabular);
			}
		}
		return base;
	}

}
