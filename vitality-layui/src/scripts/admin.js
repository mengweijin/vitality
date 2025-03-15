import { template } from "@/scripts/template.js";

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
    let table = layui.sessionData(VTL_STORAGE);
    let user = table?.user;
    return user !== undefined && user !== null;
  },

  getToken: function(){
    let table = layui.sessionData(VTL_STORAGE);
    let token = table?.user?.token;
    return token;
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
          layui.sessionData(VTL_STORAGE, {
            key: VTL_STORAGE_USER,
            value: r.data,
          });
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
        layui.sessionData(VTL_STORAGE, {
          key: VTL_STORAGE_USER,
          remove: true,
        });
        this.toLogin();
      },
    });
  },

  toAdmin: function (callback = function () {}) {
    // 默认值为 undefined 时才触发默认值的赋值，null 不会触发。
    template.load("#app", "views/admin/admin.html", undefined, callback);
  },

  toLogin: function (callback = function () {}) {
    template.load("#app", "views/login/login.html", undefined, callback);
  },
};

export { admin };
