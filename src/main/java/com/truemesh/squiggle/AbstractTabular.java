package com.truemesh.squiggle;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * 
 *
 */
//FIXME maybe need to remove alias from this class since alias is very specific to select'ing things
public abstract class AbstractTabular implements Tabular  {
	
	protected static final Logger LOGGER = LoggerFactory.getLogger(AbstractTabular.class);
	
    protected final String alias;
    protected List<Column> columns;
    
    public AbstractTabular() {
        this( null);
    }

    public AbstractTabular(String alias) {
        this.alias = alias;
        this.columns = new ArrayList<Column>();
    }



    /**
     * Whether this table has an alias assigned.
     */
    public boolean hasAlias() {
        return alias != null;
    }

    /**
     * Short alias of table
     */
    public String getAlias() {
        return alias;
    }
    
    /**
     * Get a column for a particular table.
     */
    public Column getColumn(String columnName) {
    	return getColumn(columnName, null, null, null);
    }
    
    public Column getColumn(String columnName,String type,String length,Boolean notnullable,String comment) {
    	Column c = new Column(this, columnName,type,length,notnullable,comment);
    	columns.add(c);
    	return c;
    }
    
    public Column getColumn(String columnName, String type, String length,Boolean notnullable) {
		return getColumn(columnName, type, length, notnullable, null);
	}

    public WildCardColumn getWildcard() {
        return new WildCardColumn(this);
    }

    public List<Column> getColumns(){
    	return columns;
    }
    
    public Column removeColumn(String name){
    	for(Iterator<Column> it = columns.iterator();it.hasNext();){
    		Column col = it.next();
    		if(col.getName().equals(name)){
    			it.remove();
    			return col;
    		}
    	}
    	return null;
    }
    
    public Column findColumn(String name){
    	for(Iterator<Column> it = columns.iterator();it.hasNext();){
    		Column col = it.next();
    		if(col.getName().equals(name)){
    			return col;
    		}
    	}
    	return null;
    }
    
    public Boolean hasColumn(String name){
    	return findColumn(name) != null;
    }

	
}
