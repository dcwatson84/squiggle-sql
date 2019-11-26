package com.truemesh.squiggle;

import java.util.Collection;

import com.truemesh.squiggle.output.Output;
import com.truemesh.squiggle.output.Outputable;
import com.truemesh.squiggle.output.ToStringer;

/**
 * 
 * 
 *
 */
public class GrantStatement implements Outputable {
	
	protected NamedObject object;
	protected Collection<String> privileges;
	protected Collection<String> options;
	protected String principle;
	
	public GrantStatement(NamedObject object, Collection<String> privileges,String principle,Collection<String> options) {
		super();
		this.object = object;
		this.privileges = privileges;
		this.principle = principle;
		this.options = options;
	}
	
	public GrantStatement(NamedObject object, Collection<String> privileges,String principle) {
		this(object,privileges,principle,null);
	}

	@Override
	public void write(Output out) {
		out.print("GRANT ");
		Output.appendList(out, privileges, SqlConstants.LIST_SEPARATOR_WITH_SPACE);
		out.print(" ON ");
		object.writeFullName(out);
		out.print(" TO ");
		out.print(principle);
		if(options != null && options.size() > 0){
			out.print(" WITH ");
			Output.appendList(out, options, SqlConstants.LIST_SEPARATOR_WITH_SPACE);
			out.print(" OPTION");
		}
	}
	
	public String toString() {
        return ToStringer.toString(this)+SqlConstants.STATEMENT_TERMINATOR;
	}
}
