package com.truemesh.squiggle.literal;

import java.util.Date;

/**
 * 
 * 
 *
 */
public class Literals {

	public static StringLiteral string(String s){
		return new StringLiteral(s);
	}
	
	public static DateTimeLiteral date(Date d){
		return new DateTimeLiteral(d);
	}
}
