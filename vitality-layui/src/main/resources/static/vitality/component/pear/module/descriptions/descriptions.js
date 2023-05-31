layui.define(['table', 'jquery'], function(exports) {
	"use strict";

	var MOD_NAME = 'descriptions',
		$ = layui.jquery;




    var descriptions = {

        getData: function(options) {

        },


        render: function(options) {
            let defaults = {
                elem: '#descriptions',
                columnNum: 2,
                url: null,
                cols: [
                    { field: 'username', title: '账号' },
                    { field: 'password', title: '密码' },
                    { field: 'nickname', title: '昵称' },
                    { field: 'createTime', title: '创建时间', templet: function (d) {
                        return layui.util.toDateString(d.createTime, "yyyy-MM-dd HH:mm:ss");
                    }},
                ],
                data: { username: 'zhangsan', password: '123456', nickname: '张三', createTime: '2023-01-01' }
            }
            options = $.extend(defaults, options);
            let cols = options.cols;
            let data = options.data;

            var container = $("<div/>", { class: "layui-fluid" });
            let rowColNum = 12 / (options.columnNum * 2);

            let currentRow = null;
            for (let i in cols) {
                if(i % options.columnNum == 0) {
                    currentRow = $("<div/>", { class: "layui-row", style: "margin-top: 2px; padding: 0 15px;" });
                    $(container).append(currentRow);
                }
                for (let j in data) {
                    if(cols[i].field === j) {
                        $(currentRow).append($("<div/>", { style: "background-color: #d3e7f9; padding: 10px; line-height: 20px; text-align: right;", class: "layui-col-xs" + rowColNum }).text(cols[i].title + "："));
                        $(currentRow).append($("<div/>", { style: "background-color: #eeeeee; padding: 10px; line-height: 20px;", class: "layui-col-xs" + rowColNum }).text(data[j]));
                    }
                }

            }

            $(options.elem).html($(container).html());
        }
    }

	exports(MOD_NAME, descriptions);
})