package com.truemesh.squiggle.output;

import com.truemesh.squiggle.InsertUpdateStatement.ParamStyle;

/**
 * Utility to quickly grab the complete String from an object that is Outputtable
 *
 * 
 * 
 */
public class ToStringer {
	public static String toString(Outputable outputable,ParamStyle style) {
		 Output out = new Output("    ",style);
	        outputable.write(out);
	        return out.toString();
	}
	public static String toString(Outputable outputable) {
       return toString(outputable,ParamStyle.LITERAL);
    }
}
