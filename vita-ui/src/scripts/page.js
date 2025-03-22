import { template } from "@/scripts/template.js";

const ID = "tabs";

let $ = layui.$;
let tabs = layui.tabs;
let dropdown = layui.dropdown;

let page = {
  
  render: function () {
    tabs.render({ elem: `#${ID}`, closable: true });
    // 为标签头添加上下文菜单
    dropdown.render(this.contextmenuOpts());
  },

  // 新增标签
  addTab: function (opts) {
    let config = {
      id: `tab-${new Date()}`,
      // 此处加 n 仅为演示区分，实际应用不需要
      title: "New Tab",
      content: `<div id="${this.id}"></div>`,
      done: (params) => {
        // 给新标签头添加上下文菜单
        dropdown.render(
          $.extend({}, this.contextmenuOpts(), {
            // 当前标签头元素
            elem: params.thisHeaderItem,
          })
        );
        template.load(`#tabs #${this.id}`, "src/views/login.html");
      },
    };

    opts = $.extend(config, opts);
    // 添加标签到最后
    tabs.add(`${ID}`, opts);
  },

  contextmenuOpts: function () {
    let opts = {
      elem: `#${ID} .layui-tabs-header>li`,
      trigger: "contextmenu",
      data: [
        {
          title: "关闭当前",
          mode: "this",
        },
        {
          title: "关闭右侧",
          mode: "right",
        },
        {
          title: "关闭其他",
          mode: "other",
        },
        {
          title: "关闭所有",
          mode: "all",
        },
      ],
      click: function (data, othis, event) {
        // 获取活动标签索引
        let index = this.elem.index();
        if (data.mode === "this") {
          // 关闭当前标签
          tabs.close("tabs", index);
        } else {
          // 批量关闭标签
          tabs.closeMult("tabs", data.mode, index);
        }
      },
    };
    return opts;
  },
  
};

export { page };
