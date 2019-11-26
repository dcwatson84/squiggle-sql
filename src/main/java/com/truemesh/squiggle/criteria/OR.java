package com.truemesh.squiggle.criteria;

import com.truemesh.squiggle.Criteria;



/**
 * Used for adding CRITERIA1 OR CRITERIA2 to a statement.
 * <p/>
 * <pre>
 * SelectQuery select = ...
 * ...
 * Criteria a = new MatchCriteria(table, col1, "=", 1);
 * Criteria b = new MatchCriteria(table, col2, "=", 2);
 * select.addCriteria(new OR(a, b));
 * // ( table.col1 = 1 OR table.col2 = 2 )
 * </pre>
 * 
 * 
 * 
 */
public class OR extends BaseLogicGroup {

    public OR(Criteria... parts) {
        super("OR", parts);
    }

}
