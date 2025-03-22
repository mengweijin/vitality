import { template } from "@/scripts/template.js";
import { userStorage } from "@/storage/userStorage.js";

let $ = layui.$;

/**
 * admin 组件
 */
let admin = {
  
  isLogin: function () {
    let user = userStorage.get();
    return user !== undefined && user !== null;
  },

  /**
   * 登录
   */
  login: function (data) {
    $.ajax({
      url: "/login",
      method: "post",
      data: data,
    }).then((r) => {
      userStorage.set(r.data);
      this.toAdmin();
    });
  },

  /**
   * 登出
   */
  logout: function () {
    $.ajax({
      url: "/logout",
      method: "post",
    }).then((r) => {
      // 前端登出
      userStorage.del();
      // 跳转页面
      this.toLogin();
    });
  },

  /**
   * 默认值为 undefined 时才触发 ES6 函数默认值的赋值，null 不会触发。
   */
  toAdmin: function () {
    template.load("#app", "src/views/admin.html");
  },

  toLogin: function () {
    template.load("#app", "src/views/login.html");
  },
};

export { admin };
