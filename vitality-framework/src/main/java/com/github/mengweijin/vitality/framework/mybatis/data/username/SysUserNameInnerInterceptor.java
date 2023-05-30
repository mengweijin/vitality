package com.github.mengweijin.vitality.framework.mybatis.data.username;

import com.baomidou.mybatisplus.core.toolkit.PluginUtils;
import com.baomidou.mybatisplus.extension.parser.JsqlParserSupport;
import com.baomidou.mybatisplus.extension.plugins.inner.InnerInterceptor;
import com.github.mengweijin.vitality.framework.mybatis.data.MapperUtils;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.expression.Alias;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.AllTableColumns;
import net.sf.jsqlparser.statement.select.Join;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectBody;
import net.sf.jsqlparser.statement.select.SelectExpressionItem;
import net.sf.jsqlparser.statement.select.SelectItem;
import net.sf.jsqlparser.statement.select.SetOperationList;
import net.sf.jsqlparser.statement.select.SubSelect;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Not useful.
 * {@link QueryUserName}
 * {@link JsqlParserSupport} <a href="https://github.com/JSQLParser/JSqlParser">JSqlParser</a>
 * @author mengweijin
 * @date 2023/4/16
 */
@Deprecated
@Slf4j
public class SysUserNameInnerInterceptor extends JsqlParserSupport implements InnerInterceptor {

    @Override
    public void beforeQuery(Executor executor, MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, BoundSql boundSql) throws SQLException {
        PluginUtils.MPBoundSql mpBs = PluginUtils.mpBoundSql(boundSql);
        mpBs.sql(parserSingle(mpBs.sql(), ms.getId()));
    }

    @Override
    protected void processSelect(Select select, int index, String sql, Object obj) {
        SelectBody selectBody = select.getSelectBody();
        if (selectBody instanceof PlainSelect) {
            this.wrapLeftJoinSysUser((PlainSelect) selectBody, (String) obj);
        } else if (selectBody instanceof SetOperationList setOperationList) {
            List<SelectBody> selectBodyList = setOperationList.getSelects();
            selectBodyList.forEach(s -> this.wrapLeftJoinSysUser((PlainSelect) s, (String) obj));
        }
    }

    /**
     * 设置 where 条件
     *
     * @param plainSelect  查询对象
     * @param mappedStatementId 查询条件片段
     */
    protected void wrapLeftJoinSysUser(PlainSelect plainSelect, String mappedStatementId) {
        log.debug("{} Expression where: {}", this.getClass().getSimpleName(), plainSelect.getWhere());
        log.debug("{} mappedStatementId: {}", this.getClass().getSimpleName(), mappedStatementId);

        MapperUtils.processMethodExpression(
                mappedStatementId,
                QueryUserName.class,
                queryUserName -> this.sysUserNameSqlWrapper(plainSelect));
    }

    private PlainSelect sysUserNameSqlWrapper(PlainSelect plainSelect) {
        String createByLeftJoinTableAlias = "createBy_alias_";
        String updateByLeftJoinTableAlias = "updateBy_alias_";

        String originalQueryTableAlias = "original_query_alias_";

        PlainSelect all = new PlainSelect();
        ArrayList<SelectItem> list = new ArrayList<>();
        AllTableColumns columns = new AllTableColumns(new Table(originalQueryTableAlias));
        // createByName
        SelectExpressionItem createByNameItem = new SelectExpressionItem(new Column(new Table(createByLeftJoinTableAlias), "NICK_NAME"));
        createByNameItem.setAlias(new Alias("createByName"));
        // updateByName
        SelectExpressionItem updateByItem = new SelectExpressionItem(new Column(new Table(updateByLeftJoinTableAlias), "NICK_NAME"));
        updateByItem.setAlias(new Alias("updateByName"));
        list.add(columns);
        list.add(createByNameItem);
        list.add(updateByItem);
        all.addSelectItems(list);

        SubSelect subSelect = new SubSelect();
        subSelect.setSelectBody(plainSelect);
        subSelect.setAlias(new Alias(originalQueryTableAlias));
        all.setFromItem(subSelect);

        // left Join ---- createBy
        Join createByJoin = new Join();
        createByJoin.setLeft(true);
        Table createByJoinTable = new Table("VTL_USER");
        createByJoinTable.setAlias(new Alias(createByLeftJoinTableAlias));
        createByJoin.setRightItem(createByJoinTable);
        Column createByLeftColumn = new Column(new Table(createByLeftJoinTableAlias), "ID");
        Column createByRightColumn = new Column(new Table(originalQueryTableAlias), "CREATE_BY");
        createByJoin.setOnExpressions(Collections.singletonList(new EqualsTo(createByLeftColumn, createByRightColumn)));
        all.addJoins(createByJoin);

        // left Join ---- updateBy
        Join updateByJoin = new Join();
        updateByJoin.setLeft(true);
        Table updateByJoinTable = new Table("VTL_USER");
        updateByJoinTable.setAlias(new Alias(updateByLeftJoinTableAlias));
        updateByJoin.setRightItem(updateByJoinTable);
        Column updateByLeftColumn = new Column(new Table(updateByLeftJoinTableAlias), "ID");
        Column updateByRightColumn = new Column(new Table(originalQueryTableAlias), "UPDATE_BY");
        updateByJoin.setOnExpressions(Collections.singletonList(new EqualsTo(updateByLeftColumn, updateByRightColumn)));
        all.addJoins(updateByJoin);

        plainSelect = all;
        return plainSelect;
    }
}
