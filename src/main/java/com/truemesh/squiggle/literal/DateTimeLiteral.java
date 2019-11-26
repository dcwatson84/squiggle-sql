package com.truemesh.squiggle.literal;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.truemesh.squiggle.SqlConstants;
import com.truemesh.squiggle.output.Output;

/**
 * 
 * 
 *
 */
public class DateTimeLiteral extends LiteralWithSameRepresentationInJavaAndSql<Date> {
	
    private static final String FORMAT_STRING = "yyyy-MM-dd HH:mm:ss.S";
	private static ThreadLocal<DateFormat> FORMAT = new ThreadLocal<DateFormat> () {
		  @Override
		  protected DateFormat initialValue() {
			  return new SimpleDateFormat(FORMAT_STRING);
		  }
	};

	public DateTimeLiteral(Date literalValue) {
        super(literalValue);
	}
	
	@Override
	public void writeContent(Output out) {
		out.print(SqlConstants.LITERAL);
		out.print(FORMAT.get().format(parameterValue));
		out.print(SqlConstants.LITERAL);
	}
}