package com.truemesh.squiggle.output;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.truemesh.squiggle.InsertUpdateStatement.ParamStyle;
import com.truemesh.squiggle.Parameter;
import com.truemesh.squiggle.SelectQuery.Clause;

/**
 * The Output is where the elements of the query output their bits of SQL to.
 *
 * 
 * 
 */
public class Output {

	protected static final Logger LOGGER = LoggerFactory.getLogger(Output.class);
	
    /**
     * @param indent String to be used for indenting (e.g. "", "  ", "       ", "\t")
     */
	public Boolean useIndent = false;
	public Boolean useNewline = false;
	
	 public Output(String indent,ParamStyle style) {
	        this.indent = indent;
	        this.paramStyle = style;
	  }
    public Output(String indent) {
        this(indent,ParamStyle.LITERAL);
    }

    private StringBuffer result = new StringBuffer();
    private StringBuffer currentIndent = new StringBuffer();
    private boolean newLineComing;

    private final String indent;
    
    public Clause currentClause;
    protected ParamStyle paramStyle;

    public String toString() {
        return result.toString();
    }

    public Output print(Object o) {
    	LOGGER.trace("print:{}",o);
        writeNewLineIfNeeded();
        //NOTE allow object to output itself if it can?
        if(o instanceof Parameter)writeParam((Parameter) o);
        else if(o instanceof Outputable) ((Outputable)o).write(this);
        else result.append(o);
        return this;
    }

    public Output writeParam(Parameter paramable) {
    	LOGGER.trace("writeParam:{} ",paramable);
		switch (paramStyle) {
		case INDEXED:
			print("?");
			break;
		case LITERAL:
			paramable.write(this);
			break;
		case NAMED:
			print(":");
			print(paramable.getParameterName());
			break;
		}
		return this;
	}
    
    public Output print(char c) {
    	LOGGER.trace("print:{}",c);
        writeNewLineIfNeeded();
        result.append(c);
        return this;
    }

    public Output println(Object o) {
    	LOGGER.trace("println:{}",o);
    	print(o);
        newLineComing = true;
        return this;
    }

    public Output println() {
    	LOGGER.trace("println");
        newLineComing = true;
        return this;
    }

    public void indent() {
        if(useIndent)currentIndent.append(indent);
        
    }

    public void unindent() {
    	if(useIndent) currentIndent.setLength(currentIndent.length() - indent.length());
    }

    private void writeNewLineIfNeeded() {
    	LOGGER.trace("writeNewLineIfNeeded {} to: {}",newLineComing,result);
        if (newLineComing) {
        	if(useNewline){
	            result.append('\n');
        	}
            else {
            	result.append(" ");
            }
        	result.append(currentIndent);
        	newLineComing = false;
        }
    	
    	
    }

    
    public static void appendIndentedList(Output out, Collection<? extends Outputable> things, String seperator) {
        out.indent();
        appendList(out, things, seperator);
        out.unindent();
    }
    
    public static void appendList(Output out,Object[] collection, String seperator) {
    	appendList(out, Arrays.asList(collection), seperator);
    }
    
    /**
     * Iterate through a Collection and append all entries (using .toString()) to
     * a StringBuffer.
     */
    public static void appendList(Output out, Collection<?> collection, String seperator) {
    	appendList(out, collection, seperator, OUTPUTTER);
    }
    
    public static void appendList(Output out, Collection<?> collection, String seperator,Outputter outputter) {
    	
        Iterator<?> i = collection.iterator();
        boolean hasNext = i.hasNext();

        while (hasNext) {
        	Object object = i.next();
        	out.print(object);
            hasNext = i.hasNext();
            if (hasNext) {
                out.print(seperator);
            }
        }

    }
    
    public interface Outputter {
    	public void out(Output out,Outputable outputable);
    }
    
    public static Outputter OUTPUTTER = new Outputter(){
		@Override
		public void out(Output out,Outputable outputable) {
			outputable.write(out);
		}
    	
    };
}
