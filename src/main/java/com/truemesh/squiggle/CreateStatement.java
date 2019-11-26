package com.truemesh.squiggle;

import java.util.ArrayList;
import java.util.List;

import com.truemesh.squiggle.output.Output;
import com.truemesh.squiggle.output.Outputable;
import com.truemesh.squiggle.output.ToStringer;

/**
 * 
 * 
 *
 */
public class CreateStatement implements Outputable {

	protected Createable table;
	
	public CreateStatement(Createable t){
		table = t;
	}
	
	public String toString() {
	        return ToStringer.toString(this)+SqlConstants.STATEMENT_TERMINATOR;
	}
	
	@Override
	public void write(Output out) {
		out.print("CREATE ");
		table.writeObjectType(out);
		out.print(" ");
		table.writeContentCreate(out);
	}
	
	public static List<CreateStatement> createIndexStatements(Table object){
		List<CreateStatement> list = new ArrayList<CreateStatement>();
		for(Index index : object.getIndexes()){
			list.add(new CreateStatement(index));
		}
		return list;
	}
	
	
}
