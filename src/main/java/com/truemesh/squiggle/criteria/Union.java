package com.truemesh.squiggle.criteria;

import com.truemesh.squiggle.Tabular;

/**
 * 
 * 
 *
 */
public class Union extends BaseOperator implements Tabular {


	public Union(Tabular left, Tabular right) {
		this(left,right,true);
	}
	
	public Union(Tabular left, Tabular right,Boolean all) {
        super("UNION"+(all?" ALL":""), left, right);
        this.surround = false;
    }

	@Override
	public boolean hasAlias() {
		return false;
	}

	@Override
	public String getAlias() {
		return null;
	}

}
