package com.truemesh.squiggle;

import java.util.List;

import com.truemesh.squiggle.output.Outputable;

/**
 * 
 * 
 *
 */
public interface Statement extends Outputable {

	public List<Object> getParameterValues();
	
}
