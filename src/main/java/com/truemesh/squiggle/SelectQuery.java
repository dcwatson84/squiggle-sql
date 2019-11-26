package com.truemesh.squiggle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.truemesh.squiggle.criteria.MatchCriteria;
import com.truemesh.squiggle.output.Output;
import com.truemesh.squiggle.output.Outputable;
import com.truemesh.squiggle.output.ToStringer;
import com.truemesh.squiggle.parameters.Parameters;

/**
 * 
 * 
 * 
 */
public class SelectQuery extends AbstractTabular implements Outputable, ValueSet, HasParameters, Statement {
	
	protected static final Logger LOGGER = LoggerFactory.getLogger(SelectQuery.class);
	
	public static enum Clause {
		Select,From,Where,GroupBy,Having,OrderBy;
	}
	
    public static final int indentSize = 4;
    
    private final List<Selectable> selection = new ArrayList<Selectable>();
    private final List<Selectable> groupBy = new ArrayList<Selectable>();
    private final List<Criteria> having = new ArrayList<Criteria>();
    private final List<Criteria> criteria = new ArrayList<Criteria>();
    private final List<Order> order = new ArrayList<Order>();
    
    private final List<JoinedTable> joinedTables = new ArrayList<JoinedTable>();
    private final List<Tabular> externalTables = new ArrayList<Tabular>();

    private boolean isDistinct = false;

    public List<Tabular> listTables() {
    	LinkedHashSet<Tabular> tables = new LinkedHashSet<Tabular>();
    	addReferencedTablesTo(tables);
    	return new ArrayList<Tabular>(tables);
    }

    public void addToSelection(Selectable selectable) {
        selection.add(selectable);
    }
    
    public void addToGroupBy(Selectable selectable) {
        groupBy.add(selectable);
    }
    
    public void addToHaving(Criteria selectable) {
        having.add(selectable);
    }
    
    public void addJoin(JoinedTable table) {
        joinedTables.add(table);
    }

    /**
     * Syntax sugar for addToSelection(Column).
     */
    public void addColumn(Table table, String columname) {
        addToSelection(table.getColumn(columname));
    }

    public void removeFromSelection(Selectable selectable) {
        selection.remove(selectable);
    }

    /**
     * @return a list of {@link Selectable} objects.
     */
    public List<Selectable> listSelection() {
        return Collections.unmodifiableList(selection);
    }

    public boolean isDistinct() {
        return isDistinct;
    }

    public void setDistinct(boolean distinct) {
        isDistinct = distinct;
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

    /**
     * Syntax sugar for addCriteria(JoinCriteria)
     */
    public void addJoin(Table srcTable, String srcColumnname, Table destTable, String destColumnname) {
        addCriteria(new MatchCriteria(srcTable.getColumn(srcColumnname), MatchCriteria.EQUALS, destTable.getColumn(destColumnname)));
    }
    
    public void setExternalTable(Tabular t){
    	externalTables.add(t);
    }

    /**
     * Syntax sugar for addCriteria(JoinCriteria)
     */
    public void addJoin(Table srcTable, String srcColumnName, String operator, Table destTable, String destColumnName) {
        addCriteria(new MatchCriteria(srcTable.getColumn(srcColumnName), operator, destTable.getColumn(destColumnName)));
    }

    public void addOrder(Order order) {
        this.order.add(order);
    }

    /**
     * Syntax sugar for addOrder(Order).
     */
    public void addOrder(Table table, String columnname, boolean ascending) {
        addOrder(new Order(table.getColumn(columnname), ascending));
    }

    public void removeOrder(Order order) {
        this.order.remove(order);
    }

    public List<Order> listOrder() {
        return Collections.unmodifiableList(order);
    }

    public String toString() {
        return ToStringer.toString(this)+SqlConstants.STATEMENT_TERMINATOR;
    }

    public void write(Output out) {
    	write(out,true);
    }
    
    public void write(Output out,Boolean includeJoins) {
    	    	
    	out.currentClause = Clause.Select;
    	
        out.print("SELECT");
        if (isDistinct) {
            out.print(" DISTINCT");
        }
        out.println();

        Output.appendIndentedList(out, selection, SqlConstants.LIST_SEPARATOR_WITH_SPACE);

        out.currentClause = Clause.From;
        
        Set<Tabular> tables = findAllSelectedTables();
        LOGGER.trace("tables: {}",tables);
        if (!tables.isEmpty()) {
        	out.print(" ");
	        out.println("FROM");
	        Output.appendIndentedList(out, tables, SqlConstants.LIST_SEPARATOR_WITH_SPACE);
        }
        
        if(includeJoins && joinedTables.size() > 0){
        	out.print(" ");
        	Output.appendIndentedList(out, joinedTables, " ");
        }
        
        out.currentClause = Clause.Where;
        
        // Add criteria
        if (criteria.size() > 0) {
        	out.print(" ");
            out.println("WHERE");
            Output.appendIndentedList(out, criteria, " AND ");
        }
        
        out.currentClause = Clause.GroupBy;
        
        //add group by
        
        if(groupBy.size()>0){
        	out.print(" GROUP BY ");
        	Output.appendIndentedList(out, groupBy, SqlConstants.LIST_SEPARATOR_WITH_SPACE);
        	
        	out.currentClause = Clause.Having;
        	
        	if(having.size()>0){
        		out.print(" HAVING ");
        		Output.appendIndentedList(out, having, " AND ");
        	}
        }
        
        out.currentClause = Clause.OrderBy;

        // Add order
        if (order.size() > 0) {
        	out.print(" ");
            out.println("ORDER BY");
            Output.appendIndentedList(out, order, SqlConstants.LIST_SEPARATOR_WITH_SPACE);
        }
    }

    
    /**
     * Find all the tables used in the query (from columns, criteria and order).
     *
     * @return Set of {@link com.truemesh.squiggle.Table}s
     */
    @SuppressWarnings("unused")
	private Set<Tabular> findAllUsedTables() {
        Set<Tabular> tables = new LinkedHashSet<Tabular>();
        addReferencedTablesTo(tables);
        return tables;
    }
    
    private Set<Tabular> findAllSelectedTables() {
        Set<Tabular> tables = new LinkedHashSet<Tabular>();
        addReferencedTablesTo(tables);
        for(Iterator<Tabular> it = tables.iterator();it.hasNext();){
        	Tabular tabular = it.next();
        	if(isJoined(tabular) || isExternal(tabular)) it.remove();
        }
        LOGGER.trace("non joined: {}",tables);
        return tables;
    }

    public void addReferencedTablesTo(Set<Tabular> tables) {
    	addReferencedTablesTo(tables, true);
    }
    
    public void addReferencedTablesTo(Set<Tabular> tables,boolean includeJoined) {
        for (Selectable s : selection) {
        	
        	//if(!isJoinedTable(table))
        	LOGGER.trace("addReferencedTablesTo: {}",s);
            s.addReferencedTablesTo(tables);
        }
        for (Criteria c : criteria) {
            c.addReferencedTablesTo(tables);
        }
        for (Order o : order) {
            o.addReferencedTablesTo(tables);
        }
    }
    
    public boolean isJoined(Tabular table){
    	for(JoinedTable jt : joinedTables){
    		if(table.equals(jt.table))return true;
    	}
    	return false;
    }
    
    public boolean isExternal(Tabular table){
    	for(Tabular jt : externalTables){
    		if(table.equals(jt))return true;
    	}
    	return false;
    }
    

	@Override
	public void addParameterValues(List<Object> parameters) {
		Parameters.addParameterValues(parameters, selection);
		Parameters.addParameterValues(parameters, findAllSelectedTables());
		Parameters.addParameterValues(parameters, joinedTables);
		Parameters.addParameterValues(parameters, criteria);
	}

	@Override
	public List<Object> getParameterValues() {
		List<Object> values = new ArrayList<Object>();
		addParameterValues(values);
		return values;
	}
    
    
}
