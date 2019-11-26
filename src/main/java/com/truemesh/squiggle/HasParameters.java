package com.truemesh.squiggle;

import java.util.List;

import com.truemesh.squiggle.output.Outputable;

/**
 * 
 * 
 *
 */
public interface HasParameters extends Outputable {

	//public List<Parameter> getParameters();
	//public List<Object> getParameterValues();
	public void addParameterValues(List<Object> parameters);
	
}
