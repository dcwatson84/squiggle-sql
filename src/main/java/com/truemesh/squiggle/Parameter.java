package com.truemesh.squiggle;

import com.truemesh.squiggle.output.Outputable;


public interface Parameter extends Outputable {
	
	public String getParameterName();
	public Object getParameterValue();

}
