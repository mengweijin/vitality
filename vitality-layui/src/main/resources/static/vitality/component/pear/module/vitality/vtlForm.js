layui.define(['jquery', 'dict', 'form', 'dtree'], function(exports) {
	"use strict";

	var MOD_NAME = 'vtlForm';
	var $ = layui.jquery;
	var dict = layui.dict;
	var form = layui.form;
	var dtree = layui.dtree;

    var vtlForm = {
        initRadioByDict: function(elem, dictTypeCode) {
            let $elem = $(elem);
            let dictDataList = dict.dataList(dictTypeCode);
            if(!dictDataList) {
                return;
            }
            let $container = $elem.parent().empty();
            let $radioTpl = $('<input />', { type: 'radio', name: $elem.attr('name') });
            $radioTpl.attr('lay-filter', $elem.attr('lay-filter'));
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

        initTreeSelect: function(elem, url, options = {}) {
            let $elem = $(elem);
            let name = $elem.attr('name');
            let $container = $elem.parent().empty().append($elem);
            let $tpl = $('<ul />', { id: name, class: 'dtree' });
            $container.append($tpl);

            dtree.renderSelect({
                elem: "#" + name,
                //width: "100%",
                initLevel: 1,
                //icon: "-1",  // 隐藏二级图标
                //skin: options.skin || null,  // 主题风格。可选：【 'layui', 'laySimple' 】
                menubar: options.menubar || false, //开启菜单栏，包含下拉搜索
                checkbar: options.checkbar || false, // 设置复选框, 多选
                checkbarType: options.checkbarType || "self", // 默认就是all，其他的值为： no-all  p-casc   self(复选框多选)  only(复选框单选)
                method: 'get',
                url: $.vtl.getAjaxBaseUrlFromSession() + url,
                dataStyle: "layuiStyle",  //使用layui风格的数据格式。{"code":0,"msg":"操作成功","data": [{"id":"001","title": "湖南省","parentId": "0","children":[]}]}
                response: { message: "msg", statusCode: 0 },  //使用layui风格，修改response中返回数据的定义
                //dataFormat: "list",  //配置data的风格为list
                selectInitVal: options.selectInitVal || null, // 你可以在这里指定默认值，比如："001001,001003"
                done: function(res, $ul, first) {

                }
            });
        }
    };

	exports(MOD_NAME, vtlForm);
})