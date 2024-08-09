package com.github.mengweijin.framework.mybatis.data.permission;

import com.baomidou.mybatisplus.extension.plugins.handler.DataPermissionHandler;
import com.github.mengweijin.framework.constant.Const;
import com.github.mengweijin.framework.mybatis.data.MapperUtils;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.expression.operators.relational.InExpression;
import net.sf.jsqlparser.schema.Column;
import org.dromara.hutool.core.collection.CollUtil;
import org.dromara.hutool.core.text.StrUtil;

import java.util.List;
import java.util.stream.Collectors;

/**
 * {@link DataScope} Can only be used in *Mapper.java.
 * @author mengweijin
 * @since 2022/11/20
 */
@Slf4j
public abstract class BaseDataPermissionHandler implements DataPermissionHandler {

    @Override
    public Expression getSqlSegment(Expression where, String mappedStatementId) {
        log.debug("BaseDataPermissionHandler Expression where: {}", where);
        log.debug("BaseDataPermissionHandler mappedStatementId: {}", mappedStatementId);

        return MapperUtils.processMethodExpression(
                mappedStatementId,
                DataScope.class,
                dataScope -> this.dataScopeFilter(dataScope, where));
    }

    /**
     * 1、根据 dataScope 获取当前的用户的角色、部门信息
     * 2、再根据当前用户的角色、部门信息查询其下所有人员信息。
     * 3、拼接 where 条件 “ in user.id in (上一步的人员结果集合) ”
     * 4、返回拼接后的 Expression。返回 null 则表示不使用当前拦截器。
     */
    private Expression dataScopeFilter(DataScope dataScope, Expression where) {
        // 如果是特权用户，不控制数据权限
        if (this.isAdminForLoginUserId()) {
            return where;
        }

        Expression expression = null;
        switch (dataScope.scope()) {
            case USER -> {
                String loginUserId = this.getLoginUserId();
                if (StrUtil.isNotBlank(loginUserId)) {
                    EqualsTo userEqualsTo = new EqualsTo();
                    userEqualsTo.setLeftExpression(buildColumn(dataScope));
                    userEqualsTo.setRightExpression(new StringValue(loginUserId));
                    expression = userEqualsTo;
                }
            }
            case DEPT -> {
                List<String> loginUserDeptIdList = this.getLoginUserDeptIdList();
                if (CollUtil.isNotEmpty(loginUserDeptIdList)) {
                    InExpression deptInExpression = new InExpression();
                    deptInExpression.setLeftExpression(buildColumn(dataScope));
                    List<Expression> deptExpressionList = loginUserDeptIdList.stream().map(StringValue::new).collect(Collectors.toList());
                    // deptInExpression.setRightItemsList(new ExpressionList(deptExpressionList));
                    expression = deptInExpression;
                }
            }
            case ROLE -> {
                List<String> loginUserRoleIdList = this.getLoginUserRoleIdList();
                if (CollUtil.isNotEmpty(loginUserRoleIdList)) {
                    InExpression roleInExpression = new InExpression();
                    roleInExpression.setLeftExpression(buildColumn(dataScope));
                    List<Expression> roleExpressionList = loginUserRoleIdList.stream().map(StringValue::new).collect(Collectors.toList());
                    // roleInExpression.setRightItemsList(new ExpressionList(roleExpressionList));
                    expression = roleInExpression;
                }
            }
            default -> {
            }
        }
        return where == null ? expression : new AndExpression(where, expression);
    }

    protected abstract String getLoginUserId();

    protected abstract boolean isAdminForLoginUserId();

    protected abstract List<String> getLoginUserDeptIdList();

    protected abstract List<String> getLoginUserRoleIdList();

    /**
     * 构建Column
     *
     * @param dataScope dataScope
     * @return 带表别名字段
     */
    protected static Column buildColumn(DataScope dataScope) {
        String tableColumnName = dataScope.tableColumnName();
        if(StrUtil.isBlank(tableColumnName)) {
            tableColumnName = dataScope.scope().getColumnName();
        }
        String tableAlias = dataScope.tableAlias();
        tableAlias = StrUtil.isBlank(tableAlias) ? Const.EMPTY : tableAlias + Const.DOT;
        return new Column(tableAlias + tableColumnName);
    }
}
