layui.define(['jquery'], function(exports) {
	"use strict";

	var MOD_NAME = 'descriptions';
	var $ = layui.jquery;


    var descriptions = {
        config: {
            elem: null,
            fieldNum: 2,
            lineHeight: '20px',
            titleBackgroundColor: 'white' || '#d3e7f9' || '#a5dcbb' || '#c9edea',
            valueBackgroundColor: 'white' || '#eeeeee',
            url: null,
            cols: [],
            data: {}
        },
        init: function() {
            let that = this;
            if(this.config.fieldNum < 1) {
                this.config.fieldNum = 1;
            } else if(this.config.fieldNum > 3) {
                this.config.fieldNum = 3;
            }
            if(this.config.url) {
                $.ajax({
                    url: this.config.url,
                    async: false,
                    method: 'get',
                    success: function(result) {
                        that.config.data = result;
                    }
                });
            }
        },
        render: function(options) {
            this.config = $.extend(this.config, options);
            this.init();
            if(!this.config.data) {
                console.warn('No data was found in http response by url: ' + this.config.url);
                return;
            }
            let colSmSize = (12 - (this.config.fieldNum * 2)) / this.config.fieldNum;

            let $container = $("<div/>", { class: "layui-fluid", style: "padding: 15px;" });
            let $row = $("<div/>", { class: "layui-row", style: "margin-top: 2px; padding: 0 15px;" });
            let $titleColumn = $("<div/>", { style: "padding: 10px; text-align: right;" })
                .css("line-height", this.config.lineHeight)
                .css("font-weight", 'bold')
                .css("background-color", this.config.titleBackgroundColor)
                .addClass("layui-col-xs12 layui-col-sm2");
            let $valueColumn = $("<div/>", { style: "padding: 10px; text-align: left;" })
                .text("-")
                .css("line-height", this.config.lineHeight)
                .css("background-color", this.config.valueBackgroundColor)
                .addClass("layui-col-xs12 layui-col-sm" + colSmSize);

            let cols = this.config.cols;
            let data = this.config.data;

            let count = 0;
            let currentRow = null;
            for (let i in cols) {
                if(cols[i].hide) {
                    continue;
                }
                if(cols[i].newline || cols[i].longtext) {
                    count = 0;
                }
                if(count % this.config.fieldNum == 0) {
                    currentRow = $row.clone();
                    $container.append(currentRow);
                }

                $(currentRow).append($titleColumn.clone().text(cols[i].title + "："));
                let $currentValueColumn = $valueColumn.clone();
                if(cols[i].longtext) {
                    $currentValueColumn.removeClass("layui-col-sm" + colSmSize).addClass("layui-col-sm10");
                }
                $(currentRow).append($currentValueColumn);
                for (let j in data) {
                    let templet = cols[i].templet;
                    if(templet && typeof templet === 'function') {
                        let text = templet.call(this, data);
                        $currentValueColumn.text($.vtl.isBlank(text) ? '-' : text);
                        count++;
                        break;
                    } else if(cols[i].field === j) {
                        let text = data[j];
                        $currentValueColumn.text($.vtl.isBlank(text) ? '-' : text);
                        count++;
                        break;
                    }
                }
            }
            $(this.config.elem).append($container);
        }
    };

	exports(MOD_NAME, descriptions);
})