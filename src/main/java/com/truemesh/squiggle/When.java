package com.truemesh.squiggle;

import java.util.List;

import com.truemesh.squiggle.output.Output;
import com.truemesh.squiggle.output.Outputable;
import com.truemesh.squiggle.parameters.Parameters;

/**
 * 
 * 
 *
 */
public class When implements Outputable, HasParameters {
	
	protected Selectable match;
	protected Selectable output;
	
	public When(Selectable match, Selectable output) {
		super();
		this.match = match;
		this.output = output;
	}

	@Override
	public void write(Output out) {
		out.print("when ");
		out.print(match);
		out.print(" then ");
		out.print(output);
	}

	@Override
	public void addParameterValues(List<Object> parameters) {
		Parameters.addParameterValues(parameters, match);
		Parameters.addParameterValues(parameters, output);
	}
	
	

}
