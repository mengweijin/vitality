/**
  扩展一个 quickboot 模块
  //提示：模块也可以依赖其它模块，如：layui.define('mod1', callback);
**/
layui.define(['jquery', 'layer', 'table', 'element'], function (exports) {
  "use strict";
  let $ = layui.$, layer = layui.layer, table = layui.table, element = layui.element

  let MOD_NAME = 'quickboot'

  let quickboot = {

    /**
     * 获取 Layui checkbox 的提交值。
     * selector: 如：
     * 根据 lay-filter 和 checkbox 的 name 属性选择：'[lay-filter=operate-filter] input:checkbox[name=hobby]:checked'
     * 根据 formId 和 checkbox 的 name 属性选择：'form input:checkbox[name=hobby]:checked'
     * @return 示例：[1,2,3,4]
     */
    checkBoxValues: function (selector) {
      let value = [];
      $(selector).each(function () {
        value.push($(this).val());
      });
      // return value.join(",");
      return value;
    },

    /**
     * 禁用所有表单元素
     * @param selector jquery选择器，示例：#content
     */
    disableForm: function (selector) {
      // *：选择元素下所有元素； unbind(): 解绑所有事件 这里其实并不需要unbind也可以同样的效果。
      $(selector + " *").attr("disabled", true).unbind();
    },

    /**
     * 
     * @param {String} filter Mandatory. Layui lay-filter
     * @param {String} title Mandatory. 
     * @param {String} url Mandatory. 
     * @param {String} id Optional. lay-id
     */
    openTab: function (filter, title, url, id) {
      id = id || Date.now();

      // 防止重复打开
      let $titleLiElements = $("[lay-filter=" + filter + "] ul li");
      for (let i = 0; i < $titleLiElements.length; i++) {
        if ($titleLiElements.eq(i).attr('lay-id') == id) {
          element.tabChange(filter, id);
          return;
        }
      };

      element.tabAdd(filter, {
        title: title
        , content: '<iframe src="' + url + '" scrolling="auto" frameborder="no" width="100%" height="100%"></iframe>'
        , id: id
      });
      element.tabChange(filter, id);
      $("[lay-filter=" + filter + "] div.layui-tab-content div.layui-show").css("height", "100%");

      if ($titleLiElements.length > 10) {
        let nth2Layid = $("[lay-filter=" + filter + "] ul li:nth-child(2)").attr("lay-id");
        element.tabDelete(filter, nth2Layid);
      }
    },

    /**
     * 
     * @param {String} url Mandatory. For Example: /sys/user/1?id=1
     * @param {Array} area Optional. For Example: ['800px', '450px']
     * @param {Function} callback Optional. 弹层销毁时的回调函数
     */
    openPage: function (url, area, callback) {
      let defaultArea = ['800px', '450px'];
      // shift arguments if area argument was omitted
      if ($.isFunction(area)) {
        callback = area;
        area = defaultArea;
      } else {
        area = area || defaultArea;
      }
      layer.open({
        type: 2
        , title: "编辑（Edit）"
        , shadeClose: false
        , shade: [0.5, "#393D49"]
        , shadeClose: true
        , maxmin: true
        , area: area
        , resize: false
        , content: url
        , end: callback
      });
    },

    /**
    * 从父页面关闭layer弹窗。（从父页面获取值 parent.$('#父页面元素id').val();）
    * 注意：window.parent和parent是有区别的
    */
    closeLayer: function () {
      //先得到当前iframe层的索引
      let index = parent.layer.getFrameIndex(window.name);
      //再执行关闭弹出层
      parent.layer.close(index);
      // 重载整个父页面
      // parent.location.reload();
    },

    /**
     * 
     * @param {String} id tableId Mandatory. 
     * @param {Object} options Optional
     * @param {Boolean} deep Optional. 是否采用深度重载（即参数深度克隆，也就是重载时始终携带初始时及上一次重载时的参数），默认 false
     */
    reloadTableData: function (id, options, deep) {
      table.reloadData(id, { where: options || {}, page: { theme: '#1E9FFF', curr: 1 } }, deep || false)
    },

    /**
     * 使用：quickboot.reload('logPathDataTable');
     */
    reloadParentTable(tableId) {
      parent.layui.table.reload(tableId);
    },

    /**
     *  let dataList = [
     *      { "id": "1", "parentId": "0", "name": "xxx", "age": "22" },
     *      { "id": "2", "parentId": "1", "name": "yyy", "age": "15" },
     *      { "id": "3", "parentId": "1", "name": "zzz", "age": "12" },
     *      { "id": "4", "parentId": "0", "name": "zzz", "age": "23" },
     *      { "id": "5", "parentId": "4", "name": "zzz", "age": "34" }
     *  ];
     * 
     * let result = quickboot.groupBy(dataList, 'parentId');
     *
     * @param {Array} dataList 
     * @param {String} groupKey 
     */
    groupBy: function (dataList, groupKey) {
      return dataList.reduce(function (result, current) {
        result[current[groupKey]] = result[current[groupKey]] || [];
        result[current[groupKey]].push(current);
        return result;
      }, {})
    },

    /**
     *  let dataList = [
     *      { "id": "1", "parentId": "0", "name": "xxx", "age": "22" },
     *      { "id": "2", "parentId": "1", "name": "yyy", "age": "15" },
     *      { "id": "3", "parentId": "1", "name": "zzz", "age": "12" },
     *      { "id": "4", "parentId": "0", "name": "zzz", "age": "23" },
     *      { "id": "5", "parentId": "4", "name": "zzz", "age": "34" }
     *  ];
     * 
     *  let result = qb.tree(dataList, '0');
     * 
     * @param {Array} dataList Mandatory. 
     * @param {String} rootId Mandatory. For Example: '0'
     * @param {String} idFieldName Optional, default 'id'
     * @param {String} parentFieldName Optional, default 'parentId'
     * @param {String} childrenNodeName Optional, default 'children'
     * @returns 
     */
    tree: function (dataList, rootId, idFieldName, parentFieldName, childrenNodeName) {
      idFieldName = idFieldName || 'id';
      parentFieldName = parentFieldName || 'parentId';
      childrenNodeName = childrenNodeName || 'children';
      let groupedList = quickboot.groupBy(dataList, parentFieldName);
      for (let i in dataList) {
        let row = dataList[i];
        row[childrenNodeName] = groupedList[row[idFieldName]];
      }
      return groupedList[rootId];
    }
  };

  // 输出 quickboot 接口
  exports(MOD_NAME, quickboot);
});  