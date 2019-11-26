package com.truemesh.squiggle;

import java.util.List;
import java.util.Set;

import com.truemesh.squiggle.output.Output;
import com.truemesh.squiggle.parameters.Parameters;

/**
 * 
 * 
 *
 */
public class Cast extends AliasedSelectable implements Matchable, Selectable, HasParameters {

	// private final String functionName;
	    //private final Matchable[] arguments;
	Matchable argument;
	String type;
	Matchable atTimeZone;
	
	public Cast(Matchable argument,String type) {
		this(null,argument,type,null);
	}
	
	public Cast(String alias,Matchable argument,String type) {
		this(alias,argument,type,null);
	}
	
	 public Cast(Matchable argument,String type,Matchable atTimeZone) {
		 this(null,argument,type,atTimeZone);
	 }
	
    public Cast(String alias,Matchable argument,String type,Matchable atTimeZone) {
    	super(alias);
    	this.argument = argument;
    	this.type = type;
    	this.atTimeZone = atTimeZone;
    }

    

    public void addReferencedTablesTo(Set<Tabular> tables) {
    	argument.addReferencedTablesTo(tables);
    }
    

	@Override
	public void writeContent(Output out) {
		
		 out.print("cast(");
		 argument.write(out);
		 out.print(" as ");
		 out.print(type);
		 out.print(")");
		 if(atTimeZone != null){
			 out.print(" at time zone ");
			 atTimeZone.write(out);
		 }
	}


	@Override
	public void addParameterValues(List<Object> parameters) {
		Parameters.addParameterValues(parameters, argument);
		Parameters.addParameterValues(parameters, atTimeZone);
	}
	
}
