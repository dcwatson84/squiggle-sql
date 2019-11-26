package com.truemesh.squiggle;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.truemesh.squiggle.InsertUpdateStatement.ParamStyle;
import com.truemesh.squiggle.JoinedTable.JoinType;
import com.truemesh.squiggle.criteria.MatchCriteria;
import com.truemesh.squiggle.criteria.Union;
import com.truemesh.squiggle.literal.IntegerLiteral;
import com.truemesh.squiggle.literal.NullLiteral;
import com.truemesh.squiggle.literal.StringLiteral;
import com.truemesh.squiggle.output.ToStringer;

public class SquiggleTest {

	
	String configTableName = "CONFIGURATION";
	String configTableAlias = "configTable";
	Table configTable = new Table(configTableName,configTableAlias);
	
	String vendTableName = "IDI_VEND";
	String vendAlias = "vendor";
	Table vendTable = new Table(vendTableName,vendAlias);
	
	
	@Test
	public void testGrant(){
		Table t = new Table("TEST_TABLE",null,"DFSYSTEM");
		GrantStatement gs = new GrantStatement(t, Arrays.asList("SELECT","UPDATE"), "MYUSER",Arrays.asList("GRANT"));
		String sql = gs.toString();
		System.out.println(sql);
	}
	
	@Test
	public void testCreateIndex(){
		Table t = new Table("TEST_TABLE",null,"DFSYSTEM");
		Column c0 = t.getColumn("COL1", "VARCHAR2", "256 CHAR",false);
		Column c1 = t.getColumn("COL2", "VARCHAR2", "10 CHAR",false);
		//"STUDY_KEY" NUMBER(10,0), 
		t.getColumn("COL3", "NUMBER", "10,0",false);
		//"INSRT_TS" TIMESTAMP (6)
		t.getColumn("COL4", "TIMESTAMP", "6",false);
		
		t.addIndex("MYINDEX", Arrays.asList(c0,c1));
		List<CreateStatement> s = CreateStatement.createIndexStatements(t);
		for(CreateStatement cs : s){
			String sql = cs.toString();
			System.out.println(sql);
		}
	}
	
	@Test
	public void testCreateView(){
		Table t = new Table("TEST_TABLE","t","DFSYSTEM");
		t.getColumn("COL1", "VARCHAR2", "256 CHAR",false);
		t.getColumn("COL2", "VARCHAR2", "10 CHAR",false);
		//"STUDY_KEY" NUMBER(10,0), 
		t.getColumn("COL3", "NUMBER", "10,0",false);
		//"INSRT_TS" TIMESTAMP (6)
		t.getColumn("COL4", "TIMESTAMP", "6",false);
		
		//
		
		SelectQuery sq = new SelectQuery();
		sq.addToSelection(t.getWildcard());
		
		View v = new View("MY_VIEW", null, "DFSYSTEM", sq);
		
		CreateStatement cs = new CreateStatement(v);
		String sql = cs.toString();
		System.out.println(sql);
	}
	
	@Test
	public void testCreateTable(){
		Table t = new Table("TEST_TABLE",null,"DFSYSTEM");
		t.getColumn("COL1", "VARCHAR2", "256 CHAR",false);
		t.getColumn("COL2", "VARCHAR2", "10 CHAR",false);
		//"STUDY_KEY" NUMBER(10,0), 
		t.getColumn("COL3", "NUMBER", "10,0",false);
		//"INSRT_TS" TIMESTAMP (6)
		t.getColumn("COL4", "TIMESTAMP", "6",false);
		CreateStatement cs = new CreateStatement(t);
		String sql = cs.toString();
		System.out.println(sql);
	}
	
	@Test
	public void testTableWildcard(){
		SelectQuery sq = new SelectQuery();
		Table t = new Table(configTableName,configTableAlias);
		//sq.addColumn(t, t.getWildcard());
		sq.addToSelection(t.getWildcard());
		String sql = sq.toString();
		System.out.println(sql); 
		String expected = "SELECT "+configTableAlias+".* FROM \""+configTableName+"\" "+configTableAlias+";";
		System.out.println("expected:\n"+expected);
		Assert.assertEquals(sql, expected);
	}
	
	@Test
	public void testWildcardNoAlias(){
		SelectQuery sq = new SelectQuery();
		Table t = new Table(configTableName);
		sq.addToSelection(t.getWildcard());
		String sql = sq.toString();
		System.out.println(sql); 
		String expected = "SELECT * FROM \""+configTableName+"\";";
		System.out.println("expected:\n"+expected);
		Assert.assertEquals(sql, expected);
	}
	
	@Test
	public void testInner(){
		SelectQuery sq = new SelectQuery();
		Table t = new Table(configTableName,configTableAlias);
		//sq.addColumn(t, t.getWildcard());
		sq.addToSelection(t.getWildcard());
		System.out.println("INNER: "+sq);
		
		
		SubSelect ss = new SubSelect(sq, "mysel");
		WildCardColumn wcc = ss.getWildcard();
		
		SelectQuery outer = new SelectQuery();
		outer.addToSelection(wcc);
		
		
		String sql = outer.toString();
		System.out.println("OUTER: "+sql);
		//Assert.assertEquals(sql, "SELECT mt.*  FROM MY_TABLE mt ");
	}
	
	@Test
	public void testColumns(){
		Table t = new Table(configTableName,configTableAlias);
		SelectQuery sq = new SelectQuery();
		sq.addToSelection(t.getColumn("NAME"));
		sq.addToSelection(t.getColumn("KEY"));
		sq.addToSelection(t.getColumn("VALUE"));
		String sql = sq.toString();
		System.out.println("SQL:\n"+sql);
	}
	
	@Test
	public void testCase(){
		Table t = new Table(configTableName,configTableAlias);
		SelectQuery sq = new SelectQuery();
		sq.addToSelection(t.getColumn("NAME"));
		sq.addToSelection(t.getColumn("KEY"));
		sq.addToSelection(t.getColumn("VALUE"));
		
		Case c = new Case(t.getColumn("KEY"));
		c.addWhen(new StringLiteral("LOCK_DURATION"), new StringLiteral("CUSTOM1"));
		c.addWhen(new StringLiteral("IO_ENVIRONMENT"), new StringLiteral("CUSTOM2"));
		c.setElse(t.getColumn("KEY"));
		c.setAlias("CUSTOMKEY");
		sq.addToSelection(c);
		String sql = sq.toString();
		//System.out.println("SQL:\n"+sql);
		
		outputStatement(sq);
	}
	
	
	@Test
	public void testMultipleTables(){
		Table config = new Table(configTableName,configTableAlias);
		Table vendor = new Table(vendTableName,vendAlias);
		SelectQuery sq = new SelectQuery();
		sq.addToSelection(config.getColumn("NAME"));
		sq.addToSelection(config.getColumn("KEY"));
		sq.addToSelection(config.getColumn("VALUE"));
		sq.addToSelection(vendor.getColumn("IDI_VEND_ID"));
		sq.addToSelection(vendor.getColumn("VEND_NM"));
		sq.addToSelection(vendor.getColumn("VEND_DESCR"));
		String sql = sq.toString();
		System.out.println("SQL:\n"+sql);
		
		outputStatement(sq);
	}
	
	@Test
	public void testWhereAndOrder(){
		Table config = new Table(configTableName,configTableAlias);
		
		SelectQuery sq = new SelectQuery();
		//sq.addToSelection(config.getColumn("NAME"));
		sq.addToSelection(config.getColumn("KEY"));
		sq.addToSelection(config.getColumn("VALUE"));
	
		
		sq.addCriteria(new MatchCriteria(config.getColumn("NAME"), "=", "DF-ADMIN"));
		sq.addCriteria(new MatchCriteria(config.getColumn("KEY"), "=", "ldap.int.url"));
		
		sq.addOrder(new Order(config.getColumn("KEY"), true));
		
		
		String sql = sq.toString();
		//System.out.println("SQL:\n"+sql);
		Assert.assertEquals(sql, "SELECT configTable.KEY, configTable.VALUE FROM \"CONFIGURATION\" configTable WHERE configTable.NAME = 'DF-ADMIN' AND configTable.KEY = 'ldap.int.url' ORDER BY configTable.KEY;");
		
		outputStatement(sq);
	}
	
	@Test
	public void testUnion(){
		SelectQuery q1 = new SelectQuery();
		q1.addToSelection(configTable.getColumn("NAME"));
		q1.addToSelection(configTable.getColumn("KEY"));
		
		q1.addCriteria(new MatchCriteria(configTable.getColumn("NAME"), "=", "DF-ADMIN"));
		
		SelectQuery q2 = new SelectQuery();
		q2.addToSelection(configTable.getColumn("NAME"));
		q2.addToSelection(configTable.getColumn("KEY"));
		q2.addCriteria(new MatchCriteria(configTable.getColumn("NAME"), "=", "SDH-ADMIN"));
		
		Union u = new Union(q1, q2);
	
		SubSelect ss = new SubSelect(u, "mysel");
		
		
		SelectQuery outer = new SelectQuery();
		outer.addToSelection(ss.getWildcard());

		String sql = outer.toString();
		System.out.println("SQL:\n"+sql);
		
	}
	
	@Test
	public void testCount(){
		SelectQuery q1 = new SelectQuery();
		q1.addToSelection(configTable.getColumn("NAME"));
		//q1.addToSelection(configTable.getColumn("KEY"));
		q1.addToSelection(new Count(configTable.getColumn("NAME")));
		
		q1.addToGroupBy(configTable.getColumn("NAME"));
		
		String sql = q1.toString();
		System.out.println("SQL:\n"+sql);
	}
	
	@Test
	public void testHaving(){
		SelectQuery q1 = new SelectQuery();
		Column nameColumn = configTable.getColumn("NAME");
		q1.addToSelection(nameColumn);
		//q1.addToSelection(configTable.getColumn("KEY"));
		Count count = new Count(nameColumn);
		q1.addToSelection(count);
		
		q1.addToGroupBy(nameColumn);
		
		q1.addToHaving(new MatchCriteria(count, ">", new IntegerLiteral(1)));
		
		q1.addOrder(new Order(count,true));
		
		String sql = q1.toString();
		System.out.println("SQL:\n"+sql);
	}
	
	@Test
	public void testReferenceAlias(){
		SelectQuery q1 = new SelectQuery();
		Column nameColumn = configTable.getColumn("NAME");
		nameColumn.setAlias("mycol");
		q1.addToSelection(nameColumn);
		
		q1.addCriteria(new MatchCriteria(nameColumn, "=","DF-ADMIN"));
		
		//q1.addToSelection(configTable.getColumn("KEY"));
		/*Count count = new Count(nameColumn);
		q1.addToSelection(count);
		
		q1.addToGroupBy(nameColumn);
		
		q1.addToHaving(new MatchCriteria(count, ">", new IntegerLiteral(1)));
		
		q1.addOrder(new Order(count,true));
		*/
		
		String sql = q1.toString();
		System.out.println("SQL:\n"+sql);
	}
	
	@Test
	public void testSelectLiteral(){
		SelectQuery q1 = new SelectQuery();
		Column nameColumn = configTable.getColumn("NAME");
		q1.addToSelection(nameColumn);
		//q1.addToSelection(configTable.getColumn("KEY"));
		Count count = new Count(configTable.getColumn("NAME"));
		q1.addToSelection(count);
		q1.addToSelection(new StringLiteral("mystr"));
		
		
		q1.addToGroupBy(configTable.getColumn("NAME"));
		
		q1.addToHaving(new MatchCriteria(count, ">", new IntegerLiteral(2)));
		
		q1.addOrder(new Order(count,true));
		
		String lit = ToStringer.toString(q1, ParamStyle.LITERAL);
		System.out.println("Literal:\n"+lit);
		String param = ToStringer.toString(q1, ParamStyle.INDEXED);
		System.out.println("Literal:\n"+param);
	}
	
	@Test
	public void testAliases(){
		SelectQuery q1 = new SelectQuery();
		Column nameColumn = configTable.getColumn("NAME");
		q1.addToSelection(nameColumn);
		//q1.addToSelection(configTable.getColumn("KEY"));
		Count count = new Count(configTable.getColumn("NAME"));
		count.setAlias("MYCNT");
		q1.addToSelection(count);
		
		IntegerLiteral il = new IntegerLiteral(1);
		il.setAlias("MYINT");
		q1.addToSelection(il);
		
		StringLiteral sl = new StringLiteral("str");
		sl.setAlias("MYSTR");
		q1.addToSelection(sl);
		
		
		q1.addToGroupBy(configTable.getColumn("NAME"));
		
		//q1.addToHaving(new MatchCriteria(count, ">", new IntegerLiteral(1)));
		//q1.addCriteria(new MatchCriteria(sl, "=",new StringLiteral("str")));
		
		q1.addOrder(new Order(count,true));
		
		//q1.addCriteria(new MatchCriteria(il, "=", new IntegerLiteral(1)));
	
		
		String sql = q1.toString();
		System.out.println("SQL:\n"+sql);
	}
	
	@Test
	public void testDrop(){
		
		Table t = new Table("mytable", null, "myschema");
		DropStatement ds = new DropStatement(t);
		String sql = ToStringer.toString(ds);
		System.out.println("drop: "+sql);
		Assert.assertEquals("DROP TABLE \"myschema\".\"mytable\"", sql);
		
	}
	
	@Test
	public void testFunction(){
		
		SelectQuery q1 = new SelectQuery();
		Column nameColumn = configTable.getColumn("NAME");
		q1.addToSelection(nameColumn);
	
		FunctionCall num = new FunctionCall("TO_NUMBER", new NullLiteral());
		num.setAlias("old");
		q1.addToSelection(num);
		//q1.addCriteria(new MatchCriteria(il, "=", new IntegerLiteral(1)));
	
		
		String sql = q1.toString();
		System.out.println("SQL:\n"+sql);
		
	}
	
	
	@Test
	public void testJoin(){
		
		SelectQuery q1 = new SelectQuery();
		Column nameColumn = configTable.getColumn("NAME");
		Column keyColumn = configTable.getColumn("KEY");
		q1.addToSelection(nameColumn);
		q1.addToSelection(keyColumn);
	
		FunctionCall num = new FunctionCall("TO_NUMBER", new NullLiteral());
		num.setAlias("old");
		q1.addToSelection(num);
		//q1.addCriteria(new MatchCriteria(il, "=", new IntegerLiteral(1)));
		
		Column vendorName = vendTable.getColumn("VEND_NM");
		q1.addToSelection(vendorName);
		MatchCriteria join = new MatchCriteria(keyColumn, "=", vendorName);
		JoinedTable jt = new JoinedTable(vendTable, JoinType.Inner, join);
		
		q1.addJoin(jt);
		
		String sql = q1.toString();
		System.out.println("SQL:\n"+sql);
		
	}
	
	@Test
	public void testSelectParameterValues(){
		SelectQuery q1 = new SelectQuery();
		Column nameColumn = configTable.getColumn("NAME");
		Column keyColumn = configTable.getColumn("KEY");
		q1.addToSelection(nameColumn);
		q1.addToSelection(keyColumn);
		q1.addToSelection(new StringLiteral("mystr"));
		
		q1.addCriteria(new MatchCriteria(configTable.getColumn("NAME"), "=", "DF-ADMIN"));
		q1.addCriteria(new MatchCriteria(configTable.getColumn("KEY"), "LIKE", "%l%"));
		
		String sql = ToStringer.toString(q1, ParamStyle.INDEXED);
		System.out.println("SQL:\n"+sql);
		System.out.println("Values:\n"+q1.getParameterValues());
	}
	
	@Test
	public void testInsertWithParameters(){
		InsertStatement statement = new InsertStatement(configTable);
		//is.addColumn(configTable.getColumn("NAME"), new StringLiteral("MYNAMEVALUE"));
		statement.addColumn("NAME", new StringLiteral("CONFIGNAME"));
		statement.addColumn("KEY", new StringLiteral("MYKEY"));
		statement.addColumn("VALUE", new NullLiteral());
		
		outputStatement(statement);
	}
	
	@Test
	public void testUpdateWithParameters(){
		UpdateStatement statement = new UpdateStatement(configTable);
		//is.addColumn(configTable.getColumn("NAME"), new StringLiteral("MYNAMEVALUE"));
		statement.addColumn("NAME", new StringLiteral("CONFIGNAME"));
		statement.addColumn("KEY", new StringLiteral("MYKEY"));
		statement.addColumn("VALUE", new StringLiteral("SOMEVALUE"));
		
		statement.addCriteria(new MatchCriteria(configTable.getColumn("KEY"), "LIKE", "%l%"));
		
		outputStatement(statement);
	}
	
	public static void outputStatement(Statement statement){
		String ind = ToStringer.toString(statement, ParamStyle.INDEXED);
		String lit = ToStringer.toString(statement, ParamStyle.LITERAL);
		List<Object> values = statement.getParameterValues();
		System.out.println("SQL:\n"+lit);
		System.out.println("SQL:\n"+ind);
		System.out.println("Values:\n"+values);
	}
}
