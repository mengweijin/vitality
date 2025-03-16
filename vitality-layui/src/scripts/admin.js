import * as layui from "@/scripts/layui.js";
import { template } from "@/scripts/template.js";
import { userStorage } from "@/storage/userStorage.js";

/**
 * 渲染
 * @param {Function} callback 回调函数
 */
let render = function (callback = function () {}) {
  return isLogin() ? toAdmin(callback) : toLogin(callback);
};

let isLogin = function () {
  let user = userStorage.get();
  return user !== undefined && user !== null;
};

/**
 * 登录
 */
let login = function (data) {
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
};

/**
 * 登出
 */
let logout = function () {
  let $ = layui.jquery;
  $.ajax({
    url: "/logout",
    method: "post",
    success: (r) => {
      userStorage.del();
      this.render();
    },
  });
};

/**
 * 默认值为 undefined 时才触发 ES6 函数默认值的赋值，null 不会触发。
 */
let toAdmin = function (callback = function () {}) {
  template.load("#app", "src/views/layout.html", undefined, callback);
};

let toLogin = function (callback = function () {}) {
  template.load("#app", "src/views/login.html", undefined, callback);
};

export { render, isLogin, login, logout, toAdmin, toLogin };
