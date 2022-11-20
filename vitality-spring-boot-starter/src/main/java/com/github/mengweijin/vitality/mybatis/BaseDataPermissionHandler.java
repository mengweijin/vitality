package com.github.mengweijin.vitality.mybatis;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.handler.DataPermissionHandler;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.expression.operators.relational.InExpression;
import net.sf.jsqlparser.schema.Column;
import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author mengweijin
 * @date 2022/11/20
 */
@Slf4j
public abstract class BaseDataPermissionHandler implements DataPermissionHandler {

    @Override
    public Expression getSqlSegment(Expression where, String mappedStatementId) {
        try {
            Class<?> clazz = Class.forName(mappedStatementId.substring(0, mappedStatementId.lastIndexOf(".")));
            String methodName = mappedStatementId.substring(mappedStatementId.lastIndexOf(".") + 1);
            Method[] methods = clazz.getDeclaredMethods();
            for (Method method : methods) {
                DataScope dataScope = method.getAnnotation(DataScope.class);
                if (dataScope != null && method.getName().equals(methodName)) {
                    return dataScopeFilter(dataScope, where);
                }
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return where;
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
            case DEPT:
                List<String> loginUserDeptIdList = this.getLoginUserDeptIdList();
                if(CollUtil.isNotEmpty(loginUserDeptIdList)) {
                    InExpression deptInExpression = new InExpression();
                    deptInExpression.setLeftExpression(buildColumn(dataScope, dataScope.scope()));
                    List<Expression> deptExpressionList = loginUserDeptIdList.stream().map(StringValue::new).collect(Collectors.toList());
                    deptInExpression.setRightItemsList(new ExpressionList(deptExpressionList));
                    expression = deptInExpression;
                }
                break;
            case ROLE:
                List<String> loginUserRoleIdList = this.getLoginUserRoleIdList();
                if(CollUtil.isNotEmpty(loginUserRoleIdList)) {
                    InExpression roleInExpression = new InExpression();
                    roleInExpression.setLeftExpression(buildColumn(dataScope, dataScope.scope()));
                    List<Expression> roleExpressionList = loginUserRoleIdList.stream().map(StringValue::new).collect(Collectors.toList());
                    roleInExpression.setRightItemsList(new ExpressionList(roleExpressionList));
                    expression = roleInExpression;
                }
                break;
            case USER:
                String loginUserId = this.getLoginUserId();
                if(StrUtil.isNotBlank(loginUserId)) {
                    EqualsTo userEqualsTo = new EqualsTo();
                    userEqualsTo.setLeftExpression(buildColumn(dataScope, dataScope.scope()));
                    userEqualsTo.setRightExpression(new StringValue(loginUserId));
                    expression = userEqualsTo;
                }
                break;
            case ALL:
            default:
                break;
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
     * @param dataScope
     * @param scope
     * @return 带表别名字段
     */
    protected static Column buildColumn(DataScope dataScope, DataScope.Scope scope) {
        String tableColumn = dataScope.tableColumn();
        if(StrUtil.isBlank(tableColumn) && StrUtil.isNotBlank(scope.getDefaultColumn())) {
            tableColumn = scope.getDefaultColumn();
        }
        Assert.notBlank(tableColumn);

        String tableAlias = dataScope.tableAlias();
        tableAlias = StrUtil.isBlank(tableAlias) ? tableAlias : tableAlias + ".";

        return new Column(tableAlias + tableColumn);
    }
}
