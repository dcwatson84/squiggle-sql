package com.truemesh.squiggle.criteria;

import com.truemesh.squiggle.Criteria;

/**
 * See OR and AND
 * 
 * 
 * 
 */
public abstract class BaseLogicGroup extends BaseOperator implements Criteria {

    public BaseLogicGroup(String operator, Criteria left, Criteria right) {
    	super(operator,left,right);
    }


	public BaseLogicGroup(String operator, Criteria... array) {
		super(operator, array);
	}
}
