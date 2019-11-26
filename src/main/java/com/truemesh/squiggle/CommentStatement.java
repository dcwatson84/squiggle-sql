package com.truemesh.squiggle;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.truemesh.squiggle.output.Output;
import com.truemesh.squiggle.output.Outputable;

/**
 * 
 * 
 *
 */
public class CommentStatement implements Outputable {
	
	protected static final Logger LOGGER = LoggerFactory.getLogger(CommentStatement.class);
	
	private Column column;
	private String comment;
	
	public CommentStatement(Column column, String comment) {
		super();
		this.column = column;
		this.comment = comment;
	}


	public static List<CommentStatement> createCommentStatements(TabularObject object){
		List<CommentStatement> list = new ArrayList<CommentStatement>();
		LOGGER.debug("creating comments for {}",object);
		for(Column column : object.getColumns()){
			LOGGER.debug("comment for {} = {}",column,column.getComment());
			if(column.getComment() != null)
				list.add(new CommentStatement(column, column.getComment()));
		}
		return list;
	}

	@Override
	public void write(Output out) {
		out.print("COMMENT ON COLUMN ");
		column.writeFullName(out);
		out.print(" IS '");
		out.print(comment);
		out.print("'");
	}

}
