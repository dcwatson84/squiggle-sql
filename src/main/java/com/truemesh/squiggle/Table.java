package com.truemesh.squiggle;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.truemesh.squiggle.output.Output;

/**
 * 
 * 
 * 
 */
public class Table extends TabularObject implements Createable {

	protected List<Index> indexes = new ArrayList<Index>();
	
    public Table(String name) {
		super(name);
	}
    
	public Table(String name, String alias, String schema) {
		super(name, alias, schema);
	}

	public Table(String name, String alias) {
		super(name, alias);
	}
	
	@Override
	public Column getColumn(String columnName,String type,String length,Boolean notnullable,String comment) {
		//Dont create two columns with the same name on a table object
		Column found = findColumn(columnName);
		if(found != null){
			found.setDdl(type, length, comment, notnullable);
			return found;
		}
		else return super.getColumn(columnName, type, length, notnullable, comment);
	}
	
	public void addIndex(String name,List<Column> c){
		Index idx = new Index(name, this, c);
		this.indexes.add(idx);
	}
	
	public List<Index> getIndexes() {
		return indexes;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Table other = (Table) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	
	
	public Table cloneWithAlias(String alias){
		Table t = new Table(name,alias);
		for(Column column : this.columns){
			t.getColumn(column.getName());
		}
		return t;
	}

	@Override
	public void writeContentCreate(Output out) {
		writeFullName(out);
		out.print(" ");
		out.print(SqlConstants.SUB_CLAUSE_OPEN);
		for(Iterator<Column> it = columns.iterator();it.hasNext();){
			Column c = it.next();
			c.writeContentCreate(out);
			if(it.hasNext())out.print(SqlConstants.LIST_SEPARATOR_WITH_SPACE);
		}
		out.print(SqlConstants.SUB_CLAUSE_CLOSE);
	}

	@Override
	public void writeObjectType(Output out) {
		out.print("TABLE");
	}

}
