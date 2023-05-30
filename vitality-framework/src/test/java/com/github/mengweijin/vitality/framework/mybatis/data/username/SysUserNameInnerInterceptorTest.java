package com.github.mengweijin.vitality.framework.mybatis.data.username;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Alias;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.AllColumns;
import net.sf.jsqlparser.statement.select.AllTableColumns;
import net.sf.jsqlparser.statement.select.Join;
import net.sf.jsqlparser.statement.select.OrderByElement;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectExpressionItem;
import net.sf.jsqlparser.statement.select.SelectItem;
import net.sf.jsqlparser.statement.select.SubSelect;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

/**
 * @author mengweijin
 * @date 2023/4/16
 */
public class SysUserNameInnerInterceptorTest {

    @Test
    public void plainSelectTest() throws JSQLParserException {
        String sql = """
            select * from SYS_DEPT 
            where TYPE = 'AA' AND CODE = '1001' 
            order by SEQ DESC
        """;

        Statement statement = CCJSqlParserUtil.parse(sql);

        if (statement instanceof Select select) {
            PlainSelect plainSelect = (PlainSelect) select.getSelectBody();

            // select
            AllColumns selectItem = (AllColumns) plainSelect.getSelectItems().get(0);
            Assertions.assertEquals("*", selectItem.toString());

            // from table
            Table table = plainSelect.getFromItem(Table.class);
            Assertions.assertEquals("SYS_DEPT", table.getName());

            // where
            AndExpression andExpression = (AndExpression) plainSelect.getWhere();
            EqualsTo equalsTo1 = andExpression.getLeftExpression(EqualsTo.class);
            Column equalsTo1LeftExpression = equalsTo1.getLeftExpression(Column.class);
            StringValue equalsTo1RightExpression = equalsTo1.getRightExpression(StringValue.class);
            Assertions.assertEquals("TYPE", equalsTo1LeftExpression.getColumnName());
            Assertions.assertEquals("AA", equalsTo1RightExpression.getValue());

            EqualsTo equalsTo2 = andExpression.getRightExpression(EqualsTo.class);
            Column equalsTo2LeftExpression = equalsTo2.getLeftExpression(Column.class);
            StringValue equalsTo2RightExpression = equalsTo2.getRightExpression(StringValue.class);
            Assertions.assertEquals("CODE", equalsTo2LeftExpression.getColumnName());
            Assertions.assertEquals("1001", equalsTo2RightExpression.getValue());

            // order by
            OrderByElement orderByElement = plainSelect.getOrderByElements().get(0);
            Column expression = orderByElement.getExpression(Column.class);
            Assertions.assertEquals("SEQ", expression.getColumnName());
            Assertions.assertFalse(orderByElement.isAsc());
            Assertions.assertTrue(orderByElement.isAscDescPresent());
        }
    }

    @Test
    public void plainSelectLeftJoinTest() throws JSQLParserException {
        String sql = """
            select dict.*, user.nick_name from SYS_DICT dict 
            left join SYS_USER user on user.id = dict.create_by
        """;

        Statement statement = CCJSqlParserUtil.parse(sql);

        if (statement instanceof Select select) {
            PlainSelect plainSelect = (PlainSelect) select.getSelectBody();

            // select
            AllTableColumns allTableColumns = (AllTableColumns) plainSelect.getSelectItems().get(0);
            Assertions.assertEquals("dict.*", allTableColumns.toString());
            SelectExpressionItem selectExpressionItem = (SelectExpressionItem) plainSelect.getSelectItems().get(1);
            Assertions.assertEquals("user.nick_name", selectExpressionItem.toString());

            // from table
            Table table = plainSelect.getFromItem(Table.class);
            Assertions.assertEquals("SYS_DICT dict", table.toString());
            Assertions.assertEquals("SYS_DICT", table.getName());
            Assertions.assertEquals("dict", table.getAlias().getName());

            // joins
            Join join = plainSelect.getJoins().get(0);
            Assertions.assertEquals("SYS_USER user", join.getRightItem().toString());

            EqualsTo joinEqualsTo = (EqualsTo) ((LinkedList<?>) join.getOnExpressions()).get(0);
            Assertions.assertEquals("user.id = dict.create_by", joinEqualsTo.toString());
        }
    }

    @Test
    public void plainSelectLeftJoinWithSubQueryTest() throws JSQLParserException {
        String sql = """
            select t1.*, user.nick_name from (
                select ID, NAME, CODE, CREATE_BY, CREATE_TIME from SYS_DEPT 
                where TYPE = 'AA' AND CODE = '1001' 
                order by SEQ DESC
            ) as t1
            left join SYS_USER user on user.id = t1.create_by
        """;

        Statement statement = CCJSqlParserUtil.parse(sql);

        if (statement instanceof Select select) {
            PlainSelect plainSelect = (PlainSelect) select.getSelectBody();
            System.out.println();
        }
    }


    @Test
    public void plainSelectAddLeftJoinWithSubQueryTest() throws JSQLParserException {
        String sql = """
            select ID, NAME, CODE, CREATE_BY, CREATE_TIME from SYS_DEPT 
            where TYPE = 'AA' AND CODE = '1001' 
            order by SEQ DESC
        """;

        Statement statement = CCJSqlParserUtil.parse(sql);

        if (statement instanceof Select select) {
            PlainSelect plainSelect = (PlainSelect) select.getSelectBody();

            PlainSelect all = new PlainSelect();
            ArrayList<SelectItem> list = new ArrayList<>();
            AllTableColumns columns = new AllTableColumns(new Table("t1"));
            SelectExpressionItem item = new SelectExpressionItem(new Column(new Table("user"), "nick_name"));
            list.add(columns);
            list.add(item);
            all.addSelectItems(list);

            SubSelect subSelect = new SubSelect();
            subSelect.setSelectBody(plainSelect);
            subSelect.setAlias(new Alias("t1"));
            all.setFromItem(subSelect);

            Join join = new Join();
            join.setLeft(true);
            Table table = new Table("SYS_USER");
            table.setAlias(new Alias("user"));
            join.setRightItem(table);
            Column leftColumn = new Column(new Table("user"), "id");
            Column rightColumn = new Column(new Table("t1"), "create_by");
            join.setOnExpressions(Collections.singletonList(new EqualsTo(leftColumn, rightColumn)));
            all.addJoins(join);

            System.out.println(all);
        }
    }
}