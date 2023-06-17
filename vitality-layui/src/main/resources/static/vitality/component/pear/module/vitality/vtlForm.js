layui.define(['jquery', 'dict', 'form', 'zTree', 'popover'], function(exports) {
	"use strict";

	var MOD_NAME = 'vtlForm';
	var $ = layui.jquery;
	var dict = layui.dict;
	var form = layui.form;
	var zTree = layui.zTree;
	var popover = layui.popover;

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

        initMenuTreeSelector: function(elem, options = {}) {
            let $elem = $(elem);
            let name = $elem.attr('name');
            let layFilter = $elem.attr('lay-filter') || '0';
            let labelInputLayFilter = 'menu-tree-' + layFilter;
            let $container = $elem.parent();

            let $inputTpl = $('<input />', { name: name + 'Label', readonly: '', class: 'layui-input', placeholder: '点击选择(Click here)......' }).attr('lay-filter', labelInputLayFilter);
            $container.append($inputTpl);

            form.render('input');

            $inputTpl.click(function(){
                let url = $.vtl.getCtx() + '/views/system/menu/menuTreeTableSelector.html';
                let success = function(layero, index, that) {
                    // 得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
                    var iframeWin = window[layero.find('iframe')[0]['name']];
                    //iframeWin.init($('#userAvatar').attr('src'));
                };
                let yes = function(index, layero, that) {
                    // var $body = layui.layer.getChildFrame('body', index);
                    // var $sourceImage = layui.layer.getChildFrame('#sourceImage', index);
                    var iframeWin = window[layero.find('iframe')[0]['name']];
                    //let img = iframeWin.save();
                    //$('#userAvatar').attr('src', img);
                };
                $.vtl.openLayer(url, {
                    title: '选择数据',
                    area: ['90%', '90%'],
                    btn: ['确定', '取消'],
                    success: success,
                    yes: yes
                });
            });


        },

        /**
         * 表单绑定气泡组件
         * vtlForm.initPopover('form input:hidden[name=parentId]', '/vitality/views/system/config/configList.html');
         */
        initPopover: function(elem, url, options = {}) {
            let $elem = $(elem);
            let name = $elem.attr('name');
            let layFilter = $elem.attr('lay-filter') || '';
            let labelInputLayFilter = 'popover-' + layFilter;
            let $container = $elem.parent();

            let $inputTpl = $('<input />', { name: name + 'Label', readonly: '', class: 'layui-input', placeholder: '点击选择(Click here)......' }).attr('lay-filter', labelInputLayFilter);
            $container.append($inputTpl);

            form.render('input');

            let popoverWidth = $inputTpl.width();
            let popoverHeight = $(window).height() - $inputTpl.offset().top - $inputTpl.height() - 150;

            popover.create('form input:text[lay-filter=' + labelInputLayFilter + ']', {
                title: '数据选择',
                trigger: 'click', //values:  click,hover,manual(handle events by your self),sticky(always show after popover is created);
                animation: 'pop', //pop with animation,values: pop,fade (only take effect in the browser which support css3 transition)
                closeable: true, //display close button or not
                placement: 'bottom',//values: auto,top,right,bottom,left,top-right,top-left,bottom-right,bottom-left,auto-top,auto-right,auto-bottom,auto-left,horizontal,vertical
                delay: {
                    //show and hide delay time of the popover, works only when trigger is 'hover',the value can be number or object
                    show: null,
                    hide: 100
                },
                async: {
                     type:'GET', // ajax request method type, default is GET
                },
                direction: 'ltr', // direction of the popover content default is ltr ,values:'rtl';
                padding: false, //content padding
                opacity: 0.98,
                type: 'iframe', //content type, values:'html','iframe','async'
                url: url, //if type equals 'html', value should be jQuery selecor.  if type equels 'async' the plugin will load content by url.
                width: popoverWidth, //auto, or can be set with number
                height: 'auto', //auto, can be set with number
                onShow: function($element) {}, // callback after show
                onHide: function($element) {}, // callback after hide
            });

        }
    };

	exports(MOD_NAME, vtlForm);
})