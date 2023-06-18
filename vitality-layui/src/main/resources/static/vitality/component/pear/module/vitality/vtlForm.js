layui.define(['jquery', 'dict', 'form', 'xmSelect', 'popover', 'tag'], function(exports) {
	"use strict";

	var MOD_NAME = 'vtlForm';
	var $ = layui.jquery;
	var dict = layui.dict;
	var form = layui.form;
	var xmSelect = layui.xmSelect;
	var popover = layui.popover;
	var tag = layui.tag;

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
                    $radio.attr('checked', true);
                    checkedFlag = true;
                }
                $container.append($radio);
            }
            form.render('radio');
        },

        initSelectByDict: function(elem, dictTypeCode, defaultSelected = false) {
            let dictDataList = dict.dataList(dictTypeCode);
            if(!dictDataList) {
                return;
            }

            let $select = $(elem).attr('lay-search', '');

            let $optionTpl = $('<option />', { value: '' });
            $select.append($optionTpl.clone());

            let selectedFlag = false;
            for (let i in dictDataList) {
                let $option = $optionTpl.clone().attr('value', dictDataList[i].dataCode).text(dictDataList[i].label);
                if(dictDataList[i].disabled == 1) {
                    $option.attr('disabled', true);
                }
                if(defaultSelected && !selectedFlag && dictDataList[i].defaultSelected == 1) {
                    $option.attr('selected', true);
                    selectedFlag = true;
                }
                $select.append($option);
            }
            form.render('select');
        },

        initXmSelect: function(elem, url, options = {}) {
            let $elem = $(elem);
            let name = $elem.attr('name');
            let value = $elem.attr('value') || '';
            let layFilter = $elem.attr('lay-filter');
            let layVerify = $elem.attr('lay-verify');

            let $container = $elem.parent().empty();

            let divId = 'xm-select-' + name;
            let $div = $('<div />', { id: divId });
            $container.append($div);

            let initValue = null;
            if(value instanceof Array) {
                initValue = value;
            } else {
                initValue = [ value ];
            }

            let defaults = {
                el: '#' + divId,
                name: name,                             // 表单提交时的属性名
                layVerify: layVerify,                   // 表单验证, 同layui的lay-verify
                initValue: initValue,                   // 初始化选中的数据, 需要在data中存在。数组格式：[ 1 ]
                filterable: true,                       // filterable
                paging: false,                          // 分页
                pageSize: 10,                           // 每页显示数量
                pageEmptyShow: false,                   // 无数据不展示分页
                list: [ 'ALL', 'CLEAR', 'REVERSE' ],    // 工具条。【全选, 清空, 反选】
                autoRow: true,                          // 自动换行
                size: 'medium',                         // 大小。large, medium, small, mini
                radio: false,                           // 是否开启单选模式
                clickClose: false,                      // 是否点击选项后自动关闭下拉框
                disabled: false,                        // 是否禁用该组件
                theme: {
                    color: '#1cbbb4',
                },
                model: {
                    label: {
                        type: 'block',
                        block: {
                            showCount: 0,               //最大显示数量, 0:不限制。如果配置为 1，则为超过1个，后面的隐藏。
                            showIcon: true,             //是否显示删除图标
                        }
                    }
                },
                tree: {
                    //是否显示树状结构
                    show: false,
                    //是否展示三角图标
                    showFolderIcon: true,
                    //是否显示虚线
                    showLine: false,
                    //间距
                    indent: 20,
                    //默认展开节点的数组, 为 true 时, 展开所有节点。为 [] 表示不展开任何节点。
                    expandedKeys: true,
                    //是否严格遵守父子模式
                    strict: false,
                    //是否开启极简模式
                    simple: false,
                    //点击节点是否展开
                    clickExpand: true,
                    //点击节点是否选中
                    clickCheck: true,
                },
                prop: {
                        name: 'name',               // 显示名称
                        value: 'id',                // 选中值, 当前多选唯一。原来的组件默认为：value
                        selected: 'selected',       // 是否选中
                        disabled: 'disabled',       // 是否选中
                        children: 'children',       // 分组children
                        optgroup: 'optgroup',       // 分组children
                },
                data: [
                    { name: '张三', id: 1, children: [{name: '香蕉3', id: 15}, {name: '葡萄3', id: 16},] },
                    { name: '李四', id: 2, selected: true },
                    { name: '王五', id: 3, disabled: true },
                ]
            };

            let config = $.extend(true, defaults, options);
            var inst = xmSelect.render(config);

            //获取当前多选选中的值
            //var selectArr = inst.getValue();
            //console.log(selectArr)
            // 动态禁用组件
            //inst.update({ disabled: true });
            // 设置选中项
            //inst.setValue([ 1 ])
            // 追加选中项
            //inst.append([ 2 ]);
            // 清空选中项
            //inst.setValue([  ])

        },

        initMenuTreeSelector: function(elem) {
            let $elem = $(elem);
            let name = $elem.attr('name');
            let value = $elem.attr('value');

            let layFilter = $elem.attr('lay-filter') || '0';
            let labelInputLayFilter = 'menu-tree-' + layFilter;
            let $container = $elem.parent();

            let $wrapDiv = $('<div />', { class: 'layui-input-wrap' });
            let $inputTpl = $('<input />', { name: name + 'Label', class: 'layui-input', placeholder: '点击选择(Click here)......' })
                .attr('lay-filter', labelInputLayFilter)
                .attr('lay-affix', 'close')
                .attr('lay-options', '{split: true}');
            if(!$.vtl.isBlank(value)) {
                $.sync('get', '/vtl-menu/titleHierarchy/' + value, function(result){
                    $inputTpl.val(result);
                });
            }

            $container.append($wrapDiv.append($inputTpl));

            form.render('input');

            form.on('input-affix(' + labelInputLayFilter + ')', function(data){
                $elem.val(0);
                $inputTpl.val('');
            });

            $inputTpl.click(function(){
                let url = $.vtl.getCtx() + '/views/system/menu/menuTreeTableSelector.html?checkedId=' + value;
                let success = function(layero, index, that) {
                    // 得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
                    var iframeWin = window[layero.find('iframe')[0]['name']];
                };
                let yes = function(index, layero, that) {
                    // var $body = layui.layer.getChildFrame('body', index);
                    // var $sourceImage = layui.layer.getChildFrame('#sourceImage', index);
                    var iframeWin = window[layero.find('iframe')[0]['name']];
                    let checkedArray = iframeWin.getChecked();
                    $elem.val(checkedArray[0]);
                    $inputTpl.val(checkedArray[1]);

                    layer.close(index);
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