package com.truemesh.squiggle.output;

/**
 * Any object that can output itself as part of the final query should implement this interface.
 *
 * 
 * 
 */
public interface Outputable {
    void write(Output out);
    
}
