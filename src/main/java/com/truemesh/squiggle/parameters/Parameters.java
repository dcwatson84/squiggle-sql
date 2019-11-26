package com.truemesh.squiggle.parameters;

import java.util.Collection;
import java.util.List;

import com.truemesh.squiggle.HasParameters;

/**
 * 
 * 
 *
 */
public class Parameters {
	
	public static void addParameterValues(List<Object> parameters,Object possible){
		if(possible instanceof HasParameters) ((HasParameters)possible).addParameterValues(parameters);
	}
	
	public static void addParameterValues(List<Object> parameters,Collection<?> possibles){
		if(possibles != null)
			for(Object possible: possibles) 
				addParameterValues(parameters, possible);
	}

}
