package com.github.mengweijin.framework.mybatis.data.username;

import lombok.SneakyThrows;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Alias;
import net.sf.jsqlparser.expression.Expression;
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
import net.sf.jsqlparser.statement.select.ParenthesedSelect;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectItem;
import org.dromara.hutool.core.reflect.method.MethodUtil;
import org.dromara.hutool.core.text.StrUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.LinkedList;

/**
 * @author mengweijin
 * @since 2023/4/16
 */
public class UserNickNameInnerInterceptorTest {

    @Test
    public void plainSelectTest() throws JSQLParserException {
        String sql = """
            select * from SYS_DEPT 
            where TYPE = 'AA' AND CODE = '1001' 
            order by SEQ DESC
        """;

        Statement statement = CCJSqlParserUtil.parse(sql);

        if (statement instanceof Select select) {
            PlainSelect plainSelect = (PlainSelect) select;

            // select
            SelectItem<?> selectItem = plainSelect.getSelectItems().get(0);
            Expression selectExpression = selectItem.getExpression();
            Assertions.assertInstanceOf(AllColumns.class, selectExpression);
            Assertions.assertEquals("*", selectExpression.toString());

            // from table
            Table table = plainSelect.getFromItem(Table.class);
            Assertions.assertEquals("SYS_DEPT", table.getName());

            // where
            AndExpression whereExpression = (AndExpression) plainSelect.getWhere();
            EqualsTo equalsTo1 = whereExpression.getLeftExpression(EqualsTo.class);
            Column equalsTo1LeftExpression = equalsTo1.getLeftExpression(Column.class);
            StringValue equalsTo1RightExpression = equalsTo1.getRightExpression(StringValue.class);
            Assertions.assertEquals("TYPE", equalsTo1LeftExpression.getColumnName());
            Assertions.assertEquals("AA", equalsTo1RightExpression.getValue());

            EqualsTo equalsTo2 = whereExpression.getRightExpression(EqualsTo.class);
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
            select dict.*, user.nick_name 
            from SYS_DICT dict 
            left join SYS_USER user on user.id = dict.create_by
        """;

        Statement statement = CCJSqlParserUtil.parse(sql);

        if (statement instanceof Select select) {
            PlainSelect plainSelect = (PlainSelect) select;

            // select
            SelectItem<?> selectItem0 = plainSelect.getSelectItems().get(0);
            Expression selectExpression0 = selectItem0.getExpression();
            Assertions.assertInstanceOf(AllColumns.class, selectExpression0);
            Assertions.assertEquals("dict.*", selectExpression0.toString());

            SelectItem<?> selectItem1 = plainSelect.getSelectItems().get(1);
            Expression selectExpression1 = selectItem1.getExpression();
            Assertions.assertInstanceOf(Column.class, selectExpression1);
            Assertions.assertEquals("user.nick_name", selectExpression1.toString());

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
            PlainSelect plainSelect = (PlainSelect) select;

            ParenthesedSelect parenthesedSelect = plainSelect.getFromItem(ParenthesedSelect.class);
            Assertions.assertEquals(" AS t1", parenthesedSelect.getAlias().toString());
            Assertions.assertEquals("t1", parenthesedSelect.getAlias().getName());

            Select subSelect = parenthesedSelect.getSelect();
            PlainSelect subSelectPlainSelect = subSelect.getPlainSelect();
            Table subSelectFromTable = subSelectPlainSelect.getFromItem(Table.class);
            Assertions.assertEquals("SYS_DEPT", subSelectFromTable.toString());
            Assertions.assertEquals("SYS_DEPT", subSelectFromTable.getName());
            Assertions.assertNull(subSelectFromTable.getAlias());

            Join join = plainSelect.getJoins().get(0);
            Assertions.assertEquals("SYS_USER user", join.getRightItem().toString());
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
            PlainSelect plainSelect = (PlainSelect) select;

            PlainSelect resultSelect = new PlainSelect();

            AllTableColumns columns = new AllTableColumns(new Table("t1"));
            resultSelect.addSelectItem(columns);

            Column nickNameColumn = new Column(new Table("user"), "nick_name");
            resultSelect.addSelectItems(nickNameColumn);

            ParenthesedSelect subSelect = new ParenthesedSelect().withSelect(plainSelect).withAlias(new Alias("t1"));
            resultSelect.setFromItem(subSelect);

            Join join = new Join();
            join.setLeft(true);
            Table table = new Table("SYS_USER");
            table.setAlias(new Alias("user"));
            join.setRightItem(table);
            Column leftColumn = new Column(new Table("user"), "id");
            Column rightColumn = new Column(new Table("t1"), "create_by");
            join.setOnExpressions(Collections.singletonList(new EqualsTo(leftColumn, rightColumn)));
            resultSelect.addJoins(join);

            System.out.println(resultSelect);
        }
    }

    @Test
    @SneakyThrows
    public void sysUserNameSqlWrapper() {
        UserNickNameInnerInterceptor interceptor = new UserNickNameInnerInterceptor();
        String sql = """
            select ID, NAME, CODE, CREATE_BY, CREATE_TIME from SYS_DEPT 
            where TYPE = 'AA' AND CODE = '1001' 
            order by SEQ DESC
        """;
        Statement statement = CCJSqlParserUtil.parse(sql);
        PlainSelect plainSelect = (PlainSelect) statement;

        PlainSelect select = MethodUtil.invoke(interceptor, "sysUserNameSqlWrapper", plainSelect);

        String resultSql = """
            SELECT ORIGINAL_QUERY_ALIAS_.*, CREATE_BY_VTL_USER_.NICK_NAME, UPDATE_BY_VTL_USER_.NICK_NAME FROM 
                (SELECT ID, NAME, CODE, CREATE_BY, CREATE_TIME FROM SYS_DEPT WHERE TYPE = 'AA' AND CODE = '1001' ORDER BY SEQ DESC) AS ORIGINAL_QUERY_ALIAS_ 
            LEFT JOIN VTL_USER AS CREATE_BY_VTL_USER_ ON CREATE_BY_VTL_USER_.ID = ORIGINAL_QUERY_ALIAS_.CREATE_BY 
            LEFT JOIN VTL_USER AS UPDATE_BY_VTL_USER_ ON UPDATE_BY_VTL_USER_.ID = ORIGINAL_QUERY_ALIAS_.UPDATE_BY
        """;

        Assertions.assertEquals(StrUtil.cleanBlank(resultSql), StrUtil.cleanBlank(select.toString()));
    }
}