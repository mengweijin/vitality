/**
 * 模板组件
 */
let template = {
  // 模板 id 或 模板字符串
  tpl: null,
  // 要渲染的目标元素选择器字符串
  elem: null,
  // 数据 {Object}
  data: null,
  // 数据请求链接。当存在 url 时，优先使用接口返回的数据，而不是默认的 data 中的数据。
  url: null,
  // 回调函数
  callback: function() {},

  /**
   * 渲染
   * @param {Object} options 配置项。比如：{ tpl: "#tpl", elem: "#id", url: "/user/detail?id=1", data: {} }
   */
  render: function (options) {
    let $ = layui.jquery;
    let laytpl = layui.laytpl;

    // Object.assign(target, source1, source2); // 将多个源对象的属性复制到目标对象，同名属性会被后续源对象覆盖
    Object.assign(this, options); 

    let setContent = () => {
      let tplString = this.tpl.startsWith('#') ? $(this.tpl).html() : this.tpl;
      // 渲染并输出结果
      laytpl(tplString).render(this.data, (str) => {
        $(this.elem).html(str);
        this.callback();
      });
    };

    if (options.url) {
      $.ajax({
        url: options.url,
        method: "get",
        success: (res) => {
          this.data = res;
          setContent();
        },
      });
    } else if (this.data) {
      setContent();
    }
  },
};

export { template };
