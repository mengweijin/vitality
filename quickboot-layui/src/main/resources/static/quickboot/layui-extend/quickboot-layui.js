/**
  扩展一个 quickboot 模块
  //提示：模块也可以依赖其它模块，如：layui.define('mod1', callback);
**/
layui.define(['jquery', 'layer'], function (exports) {
  "use strict";
  let $ = layui.$, layer = layui.layer

  let MOD_NAME = 'quickboot'

  let quickboot = {
    /**
     * 获取 Layui checkbox 的提交值。
     * selector: 如：
     * 根据 lay-filter 和 checkbox 的 name 属性选择：'[lay-filter=operate-filter] input:checkbox[name=hobby]:checked'
     * 根据 formId 和 checkbox 的 name 属性选择：'form input:checkbox[name=hobby]:checked'
     * @return 示例：1,2,3,4
     */
    checkBoxValues: function (selector) {
      let value = [];
      $(selector).each(function () {
        value.push($(this).val());
      });
      return value.join(",");
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
     * @param {Array} dataList 
     * @param {String} rootId For Example: '0'
     * @param {String} idFieldName optional, default 'id'
     * @param {String} parentFieldName optional, default 'parentId'
     * @param {String} childrenNodeName optional, default 'children'
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