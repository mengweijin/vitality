package com.github.mengweijin.framework.mybatis.data.username;

import com.baomidou.mybatisplus.core.toolkit.PluginUtils;
import com.baomidou.mybatisplus.extension.parser.JsqlParserSupport;
import com.baomidou.mybatisplus.extension.plugins.inner.InnerInterceptor;
import com.github.mengweijin.framework.mybatis.data.MapperUtils;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.expression.Alias;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.AllTableColumns;
import net.sf.jsqlparser.statement.select.Join;
import net.sf.jsqlparser.statement.select.ParenthesedSelect;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SetOperationList;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

/**
 * Not useful.
 * {@link QueryUserNickName}
 * {@link JsqlParserSupport} <a href="https://github.com/JSQLParser/JSqlParser">JSqlParser</a>
 * @author mengweijin
 * @since 2023/4/16
 */
@Deprecated
@Slf4j
public class UserNickNameInnerInterceptor extends JsqlParserSupport implements InnerInterceptor {

    private static final String VTL_USER = "VTL_USER";
    private static final String CREATE_BY_LEFT_JOIN_VTL_USER_ALIAS = "CREATE_BY_VTL_USER_";
    private static final String UPDATE_BY_LEFT_JOIN_VTL_USER_ALIAS = "UPDATE_BY_VTL_USER_";

    private static final String NICK_NAME = "NICK_NAME";
    private static final String ID = "ID";
    private static final String CREATE_BY = "CREATE_BY";
    private static final String UPDATE_BY = "UPDATE_BY";

    private static final String CREATE_BY_COLUMN_QUERY_ALIAS = "CREATE_BY_NAME";
    private static final String UPDATE_BY_COLUMN_QUERY_ALIAS = "UPDATE_BY_NAME";

    private static final String ORIGINAL_QUERY_ALIAS = "ORIGINAL_QUERY_ALIAS_";


    @Override
    public void beforeQuery(Executor executor, MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, BoundSql boundSql) throws SQLException {
        PluginUtils.MPBoundSql mpBs = PluginUtils.mpBoundSql(boundSql);
        mpBs.sql(parserSingle(mpBs.sql(), ms.getId()));
    }

    @Override
    protected void processSelect(Select select, int index, String sql, Object obj) {
        if (select instanceof PlainSelect selectBody) {
            this.wrapLeftJoinSysUser(selectBody, (String) obj);
        } else if (select instanceof SetOperationList setOperationList) {
            List<Select> selectBodyList = setOperationList.getSelects();
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
                QueryUserNickName.class,
                queryUserNickName -> this.sysUserNameSqlWrapper(plainSelect));
    }

    private PlainSelect sysUserNameSqlWrapper(PlainSelect plainSelect) {
        PlainSelect resultSelect = new PlainSelect();
        AllTableColumns columns = new AllTableColumns(new Table(ORIGINAL_QUERY_ALIAS));
        resultSelect.addSelectItem(columns);

        // createByName
        Column createByNameColumn = new Column(new Table(CREATE_BY_LEFT_JOIN_VTL_USER_ALIAS), NICK_NAME);
        resultSelect.addSelectItems(createByNameColumn);

        // updateByName
        Column updateByNameColumn = new Column(new Table(UPDATE_BY_LEFT_JOIN_VTL_USER_ALIAS), NICK_NAME);
        resultSelect.addSelectItems(updateByNameColumn);

        // set sub-select
        ParenthesedSelect subSelect = new ParenthesedSelect().withSelect(plainSelect).withAlias(new Alias(ORIGINAL_QUERY_ALIAS));
        resultSelect.setFromItem(subSelect);

        // left Join ---- createBy
        Join createByJoin = new Join();
        createByJoin.setLeft(true);
        Table createByJoinTable = new Table(VTL_USER);
        createByJoinTable.setAlias(new Alias(CREATE_BY_LEFT_JOIN_VTL_USER_ALIAS));
        createByJoin.setRightItem(createByJoinTable);
        Column createByLeftColumn = new Column(new Table(CREATE_BY_LEFT_JOIN_VTL_USER_ALIAS), ID);
        Column createByRightColumn = new Column(new Table(ORIGINAL_QUERY_ALIAS), CREATE_BY);
        createByJoin.setOnExpressions(Collections.singletonList(new EqualsTo(createByLeftColumn, createByRightColumn)));
        resultSelect.addJoins(createByJoin);

        // left Join ---- updateBy
        Join updateByJoin = new Join();
        updateByJoin.setLeft(true);
        Table updateByJoinTable = new Table(VTL_USER);
        updateByJoinTable.setAlias(new Alias(UPDATE_BY_LEFT_JOIN_VTL_USER_ALIAS));
        updateByJoin.setRightItem(updateByJoinTable);
        Column updateByLeftColumn = new Column(new Table(UPDATE_BY_LEFT_JOIN_VTL_USER_ALIAS), ID);
        Column updateByRightColumn = new Column(new Table(ORIGINAL_QUERY_ALIAS), UPDATE_BY);
        updateByJoin.setOnExpressions(Collections.singletonList(new EqualsTo(updateByLeftColumn, updateByRightColumn)));
        resultSelect.addJoins(updateByJoin);

        return resultSelect;
    }
}
