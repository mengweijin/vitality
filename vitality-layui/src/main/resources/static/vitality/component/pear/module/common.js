layui.define(['jquery', 'element','table'], function(exports) {
	"use strict";

	/**
	 * 常用封装类
	 * */
	var MOD_NAME = 'common',
		$ = layui.jquery,
		table = layui.table,
		element = layui.element;

	var common = new function() {
		
		/**
		 * 获取当前表格选中字段
		 * @param obj 表格回调参数
		 * @param field 要获取的字段
		 * */
		this.checkField = function(obj, field) {
			let data = table.checkStatus(obj.config.id).data;
			if (data.length === 0) {
				return "";
			}
			let ids = "";
			for (let i = 0; i < data.length; i++) {
				ids += data[i][field] + ",";
			}
			ids = ids.substr(0, ids.length - 1);
			return ids;
		}
		
		/**
		 * 当前是否为与移动端
		 * */
		this.isModile = function(){
			if ($(window).width() <= 768) {
				return true;
			}
			return false;
		}
		
		
		/**
		 * 提交 json 数据
		 * @param data 提交数据
		 * @param href 提交接口
		 * @param table 刷新父级表
		 * 
		 * */
		this.submit = function(data,href,table,callback){
			$.ajax({
			    url:href,
			    data:JSON.stringify(data),
			    dataType:'json',
			    contentType:'application/json',
			    type:'post',
			    success:callback !=null?callback(result):function(result){
			        if(result.success){
			            layer.msg(result.msg,{icon:1,time:1000},function(){
			                parent.layer.close(parent.layer.getFrameIndex(window.name));//关闭当前页
			                parent.layui.table.reload(table);
			            });
			        }else{
			            layer.msg(result.msg,{icon:2,time:1000});
			        }
			    }
			})
		}

		this.copyToClip = function(text) {
		    let el = document.createElement('textarea')
            el.value = text
            document.body.appendChild(el)
            // 选中input元素的文本
            el.select()
            // 复制内容到剪贴板
            document.execCommand('copy')
            document.body.removeChild(el)
            layer.msg("Copy SUCCESS!")
		}

		/**
         * 获取 Layui checkbox 的提交值。
         * selector: 如：
         * 根据 lay-filter 和 checkbox 的 name 属性选择：'[lay-filter=operate-filter] input:checkbox[name=hobby]:checked'
         * 根据 formId 和 checkbox 的 name 属性选择：'form input:checkbox[name=hobby]:checked'
         * @return 示例：[1,2,3,4]
         */
        this.checkedCheckBoxValue = function (selector) {
            let value = [];
            $(selector).each(function () {
                value.push($(this).val());
            });
            // return value.join(",");
            return value;
        }
	}
	exports(MOD_NAME, common);
});