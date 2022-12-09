<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title></title>
		<link rel="stylesheet" href="../../component/pear/css/pear.css" />
	</head>
	<body class="pear-container">
		<div class="layui-card">
			<div class="layui-card-body">
				<form class="layui-form" action="">
					<div class="layui-form-item">
						<div class="layui-form-item layui-inline">
							<label class="layui-form-label">名称</label>
							<div class="layui-input-inline">
								<input type="text" name="name" placeholder="" class="layui-input">
							</div>
						</div>
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
				<table id="data-table-id" lay-filter="data-table-filter"></table>
			</div>
		</div>

        <script type="text/html" id="statusTpl">
            <input type="checkbox" name="status" value="{{d.id}}" lay-skin="switch" lay-text="正常|停用"
                   lay-filter="dept-switch-filter" {{ d.status == '0' ? 'checked' : '' }}>
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
			<button class="pear-btn pear-btn-primary pear-btn-sm" lay-event="generate"><i class="layui-icon layui-icon-play"></i>生成代码</button>
		</script>


		<script src="../../component/layui/layui.js"></script>
		<script src="../../component/pear/pear.js"></script>
		<script>
			layui.use(['table', 'form', 'jquery','layer', 'admin'], function() {
				let table = layui.table;
				let form = layui.form;
				let $ = layui.jquery;
				let layer = layui.layer;
				let admin = layui.admin;

				let cols = [[
                    { type: 'checkbox', hide: true },
                    { field: 'id', title: 'ID', width: 100, hide: true },
                    { field: 'name', title: '名称', minWidth: 200 },
                    { field: 'remark', title: '备注', width: 200 },
                    { field: 'status', title: '状态', width: 100, templet: '#statusTpl' },
                    { field: 'createBy', title: '创建者', width: 100, hide: true },
                    { field: 'createTime', title: '创建时间', width: 180, templet: function (d) {
                        return layui.util.toDateString(d.createTime,  "yyyy-MM-dd HH:mm:ss");
                    }},
                    { field: 'createBy', title: '更新者', width: 100, hide: true },
                    { field: 'updateTime', title: '更新时间', width: 180, templet: function (d) {
                        return layui.util.toDateString(d.createTime,  "yyyy-MM-dd HH:mm:ss");
                    }},
                    { field: 'operation', title: '操作', width: 260, fixed: 'right', templet: '#operationTpl' }
                ]]

                <#assign tableName='${name?lower_case}'>
				table.render({
					elem: '#data-table-id',
					url: '/${tableName?replace('_','-')}/page',
					page: true,
					cols: cols,
					skin: 'line',
					toolbar: '#table-toolbar',
					defaultToolbar: [{ title: '刷新', layEvent: 'refresh', icon: 'layui-icon-refresh'}, 'filter', 'print', 'exports']
				});

				table.on('toolbar(data-table-filter)', function(obj) {
					if (obj.event === 'add') {
						window.edit();
					} else if (obj.event === 'refresh') {
                        window.refresh();
                    }
				});

				table.on('tool(data-table-filter)', function(obj) {
				    let row = obj.data;
                    switch (obj.event) {
                        case 'detail':
                            window.detail(row.id, row.name);
                            break;
                        case 'edit':
                            window.edit(row.id);
                            break;
                        case 'delete':
                            window.delete(row.id);
                            break;
                        default:
                            break;
                    }
				});

				form.on('submit(data-table-query-filter)', function(data) {
					window.refresh(data.field);
					return false;
				});

				window.refresh = function(field = {}) {
					table.reloadData('data-table-id', { where: field }, false);
				}

				window.detail = function(id, title = '详情') {
					top.layui.admin.jump(new Date(), title, 'view/${tableName?replace('_','-')}/detail.html?id=' + id)
				}

			})
		</script>
	</body>
</html>
