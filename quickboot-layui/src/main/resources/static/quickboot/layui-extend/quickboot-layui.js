/**
  扩展一个 quickboot 模块
  //提示：模块也可以依赖其它模块，如：layui.define('mod1', callback);
**/
layui.define(['jquery', 'layer'], function(exports){
  "use strict";
  var $ = layui.$, layer = layui.layer

  var MOD_NAME = 'quickboot'

  var quickboot = {
    /**
     * 获取 Layui checkbox 的提交值。
     * selector: 如：
     * 根据 lay-filter 和 checkbox 的 name 属性选择：'[lay-filter=operate-filter] input:checkbox[name=hobby]:checked'
     * 根据 formId 和 checkbox 的 name 属性选择：'form input:checkbox[name=hobby]:checked'
     * @return 示例：1,2,3,4
     */
    getCheckBoxValues: function(selector) {
        var value = [];
        $(selector).each(function(){
            value.push($(this).val());
        });
        return value.join(",");
    },

    /**
     * 禁用所有表单元素
     * @param selector jquery选择器，示例：#content
     */
    disableForm : function(selector){
      // *：选择元素下所有元素； unbind(): 解绑所有事件 这里其实并不需要unbind也可以同样的效果。
      $(selector + " *").attr("disabled", true).unbind();
    },

    /**
     * 注意：要使用empty()方法清理对象，否则可能导致内存泄漏
     * 并且所加载的页面中的js一定要写在$(function(){......});中，否则可能导致变量重复定义，控制台发生异常。
     * @param selector jquery选择器，示例：#content
     * @param url
     * @param params
     */
    loadPage : function(selector, url, params){
      let $selector = $(selector).empty();
      $.get(url, params, function (data) {
          $selector.html(data);
      });
    },

    /**
    * 从父页面关闭layer弹窗。（从父页面获取值 parent.$('#父页面元素id').val();）
    * 注意：window.parent和parent是有区别的
    */
    closeLayer : function(){
      //先得到当前iframe层的索引
      let index = parent.layer.getFrameIndex(window.name);
      //再执行关闭弹出层
      parent.layer.close(index);
      // 重载整个父页面
      // parent.location.reload();
    },

    /**
     * 使用：quickboot.refreshLayerParentTable('logPathDataTable');
     */
    refreshLayerParentTable(tableId){
        parent.layui.table.reload(tableId);
    },

    /**
     * layui表单必填项label添加红色星号
     */
    addLayuiFormRequiredStyle : function () {
        let $labels = $("[lay-verify*='required']").parent("div").siblings("label.layui-form-label");
        $labels.each(function(index, item){
            if(!$(item).hasClass("quickboot-layui-form-required")){
                $(item).addClass("quickboot-layui-form-required")
                       .append($("<strong style='color: red; font-size: 20px;'>*</strong>"));
            }
        });
    },

    /**
     * @params dataList List<Object>或者List<Map<String, Object>>数据
     * @params rootId 根值，树图或者级联数据最顶层的数据的id
     */
    buildTreeData: function (dataList, rootId){
      let itemArr = [];
      for(let i = 0; i < dataList.length; i++){
          let node = dataList[i];
          if(node.parentId === rootId){
              let newNode = {id : node.id, name : node.name, children : quickboot.buildTreeData(dataList, node.id)};
              itemArr.push(newNode);
          }
      }
      return itemArr;
    }
  };

  // 输出 quickboot 接口
  exports(MOD_NAME, quickboot);
});  