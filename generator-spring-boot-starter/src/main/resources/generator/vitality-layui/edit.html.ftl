<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="UTF-8">
		<title></title>
		<link rel="stylesheet" href="../../component/pear/css/pear.css" />
	</head>
	<body>
		<form class="layui-form" action="" lay-filter="form-filter">
			<div class="mainBox">
				<div class="main-container">
				    <div class="layui-form-item layui-hide">
                        <label class="layui-form-label required">ID</label>
                        <div class="layui-input-block">
                            <input type="text" name="id" class="layui-input" disabled>
                        </div>
                    </div>
<#list fields as field>
    <#if !field.entityIgnored>
                    <div class="layui-form-item">
                        <label class="layui-form-label required">${field.columnName}</label>
                        <div class="layui-input-block">
                            <input type="text" name="${field.propertyName}" required lay-verify="required" placeholder="" autocomplete="new-password" class="layui-input">
                        </div>
                    </div>
    </#if>
</#list>
					<div class="layui-form-item">
						<label class="layui-form-label required">输入框</label>
						<div class="layui-input-block">
							<input type="text" name="title" required lay-verify="required" placeholder="请输入标题" autocomplete="new-password" class="layui-input">
						</div>
					</div>
					<div class="layui-form-item">
						<label class="layui-form-label required">密码框</label>
						<div class="layui-input-inline">
							<input type="password" name="password" required lay-verify="required" placeholder="请输入密码" autocomplete="new-password" class="layui-input">
						</div>
						<div class="layui-form-mid layui-word-aux">辅助文字</div>
					</div>
					<div class="layui-form-item">
						<label class="layui-form-label required">选择框</label>
						<div class="layui-input-block">
							<select name="city" lay-verify="required">
								<option value=""></option>
								<option value="0">北京</option>
								<option value="1">上海</option>
								<option value="2">广州</option>
							</select>
						</div>
					</div>
					<div class="layui-form-item">
						<label class="layui-form-label">复选框</label>
						<div class="layui-input-block">
							<input type="checkbox" name="like[write]" title="写作">
							<input type="checkbox" name="like[read]" title="阅读" checked>
							<input type="checkbox" name="like[dai]" title="发呆">
						</div>
					</div>
					<div class="layui-form-item">
						<label class="layui-form-label">开关</label>
						<div class="layui-input-block">
							<input type="checkbox" name="switch" lay-skin="switch">
						</div>
					</div>
					<div class="layui-form-item">
						<label class="layui-form-label">单选框</label>
						<div class="layui-input-block">
							<input type="radio" name="sex" value="男" title="男">
							<input type="radio" name="sex" value="女" title="女" checked>
						</div>
					</div>
					<div class="layui-form-item layui-form-text">
						<label class="layui-form-label">文本域</label>
						<div class="layui-input-block">
							<textarea name="desc" placeholder="请输入内容" class="layui-textarea"></textarea>
						</div>
					</div>
				</div>
			</div>
			<div class="bottom">
				<div class="button-container">
					<button type="submit" class="pear-btn pear-btn-primary pear-btn-sm" lay-submit="" lay-filter="submit-filter">
						<i class="layui-icon layui-icon-ok"></i>提交
					</button>
					<button type="reset" class="pear-btn pear-btn-sm">
						<i class="layui-icon layui-icon-refresh"></i>重置
					</button>
				</div>
			</div>
		</form>
		<script src="../../component/layui/layui.js"></script>
		<script src="../../component/pear/pear.js"></script>
		<script>
			layui.use(['form', 'jquery', 'http'], function() {
				let form = layui.form;
				let $ = layui.jquery;
				let http = layui.http;
				let id = http.getQueryVariable('id');
				let readonly = http.getQueryVariable('readonly');

                <#assign tableName='${name?lower_case}'>
				form.on('submit(submit-filter)', function(data) {
				    if(id) {
				        $.put('/${tableName?replace('_','-')}', data.field, function(result) {
                            if (result.code == 200) {
                                parent.layer.close(parent.layer.getFrameIndex(window.name)); //关闭当前页
                                parent.layui.table.reload("data-table");
                            }
                        });
				    } else {
				        $.post('/${tableName?replace('_','-')}', data.field, function(result) {
                            if (result.code == 200) {
                                parent.layer.close(parent.layer.getFrameIndex(window.name)); //关闭当前页
                                parent.layui.table.reload("data-table");
                            }
                        });
				    }

					return false;
				});

				window.init = function(id) {
				    let $option = $("<option/>", { value: "4" });
				    $("select[name=city]").append($option.clone().text("西安"));

				    if(id) {
                		$.get('/${tableName?replace('_','-')}/' + id, function(result) {
                            form.val("form-filter", result);
                        });
                    }

                    if(readonly) {
                        $(".bottom").addClass("layui-hide");
                        $("form *").attr("disabled", true);
                    }
				}

				window.init(id);
			})
		</script>
	</body>
</html>
