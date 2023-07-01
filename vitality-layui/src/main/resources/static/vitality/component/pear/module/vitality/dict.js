layui.define(['jquery'], function(exports) {
	"use strict";

	var MOD_NAME = 'dict';
	var $ = layui.jquery;

	var TABLE_NAME = 'dict_data';

    var dict = {
        init: function() {
            let that = this;
            $.ajax({
                url: '/vtl-dict-data/list/all',
                async: false,
                method: 'get',
                success: function(result) {
                    let groupedData = $.vtl.groupBy(result, 'typeCode');
                    for(let i in groupedData) {
                        layui.sessionData(TABLE_NAME, { key: i, value: that.sort(groupedData[i]) });
                    }
                }
            });
        },
        /**
         * 数组排序。字典排序
         */
        sort: function(list) {
            list.sort(function(a, b) { return a.seq - b.seq; });
            return list;
        },
        getBadgeClass: function(index) {
            // 绿，蓝，橙，赤，青，深，浅
            let badgeClassArray = [
                'layui-badge layui-bg-green',
                'layui-badge layui-bg-blue',
                'layui-badge layui-bg-orange',
                'layui-badge',
                'layui-badge layui-bg-cyan',
                'layui-badge layui-bg-black',
                'layui-badge layui-bg-gray',
            ];
            if(index < badgeClassArray.length) {
                return badgeClassArray[index];
            } else {
                return badgeClassArray[badgeClassArray.length - 1];
            }
        },
        dataList: function(typeCode) {
            let dictTable = layui.sessionData(TABLE_NAME);
            if(!dictTable || $.isEmptyObject(dictTable)) {
                this.init();
                dictTable = layui.sessionData(TABLE_NAME);
            }
            if(dictTable[typeCode]) {
                return dictTable[typeCode];
            }
            console.error('No dict data was found by dict typeCode=' + typeCode);
            return null;
        },
        label: function(typeCode, dataCode, badgeStyle = true) {
            let list = this.dataList(typeCode);
            if(list) {
                for(let j = 0; j < list.length; j++) {
                    if(list[j].dataCode == dataCode) {
                        let item = list[j];
                        if(badgeStyle) {
                            return $('<span />', { class: this.getBadgeClass(j) }).text(list[j].label).get(0).outerHTML;
                        } else {
                            return list[j].label;
                        }
                    }
                }
            }
            console.warn('No dict data was found by [typeCode=' + typeCode + '] and [dataCode=' + dataCode + ']');
            return dataCode;
        },
        labelButton: function(typeCode, dataCode, badgeStyle = true) {
            let list = this.dataList(typeCode);
            let $btn = $('<button />').css('height', '18px').css('line-height', '18px').css('cursor', 'pointer');
            if(list) {
                for(let j = 0; j < list.length; j++) {
                    if(list[j].dataCode == dataCode) {
                        let item = list[j];
                        if(badgeStyle) {
                            $btn.css('border', '0').css('outline', 'none').css('background-color', 'transparent');
                            return $btn.append($('<span />', { class: this.getBadgeClass(j) }).css('outline', '1px solid #2f363c').text(list[j].label)).get(0).outerHTML;
                        } else {
                            return $btn.text(list[j].label).get(0).outerHTML;
                        }
                    }
                }
            }
            console.warn('No dict data was found by [typeCode=' + typeCode + '] and [dataCode=' + dataCode + ']');
            return dataCode;
        },
        clear: function() {
            layui.sessionData(TABLE_NAME, null);
        },
         refresh: function() {
             this.clear();
             this.init();
         }
    };

	exports(MOD_NAME, dict);
})