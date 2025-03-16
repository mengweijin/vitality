import { template } from "@/scripts/template.js";
import { userStorage } from "@/storage/userStorage.js";

const VTL_STORAGE = `${import.meta.env.VITE_STORAGE_PREFIX}-store`;
const VTL_STORAGE_USER = 'user';

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
    let user = userStorage.get();
    return user !== undefined && user !== null;
  },

  /**
   * 登录
   */
  login: function (data) {
    let $ = layui.jquery;
      $.ajax({
        url: "/login",
        method: "post",
        data: data,
        success: (r) => {
          userStorage.set(r.data);
          this.toAdmin();
        },
      });
  },

  /**
   * 登出
   */
  logout: function () {
    let $ = layui.jquery;
    $.ajax({
      url: "/logout",
      method: "post",
      success: (r) => {
        userStorage.del();
        this.toLogin();
      },
    });
  },

  /**
   * 默认值为 undefined 时才触发 ES6 函数默认值的赋值，null 不会触发。
   */
  toAdmin: function (callback = function () {}) {
    template.load("#app", "src/views/admin.html", undefined, callback);
  },

  toLogin: function (callback = function () {}) {
    template.load("#app", "src/views/login.html", undefined, callback);
  },
};

export { admin };
