import { VTL_STORAGE } from "@/storage/const.js";

const KEY = "user";

let userStorage = {
  set: function (user) {
    layui.sessionData(VTL_STORAGE, {
      key: KEY,
      value: user,
    });
  },
  del: function () {
    layui.sessionData(VTL_STORAGE, {
      key: KEY,
      remove: true,
    });
  },
  get: function () {
    return layui.sessionData(VTL_STORAGE)[KEY];
  },
  getToken: function () {
    return this.get()?.token;
  },
};

export { userStorage };
