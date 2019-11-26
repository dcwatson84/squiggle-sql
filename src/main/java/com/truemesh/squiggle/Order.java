package com.truemesh.squiggle;

import java.util.Set;

import com.truemesh.squiggle.output.Output;
import com.truemesh.squiggle.output.Outputable;

/**
 * ORDER BY clause. See SelectQuery.addOrder(Order).
 * 
 * 
 * 
 */
public class Order implements Outputable {
    public static final boolean ASCENDING = true;
    public static final boolean DESCENDING = false;
    
    private Projection projection;
    private boolean ascending;
    
    /**
     * @param column    Column to order by.
     * @param ascending Order.ASCENDING or Order.DESCENDING
     */
    public Order(Projection column, boolean ascending) {
        this.projection = column;
        this.ascending = ascending;
    }

    public Projection getProjection() {
        return projection;
    }

    public void write(Output out) {
        projection.write(out, false);
        if (!ascending) {
            out.print(" DESC");
        }
    }

	public void addReferencedTablesTo(Set<Tabular> tables) {
		projection.addReferencedTablesTo(tables);
	}
}
