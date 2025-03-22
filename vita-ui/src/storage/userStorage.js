import { storage, VT_STORAGE_TYPE_SESSION } from "@/storage/storage.js";

let userStorage = {
  /** 自定义一个存储的 key 值 */
  name: "user",

  /** 指定该 storage 是 sessionStorage 还是 localStorage */
  type: VT_STORAGE_TYPE_SESSION,

  /** 使用展开运算符继承 storage */
  ...storage,

  /** 扩展方法 */
  getToken: function () {
    return this.get()?.token;
  },
};

export { userStorage };
