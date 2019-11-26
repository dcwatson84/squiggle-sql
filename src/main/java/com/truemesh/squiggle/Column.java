package com.truemesh.squiggle;


import com.truemesh.squiggle.output.Output;

/**
 * 
 * 
 * 
 */
public class Column extends Projection implements Matchable, Createable, NamedObject {
	
	private final String name;
	private String type;
	private String length;
	private Boolean notNullable;
	private String comment;
	
	 public Column(Tabular table, String name,String type,String len,Boolean notnull,String comment) {
        super(table);
        this.name = name;
	    this.type = type;
	    this.length = len;
	    this.notNullable = notnull;
	    this.comment = comment;
	 }
	
	 public Column(Tabular table, String name,String type,String len,Boolean notnull) {
        super(table);
        this.name = name;
	    this.type = type;
	    this.length = len;
	    this.notNullable = notnull;
	 }
	
    public Column(Tabular table, String name) {
        super(table);
        this.name = name;
    }

    public String getName() {
        return name;
    }
    
	public String getType() {
		return type;
	}

	public String getLength() {
		return length;
	}

	public String getComment() {
		return comment;
	}

	public Boolean getNotNullable() {
		return notNullable;
	}

	public void setDdl(String type, String length,String comment,Boolean notNullable){
		this.type = type;
		this.length = length;
		this.comment = comment;
		this.notNullable = notNullable;
	}
	
	public int hashCode() {
		final int prime = 31;
		int result = getTable().hashCode();
		result = prime * result + name.hashCode();
		return result;
	}
	
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null)
			return false;
		if (getClass() != o.getClass())
			return false;
		
		final Column that = (Column)o;
		
		return this.name.equals(that.name) 
		    && this.getTable().equals(that.getTable()); 
	}

	@Override
	public void writeContent(Output out) {
		String alias = getTable().getAlias();
		if(alias != null) out.print(alias+SqlConstants.CHILD_SEPARATOR);
		out.print(SqlConstants.QUOTE);
		out.print(getName());
		out.print(SqlConstants.QUOTE);
	}

	@Override
	public String toString() {
		return "[name=" + name + "]";
	}

	@Override
	public void writeContentCreate(Output out) {
		if(type == null) throw new IllegalStateException("Cannot create without column type");
		writeName(out);
		out.print(" ");
		out.print(type);
		if(length != null){
			out.print(SqlConstants.SUB_CLAUSE_OPEN);
			out.print(length);
			out.print(SqlConstants.SUB_CLAUSE_CLOSE);
		}
		if(Boolean.TRUE.equals(notNullable)){
			out.print(" NOT NULL");
		}
	}
	
	@Override
	public void writeObjectType(Output out) {
		out.print("");
	}
	
	@Override
	public void writeFullName(Output out){
		if(!(table instanceof NamedObject)) throw new IllegalArgumentException("Column is not on Named implementation");
		NamedObject object = (NamedObject) table;
		object.writeFullName(out);
		out.print(SqlConstants.CHILD_SEPARATOR);
		writeName(out);
	}

	@Override
	public void writeName(Output out) {
		out.print(SqlConstants.QUOTE);
		out.print(name);
		out.print(SqlConstants.QUOTE);
	}
	
	
}
