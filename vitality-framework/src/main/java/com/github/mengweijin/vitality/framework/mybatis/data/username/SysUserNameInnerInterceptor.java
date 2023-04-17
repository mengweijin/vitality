package com.github.mengweijin.vitality.framework.mybatis.data.username;

import com.baomidou.mybatisplus.core.toolkit.PluginUtils;
import com.baomidou.mybatisplus.extension.parser.JsqlParserSupport;
import com.baomidou.mybatisplus.extension.plugins.inner.InnerInterceptor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectBody;
import net.sf.jsqlparser.statement.select.SetOperationList;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import java.sql.SQLException;
import java.util.List;

/**
 * {@link QueryUserName}
 * {@link JsqlParserSupport} <a href="https://github.com/JSQLParser/JSqlParser">JSqlParser</a>
 * @author mengweijin
 * @date 2023/4/16
 */
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
        log.debug("{} Expression where = {}", this.getClass().getSimpleName(), plainSelect.getWhere());
        log.debug("{} mappedStatementId = {}", this.getClass().getSimpleName(), mappedStatementId);
        Expression sqlSegment = plainSelect.getWhere();


        // TODO
        if (null != sqlSegment) {
            plainSelect.setWhere(sqlSegment);
        }
    }
}
