import * as layui from "@/scripts/layui.js";
import { userStorage } from "@/storage/userStorage.js";

let $ = layui.$;
let layer = layui.layer;

/**
 * 设置 jquery ajax 全局配置
 */
let ajaxSetup = function () {
  $.ajaxSetup({
    loading: true,
    layerIndex: -1,
    cache: false,
    async: true,
    // default is: application/x-www-form-urlencoded
    // contentType 为 'application/json;charset=UTF-8' 时, 每一个请求携带的请求体就要使用 JSON.stringify(data) 处理。
    contentType: "application/json;charset=UTF-8",
    // 配合 contentType，关闭 data 参数自动转换为查询字符串格式（如 key1=value1&key2=value2）
    processData: false,
    beforeSend: function (xhr) {
      if (this.loading) {
        this.layerIndex = layer.load(2, { shade: [0.5, "#393D49"] });
      }

      if (this.dataType !== "html") {
        this.url = import.meta.env.VITE_API_BASE + this.url;
      }

      if (this.data && this.contentType.indexOf("application/json") !== -1) {
        this.data = JSON.stringify(this.data);
      }

      xhr.setRequestHeader('Authorization', 'Bearer ' + userStorage.getToken());
    },
    error: function (xhr, textStatus, errorThrown) {
      let message = "发生异常！";
      if (xhr.responseJSON && xhr.responseJSON.message) {
        message = xhr.responseJSON.message;
      }
      switch (xhr.status) {
        case 400:
          message = xhr?.responseJSON?.msg;
          layer.msg(message, {
            icon: 5,
            time: 0,
            closeBtn: 1,
            title: xhr.status,
          });
          break;
        case 401:
          message = "未登录或者会话已过期！";
          layer.alert(
            message,
            { icon: 0, closeBtn: 0, title: "信息" },
            function (index) {
              layer.close(index);
              // 刷新页面，触发登录检查，从而跳转登录页
              top.location.reload();
            }
          );

          break;
        case 403:
          message = message + "无权限！";
          layer.open({
            icon: 4,
            title: xhr.status,
            content: message,
            closeBtn: 0,
            yes: function (index, layero, that) {
              layer.close(index);
              // top.window.layui.admin.closeCurrentTab();
            },
          });
          break;
        case 404:
          layer.msg(message + "找不到资源！", {
            icon: 2,
            title: xhr.status,
          });
          break;
        case 408:
          layer.msg("请求超时！", {
            icon: 2,
            title: xhr.status,
          });
          break;
        case 500:
          layer.msg(message + "服务器异常！", {
            icon: 2,
            time: 0,
            closeBtn: 1,
            title: xhr.status,
          });
          break;
        default:
          message = message + "发生异常，请联系系统管理员！";
          layer.msg(message, {
            icon: 2,
            time: 0,
            closeBtn: 1,
            title: xhr.status,
          });
          break;
      }
    },
    // 请求完成时运行的函数（在请求成功或失败之后均调用，即在 success 和 error 函数之后）。
    complete: function (xhr, textStatus) {
      layer.close(this.layerIndex);
      if (this.url.endsWith("/login")) {
        return;
      } else if (this.type.toUpperCase() != "GET" && xhr.status == 200) {
        layer.msg("操作成功！", {
          icon: 1,
          time: 800,
        });
      }
    },
  });
};

export { ajaxSetup };
