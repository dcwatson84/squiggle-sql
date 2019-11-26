package com.truemesh.squiggle;

import com.truemesh.squiggle.output.Output;

/**
 * Represents a physical tabular object like a Table or a View
 * Not designed to represent a projection which only exists in the context of a query
 * 
 *
 */
public abstract class TabularObject extends AbstractTabular implements NamedObject {

	protected final String name;
	protected String schema;
	
	 public TabularObject(String name) {
	        this(name, null);
	    }

	    public TabularObject(String name, String alias) {
	    	super(alias);
	        this.name = name;
	    }
	    
	    public TabularObject(String name, String alias, String schema) {
	    	super(alias);
	        this.name = name;
	        this.schema = schema;
	    }

	    public String getName() {
	        return name;
	    }

	   
	    public String getSchema() {
			return schema;
		}

		public WildCardColumn getWildcard() {
	        return new WildCardColumn(this);
	    }

		//FIXME this is only called in select statements so it should ideally be wrapped in something that makes this use obvious
		public void write(Output out) {
	        writeFullName(out);
	        if (hasAlias()) {
	            out.print(' ');
	            out.print(getAlias());
	        }
	    }

		@Override
		public String toString() {
			return "[schema=" + schema + ", name=" + name + "]";
		}
		
		public Table cloneWithAlias(String alias){
			Table t = new Table(name,alias,schema);
			for(Column column : this.columns){
				t.getColumn(column.getName());
			}
			return t;
		}
		
		@Override
		public void writeFullName(Output out){
			if(schema != null){
				out.print(SqlConstants.QUOTE);
				out.print(schema);
				out.print(SqlConstants.QUOTE);
				out.print(SqlConstants.CHILD_SEPARATOR);
			}
			writeName(out);
		}
		
		@Override
		public void writeName(Output out){
			out.print(SqlConstants.QUOTE);
			out.print(name);
			out.print(SqlConstants.QUOTE);
		}

}
