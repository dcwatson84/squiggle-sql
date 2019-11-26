package com.truemesh.squiggle;

import java.util.Set;

import com.truemesh.squiggle.output.Output;
import com.truemesh.squiggle.output.Outputable;

/**
 * 
 * 
 * 
 */
public interface Criteria extends Outputable, HasParameters {
    public void write(Output out);
    public abstract void addReferencedTablesTo(Set<Tabular> tables);
}
