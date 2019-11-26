package com.truemesh.squiggle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.truemesh.squiggle.output.Output;

/**
 * 
 * 
 *
 */
public class View extends TabularObject implements Createable {
	
	protected static final Logger LOGGER = LoggerFactory.getLogger(View.class);
	
	protected SelectQuery query;
	
	public View(String name, String alias, String schema, SelectQuery query) {
		super(name, alias, schema);
		this.query = query;
		//add columns from query to this view
		//FIXME need to find a better way for View to figure out what columns are part of it
		//This currently only exists to support generation of column comments for views
		for(Selectable s : query.listSelection()){
			if(s instanceof Column){
				Column c = (Column) s;
				this.getColumn(c.getName(), c.getType(), c.getLength(), c.getNotNullable(), c.getComment());
			}
		}
	}
	
	public View(String name, String alias, String schema) {
		super(name, alias, schema);
	}

	public View(String name, String alias) {
		super(name, alias);
	}

	public View(String name) {
		super(name);
	}
	

	@Override
	public void writeContentCreate(Output out) {
		if(query == null) throw new IllegalStateException("Cannot create view without query object");
		writeFullName(out);
		out.print(" as select * from (");
		query.write(out,true);
		out.print(")");
	}
	
	@Override
	public void writeObjectType(Output out) {
		out.print("VIEW");
	}


}
