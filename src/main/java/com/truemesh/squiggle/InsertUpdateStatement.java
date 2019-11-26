package com.truemesh.squiggle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.truemesh.squiggle.literal.DateTimeLiteral;
import com.truemesh.squiggle.literal.IntegerLiteral;
import com.truemesh.squiggle.literal.NullLiteral;
import com.truemesh.squiggle.literal.StringLiteral;
import com.truemesh.squiggle.output.Output;
import com.truemesh.squiggle.output.Outputable;
import com.truemesh.squiggle.output.ToStringer;

/**
 * 
 * 
 *
 */
public abstract class InsertUpdateStatement implements Outputable , HasParameters, Statement {
	
	public static enum ParamStyle {
		LITERAL,INDEXED,NAMED
	}
	
	protected TabularObject table;
		
	//column maps
	protected List<Column> allColumns = new ArrayList<Column>();
	
	protected Map<String,Column> columnMap = new HashMap<String,Column>();
	protected Map<Column,Selectable> columnValues = new HashMap<Column, Selectable>();
	
	protected final List<Criteria> criteria = new ArrayList<Criteria>();
	
	public InsertUpdateStatement(TabularObject table){
		this.table = table;
	}
	

	 public void addCriteria(Criteria criteria) {
	        this.criteria.add(criteria);
	    }

	    public void removeCriteria(Criteria criteria) {
	        this.criteria.remove(criteria);
	    }

	    public List<Criteria> listCriteria() {
	        return Collections.unmodifiableList(criteria);
	    }

	    public void addColumn(String column,String value){
			addColumn(column, new StringLiteral(value));
		}
	    
	    public void addColumn(String column,Long value){
			addColumn(column, new IntegerLiteral(value));
		}
	    
	    public void addColumn(String column,Date value){
			addColumn(column, new DateTimeLiteral(value));
		}
	    
	public void addColumn(String column,Selectable value){
		if(!columnMap.containsKey(column)){
			Column c = table.getColumn(column);
			allColumns.add(c);
			columnMap.put(column, c);
			if(value == null) value = new NullLiteral();
			else columnValues.put(c, value);
			
		}
		else throw new IllegalStateException("Column already exists");	
	}
	

	
	@Override
	public List<Object> getParameterValues() {
		List<Object> values = new ArrayList<Object>();
		addParameterValues(values);
		return values;
	}

	
	public void writeCriteria(Output out){
		 // Add criteria
        if (criteria.size() > 0) {
        	out.print(" ");
            out.println("WHERE");
            Output.appendIndentedList(out, criteria, " AND ");
        }
	}

	public boolean hasColumn(String column){
		return columnMap.containsKey(column);
	}
	
	public String toString() {
		return ToStringer.toString(this);
	}
	
	 public void addReferencedTablesTo(Set<Tabular> tables) {
        for (Criteria c : criteria) {
            c.addReferencedTablesTo(tables);
        }
	 }
}
