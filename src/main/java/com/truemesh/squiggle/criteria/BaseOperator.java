package com.truemesh.squiggle.criteria;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.truemesh.squiggle.Criteria;
import com.truemesh.squiggle.HasParameters;
import com.truemesh.squiggle.SqlConstants;
import com.truemesh.squiggle.Tabular;
import com.truemesh.squiggle.output.Output;
import com.truemesh.squiggle.output.Outputable;
import com.truemesh.squiggle.parameters.Parameters;

/**
 * 
 * 
 *
 */
public abstract class BaseOperator implements HasParameters {
	
	protected Boolean surround = true;
	 protected String operator;
	 protected List<Outputable> parts;

	    public BaseOperator(String operator, Outputable left, Outputable right) {
	        this.parts = Arrays.asList(left,right);
	        this.operator = operator;
	    }
	    
	    public BaseOperator(String operator, Outputable... array) {
	    	 this.parts = Arrays.asList(array);
		        this.operator = operator;
	    }

	    public void write(Output out) {
	        if(surround) out.print(SqlConstants.SUB_CLAUSE_OPEN);
	        String sep = " "+operator+" ";
	        Output.appendList(out, parts, sep);
	        if(surround)  out.print(SqlConstants.SUB_CLAUSE_CLOSE);
	    }

	    public void addReferencedTablesTo(Set<Tabular> tables) {
	    	for(Iterator<Outputable> it = parts.iterator();it.hasNext();){
	        	Outputable part = it.next();
	        	((Criteria)part).addReferencedTablesTo(tables);
	        }
		}

		@Override
		public void addParameterValues(List<Object> parameters) {
			Parameters.addParameterValues(parameters, parts);
		}

}
