/**
 * admin 组件
 */
let admin = {
  /**
   * 渲染
   * @param {Function} callback 回调函数
   */
  render: function (callback = function () {}) {
    return this.isLogin() ? this.toAdmin(callback) : this.toLogin(callback);
  },

  isLogin: function () {
    let table = layui.sessionData(import.meta.env.VITE_STORAGE_TABLE);
    let user = table?.user;
    return user !== undefined && user !== null;
  },

  toAdmin: function (callback = function () {}) {
    // 默认值为 undefined 时才触发默认值的赋值，null 不会触发。
    this.load("#app", "views/admin/admin.html", undefined, callback);
  },

  toLogin: function (callback = function () {}) {
    this.load("#app", "views/login/login.html", undefined, callback);
  },

  /**
   * 加载页面
   * @param {String} elem 元素选择器，新页面会被加载到选择的元素下面。推荐使用 #id 选择器。
   * @param {String} tpl 值可以为【模板的URL，CSS选择器，字符串】中的一种。示例如下：
   * - 模板的URL：'views/admin/admin.html'
   * - CSS选择器：'#id'
   * - 字符串：'我的名字是：<p>{{- name}}</p>'
   * @param {String | Object | Array} obj 模板渲染所需要的数据接口链接、对象、或数组的数据
   * @param {Function} callback 回调函数
   */
  load: function (elem, tpl, obj = {}, callback = function () {}) {
    let $ = layui.jquery;
    let laytpl = layui.laytpl;

    let loading = layer.load(2, { shade: [1, "#FFF"] });

    // 获取模板字符串
    let getTemplate = function () {
      let template;
      if (tpl.endsWith(".html")) {
        // 加载页面
        $.ajax({
          // 关闭 ajax 全局默认 loading
          loading: false,
          // 同步请求
          async: false,
          url: tpl,
          type: "get",
          dataType: "html",
          success: (res) => {
            template = res;
          },
        });
      } else if (tpl.startsWith("#")) {
        template = $(tpl).html();
      } else {
        template = tpl;
      }
      return template;
    };

    // 获取模板渲染需要的数据
    let getData = function () {
      let data;
      if (typeof obj === "string" || obj instanceof String) {
        $.ajax({
          loading: false,
          async: false,
          url: obj,
          method: "get",
          success: (res) => {
            data = res;
          },
        });
      } else {
        data = obj;
      }
      return data;
    };

    // 渲染并输出结果
    laytpl(getTemplate()).render(getData(), (str) => {
      $(elem).html(str);
      callback();

      layer.close(loading);
    });
  },
};

export { admin };
