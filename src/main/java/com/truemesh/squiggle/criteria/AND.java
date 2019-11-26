package com.truemesh.squiggle.criteria;

import com.truemesh.squiggle.Criteria;



/**
 * Used for adding CRITERIA1 AND CRITERIA2 to a statement.
 * <p/>
 * <pre>
 * SelectQuery select = ...
 * ...
 * Criteria a = new MatchCriteria(table, col1, "=", 1);
 * Criteria b = new MatchCriteria(table, col2, "=", 2);
 * select.addCriteria(new AND(a, b));
 * // ( table.col1 = 1 AND table.col2 = 2 )
 * </pre>
 * 
 * 
 * 
 */
public class AND extends BaseLogicGroup {

    public AND(Criteria... parts) {
        super("AND", parts);
    }

}
