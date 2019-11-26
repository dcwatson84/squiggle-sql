package com.truemesh.squiggle;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.truemesh.squiggle.output.Output;
import com.truemesh.squiggle.parameters.Parameters;

/**
 * 
 * 
 *
 */
public class FunctionCall extends AliasedSelectable implements Matchable, Selectable,Criteria {
	
	protected static final Logger LOGGER = LoggerFactory.getLogger(FunctionCall.class);
	
    private final String functionName;
    private final List<Selectable> arguments;

    public FunctionCall(String functionName, Selectable... arguments) {
    	this(functionName,null,arguments);
    }
    
    public FunctionCall(String functionName, String alias, Selectable... arguments) {
    	super(alias);
        this.functionName = functionName;
        this.arguments = (arguments!=null)?Arrays.asList(arguments):null;
    }
    


    public void addReferencedTablesTo(Set<Tabular> tables) {
    	for (Selectable argument : arguments) {
    		argument.addReferencedTablesTo(tables);
		}
    }
    

	@Override
	public void writeContent(Output out) {
		 out.print(functionName);
		 out.print(SqlConstants.SUB_CLAUSE_OPEN);
		 if(arguments != null){
			 Output.appendList(out, arguments, SqlConstants.LIST_SEPARATOR_WITH_SPACE);
		 }
	     out.print(SqlConstants.SUB_CLAUSE_CLOSE);
	}

	@Override
	public void addParameterValues(List<Object> parameters) {
		Parameters.addParameterValues(parameters, arguments);
	}


	
}
