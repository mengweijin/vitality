<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title></title>
		<link rel="stylesheet" href="../../../component/pear/css/pear.css" />
	</head>
	<body class="pear-container">
		<div class="layui-card">
			<div class="layui-card-body">
				<form class="layui-form" action="">
					<div class="layui-form-item">
<#list fields as field>
    <#if !field.entityIgnored && field.propertyType == 'String'>
                        <div class="layui-form-item layui-inline">
                            <label class="layui-form-label">${field.columnName}</label>
                            <div class="layui-input-inline">
                                <input type="text" name="${field.propertyName}" placeholder="" class="layui-input">
                            </div>
                        </div>
    </#if>
</#list>
						<div class="layui-form-item layui-inline">
							<button class="pear-btn pear-btn-md pear-btn-primary" lay-submit lay-filter="data-table-query-filter">
								<i class="layui-icon layui-icon-search"></i>查询
							</button>
							<button type="reset" class="pear-btn pear-btn-md">
								<i class="layui-icon layui-icon-refresh"></i>重置
							</button>
						</div>
					</div>
				</form>
			</div>
		</div>
		<div class="layui-card">
			<div class="layui-card-body">
				<table id="data-table" lay-filter="data-table-filter"></table>
			</div>
		</div>

        <script type="text/html" id="toolbarLeftTpl">
			<button class="pear-btn pear-btn-primary pear-btn-md" lay-event="add">
		        <i class="layui-icon layui-icon-add-1"></i>新增
		    </button>
		    <button class="pear-btn pear-btn-danger pear-btn-md layui-hide" lay-event="batchDelete">
		        <i class="layui-icon layui-icon-delete"></i>删除
		    </button>
		</script>
		<script type="text/html" id="disabledTpl">
			<input type="checkbox" name="disabled" value="{{d.id}}" lay-skin="switch" lay-text="正常|停用"
				   lay-filter="disabled-switch-filter" {{ d.disabled ? '' : 'checked' }}>
		</script>
        <script type="text/html" id="userSexTpl">
            {{#if (d.sex == "MALE") { }}
                <span>男</span>
            {{# } else if(d.sex == "FEMALE"){ }}
                <span>女</span>
            {{# } else { }}
                <span></span>
            {{# } }}
        </script>
		<script type="text/html" id="operationTpl">
			<button class="pear-btn pear-btn-xs pear-btn-primary" lay-event="detail" title="详情"><i class="layui-icon layui-icon-eye"></i></button>
			<button class="pear-btn pear-btn-xs pear-btn-primary" lay-event="edit" title="编辑"><i class="layui-icon layui-icon-edit"></i></button>
            <button class="pear-btn pear-btn-xs pear-btn-danger" lay-event="delete" title="删除"><i class="layui-icon layui-icon-delete"></i></button>
		</script>


		<script src="../../../component/layui/layui.js"></script>
		<script src="../../../component/pear/pear.js"></script>
		<script>
			layui.use(['table', 'form', 'jquery','layer', 'admin'], function() {
				let table = layui.table;
				let form = layui.form;
				let $ = layui.jquery;
				let layer = layui.layer;
				let admin = layui.admin;

				let cols = [[
                    { type: 'checkbox', hide: true },
<#list fields as field>
    <#if field.keyFlag>
                    { field: '${field.propertyName}', title: '${field.columnName}', width: 200, hide: true },
    <#else>
        <#if field.propertyType == 'LocalDateTime'>
                    { field: '${field.propertyName}', title: '${field.columnName}', width: 160, templet: function (d) {
                        return layui.util.toDateString(d.${field.propertyName},  "yyyy-MM-dd HH:mm:ss");
                    }},
        <#elseif field.propertyType == 'LocalDate'>
                    { field: '${field.propertyName}', title: '${field.columnName}', width: 130, templet: function (d) {
                        return layui.util.toDateString(d.${field.propertyName},  "yyyy-MM-dd");
                    }},
        <#elseif field.propertyType == 'LocalTime'>
                    { field: '${field.propertyName}', title: '${field.columnName}', width: 130, templet: function (d) {
                        return layui.util.toDateString(d.${field.propertyName},  "HH:mm:ss");
                    }},
        <#else>
                    { field: '${field.propertyName}', title: '${field.columnName}', width: 120 },
        </#if>
    </#if>
</#list>
                    { field: 'disabled', title: '状态', width: 100, templet: '#disabledTpl', event: 'disabled' },
                    { field: 'createByName', title: '创建者', width: 120, hide: true },
                    { field: 'createTime', title: '创建时间', width: 160, hide: true, templet: function (d) {
                        return layui.util.toDateString(d.createTime, "yyyy-MM-dd HH:mm:ss");
                    }},
                    { field: 'updateByName', title: '更新者', width: 120 },
                    { field: 'updateTime', title: '更新时间', width: 160, templet: function (d) {
                        return layui.util.toDateString(d.updateTime, "yyyy-MM-dd HH:mm:ss");
                    }},
                    { field: 'operation', title: '操作', width: 160, fixed: 'right', templet: '#operationTpl' }
                ]]

                <#assign tableName='${name?lower_case}'>

				table.render({
					elem: '#data-table',
					url: '/${tableName?replace('_','-')}/page',
					request: {
                        pageName: 'current',
                        limitName: 'size'
                    },
					parseData: function(res) {
                        return {
                            "code": 0,
                            "msg": 'SUCCESS',
                            "count": res.total,
                            "data": res.records
                        };
                    },
					page: true,
					cols: cols,
					skin: 'line',
					toolbar: '#toolbarLeftTpl',
					defaultToolbar: [{ title: '刷新', layEvent: 'refresh', icon: 'layui-icon-refresh'}, 'filter', 'print', 'exports']
				});

				table.on('toolbar(data-table-filter)', function(obj) {
					if (obj.event === 'add') {
						window.edit();
                    } else if (obj.event === 'batchDelete') {
                        window.batchDelete();
					} else if (obj.event === 'refresh') {
                        window.refresh(true);
                    }
				});

				table.on('tool(data-table-filter)', function(obj) {
				    let row = obj.data;
                    switch (obj.event) {
                        case 'detail':
                            window.detail(row.id);
                            break;
                        case 'edit':
                            window.edit(row.id);
                            break;
                        case 'delete':
                            window.delete(obj);
                            break;
                        case 'disabled':
                            window.setDisabledValue(obj);
                            break;
                        default:
                            break;
                    }
				});

				form.on('submit(data-table-query-filter)', function(data) {
					window.refresh(data.field);
					return false;
				});

				window.refresh = function(field = {}, deep = false) {
				    $.vtl.tableReloadData('data-table', field, deep);
				}

				window.edit = function(id) {
				    let url = 'edit.html' + ($.vtl.isBlank(id) ? '' : ('?id=' + id));
				    $.vtl.openLayer(url, { title: '编辑' });
                }

				window.delete = function(obj) {
                    layer.confirm('确定删除？', { icon: 3, title: '提示' }, function(index) {
                        layer.close(index);
                        $.delete('/${tableName?replace('_','-')}/' + obj.data.id, function(result) {
                            if (result.code == 200) {
                                obj.del();
                            }
                        });
                    });
                }

				window.detail = function(id, title = '详情') {
					top.layui.admin.jump('${tableName?replace('_','-')}-' + id, title, 'views/${tableName?replace('_','-')}/detail.html?&id=' + id)
				}

				window.setDisabledValue = function(obj) {
                    layer.confirm('确定切换【启用/停用】状态？', { icon: 3, title: '提示' }, function(index) {
                        layer.close(index);
                        $.post('/${tableName?replace('_','-')}/setDisabledValue/' + obj.data.id, { disabled: !obj.data.disabled }, function(result) {
                            if (result.code == 200) {
                               window.refresh(true);
                            }
                        });
                    });
                }

			})
		</script>
	</body>
</html>
