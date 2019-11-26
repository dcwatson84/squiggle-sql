package com.truemesh.squiggle.criteria;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.truemesh.squiggle.Column;
import com.truemesh.squiggle.Matchable;
import com.truemesh.squiggle.Table;
import com.truemesh.squiggle.Tabular;
import com.truemesh.squiggle.literal.BooleanLiteral;
import com.truemesh.squiggle.literal.FloatLiteral;
import com.truemesh.squiggle.literal.IntegerLiteral;
import com.truemesh.squiggle.literal.StringLiteral;
import com.truemesh.squiggle.output.Output;
import com.truemesh.squiggle.parameters.Parameters;

/**
 * 
 * 
 * 
 * 
 */
public class MatchCriteria extends BaseCriteria {
	
	protected static final Logger LOGGER = LoggerFactory.getLogger(MatchCriteria.class);
	
    public static final String EQUALS = "=";
    public static final String GREATER = ">";
    public static final String GREATEREQUAL = ">=";
    public static final String LESS = "<";
    public static final String LESSEQUAL = "<=";
    public static final String LIKE = "LIKE";
    public static final String NOTEQUAL = "<>";

    private final Matchable left;
    private final String operator;
    private final Matchable right;

    public MatchCriteria(Matchable left, String operator, Matchable right) {
        this.left = left;
        this.operator = operator;
        this.right = right;
    }

    public MatchCriteria(Column column, String matchType, boolean value) {
    	this(column, matchType, new BooleanLiteral(value));
    }

    /**
     * Initializes a MatchCriteria with a given column, comparison operator, and
     * date operand that the criteria will use to make a comparison between the
     * given column and the date.
     *
     * @param column   the column to use in the date comparison.
     * @param operator the comparison operator to use in the date comparison.
     * @param operand  the date literal to use in the comparison.
     */
    public MatchCriteria(Column column, String operator, Date operand) {
        this(column, operator, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").format(operand));
    }

    public MatchCriteria(Column column, String matchType, double value) {
    	this(column, matchType, new FloatLiteral(value));
    }

    public MatchCriteria(Column column, String matchType, long value) {
    	this(column, matchType, new IntegerLiteral(value));
    }

    public MatchCriteria(Column column, String matchType, String value) {
    	this(column, matchType, new StringLiteral(value));
    }

    public MatchCriteria(Table table, String columnname, String matchType, boolean value) {
        this(table.getColumn(columnname), matchType, value);
    }

    /**
     * Initializes a MatchCriteria with a table, column name is this table,
     * comparison operator, and date operand that the criteria will use to make a
     * comparison between the given table column and the date.
     *
     * @param table      the table that contains a column having the given name to use in
     *                   the date comparison.
     * @param columnName the name of the column to use in the date comparison.
     * @param operator   the comparison operator to use in the date comparison.
     * @param operand    the date literal to use in the comparison.
     */
    public MatchCriteria(Table table, String columnName, String operator, Date operand) {
        this(table.getColumn(columnName), operator, operand);
    }

    public MatchCriteria(Table table, String columnname, String matchType, double value) {
        this(table.getColumn(columnname), matchType, value);
    }

    public MatchCriteria(Table table, String columnname, String matchType, long value) {
        this(table.getColumn(columnname), matchType, value);
    }

    public MatchCriteria(Table table, String columnname, String matchType,  String value) {
        this(table.getColumn(columnname), matchType, value);
    }

    public Matchable getLeft() {
        return left;
    }
    
    public String getComparisonOperator() {
    	return operator;
    }
    
    public Matchable getRight() {
    	return right;
    }
    
    public void write(Output out) {
    	//left.write(out);
    	out.print(left);
        out.print(' ').print(operator).print(' ');
        out.print(right);
        //right.write(out);
    }

    public void addReferencedTablesTo(Set<Tabular> tables) {
		left.addReferencedTablesTo(tables);
		right.addReferencedTablesTo(tables);
	}
    
    @Override
	public void addParameterValues(List<Object> parameters) {
		Parameters.addParameterValues(parameters, left);
		Parameters.addParameterValues(parameters, right);
	}
}
