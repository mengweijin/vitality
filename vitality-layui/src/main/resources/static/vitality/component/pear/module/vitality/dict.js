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
        sort: function(list) {
            list.sort(function(a, b) { return a.seq - b.seq; });
            return list;
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
            return null;
        },
        dataItem: function(typeCode, dataCode) {
            let list = this.dataList(typeCode);
            if(list) {
                for(let j in list) {
                    if(list[j].dataCode == dataCode) {
                        return list[j];
                    }
                }
            }
            return null;
        },
        label: function(typeCode, dataCode) {
            let item = this.dataItem(typeCode, dataCode);
            if(item && item.label) {
                return item.label;
            }
            console.warn('No dict data was found by [typeCode=' + typeCode + '] and [dataCode=' + dataCode + ']');
            return '';
        },
        clear: function() {
            layui.sessionData(TABLE_NAME, null);
        }
    };

	exports(MOD_NAME, dict);
})