let $ = layui.jquery;
let layer = layui.layer;
let laytpl = layui.laytpl;

/**
 * 模板组件
 */
let template = {
  /**
   * 获取模板
   * @param {String} tpl 值可以为【模板的URL，CSS选择器，字符串】中的一种。示例如下：
   * - 模板的URL：'views/admin/admin.html'
   * - CSS选择器：'#id'
   * - 字符串：'我的名字是：<p>{{- name}}</p>'
   * @returns 模板字符串
   */
  getTemplate: function (tpl) {
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
        success: (str) => {
          template = str;
        },
      });
    } else if (tpl.startsWith("#")) {
      template = $(tpl).html();
    } else {
      template = tpl;
    }
    return template;
  },

  /**
   * 获取模板渲染所需要的数据
   * @param {String | Object | Array} obj 模板渲染所需要的数据接口链接、对象、或数组的数据。
   * @returns
   */
  getData: function name(obj) {
    let data;
    if (typeof obj === "string" || obj instanceof String) {
      $.ajax({
        loading: false,
        async: false,
        url: obj,
        method: "get",
        success: (str) => {
          data = str;
        },
      });
    } else {
      data = obj;
    }
    return data;
  },

  /**
   * 加载页面
   * @param {String} elem 元素选择器，新页面会被加载到选择的元素下面。推荐使用 #id 选择器。
   * @param {String} tpl 值可以为【模板的URL，CSS选择器，字符串】中的一种。示例如下：
   * - 模板的URL：'views/admin/admin.html'
   * - CSS选择器：'#id'
   * - 字符串：'我的名字是：<p>{{- name}}</p>'
   * @param {String | Object | Array} obj 模板渲染所需要的数据接口链接、对象、或数组的数据
   */
  load: function (elem, tpl, obj = {}) {
    let loading = layer.load(2, { shade: [1, "#FFF"] });

    // 获取模板字符串
    let template = this.getTemplate(tpl);
    // 获取模板渲染需要的数据
    let data = this.getData(obj);
    // 渲染并输出结果
    laytpl(template).render(data, (str) => {
      // 先清空内容以及绑定的事件，避免残留元素导致事件叠加
      $(elem).empty().html(str);
      this.reLoadJS(elem);
      layer.close(loading);
    });
  },

  /**
   * 重新加载 html 中的 <script> 脚本，以解决动态加载页面时，<script> 脚本不执行的问题。
   * @param {String} elem 
   */
  reLoadJS: function (elem) {
    // 提取并执行所有script标签
    let targetElement = document.querySelector(elem);

    let scripts = targetElement.getElementsByTagName("script");
    Array.from(scripts).forEach((oldScript) => {
      let newScript = document.createElement("script");
      // 同步，保证加载顺序
      newScript.async = false;
      newScript.type = oldScript.type ? oldScript.type : "text/javascript";

      if (oldScript.src) {
        newScript.src = oldScript.src + "?t=" + Date.now(); // 防缓存
      } else {
        newScript.textContent = oldScript.innerHTML;
      }
      // 移除旧脚本
      oldScript.remove();
      // 追加新脚本
      targetElement.appendChild(newScript);
    });
  },
};

export { template };
