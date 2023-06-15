layui.define(['jquery', 'dict', 'form'], function(exports) {
	"use strict";

	var MOD_NAME = 'vtlForm';
	var $ = layui.jquery;
	var dict = layui.dict;
	var form = layui.form;


    var vtlForm = {
        initRadioByDict: function(elem, dictTypeCode) {
            let $elem = $(elem);
            let dictDataList = dict.dataList(dictTypeCode);
            if(!dictDataList) {
                return;
            }

            let $container = $elem.parent().empty();
            let $radioTpl = $('<input />', { type: 'radio', name: $elem.attr('name') });
            let checkedFlag = false;
            for (let i in dictDataList) {
                let $radio = $radioTpl.clone().attr('title', dictDataList[i].label).attr('value', dictDataList[i].dataCode);
                if(!checkedFlag && dictDataList[i].defaultSelected == 1) {
                    $radio.attr('checked', 'true');
                    checkedFlag = true;
                }
                $container.append($radio);
            }
            form.render('radio');
        },

    };

	exports(MOD_NAME, vtlForm);
})