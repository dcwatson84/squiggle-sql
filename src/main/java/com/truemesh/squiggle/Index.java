package com.truemesh.squiggle;

import java.util.List;

import com.truemesh.squiggle.output.Output;


/**
 * 
 * 
 *
 */
public class Index implements Createable, NamedObject {
	
	protected List<Column> columns;
	protected String name;
	protected TabularObject tabularObject;
	
	public Index(String name,TabularObject object,List<Column> c){
		this.columns = c;
		this.name = name;
		this.tabularObject = object;
	}


	@Override
	public void writeContentCreate(Output out) {
		writeName(out);
		out.print(" ON ");
		tabularObject.writeFullName(out);
		out.print(" ");
		out.print(SqlConstants.SUB_CLAUSE_OPEN);
		Output.appendList(out, columns, SqlConstants.LIST_SEPARATOR);
		out.print(SqlConstants.SUB_CLAUSE_CLOSE);
	}

	@Override
	public void writeObjectType(Output out) {
		out.print("INDEX");
	}

	@Override
	public void writeName(Output out) {
		out.print(SqlConstants.QUOTE);
		out.print(name);
		out.print(SqlConstants.QUOTE);
	}


	@Override
	public void writeFullName(Output out) {
		writeName(out);
	}
	
	

}
