import { sessionStorage } from "@/storage/session.js";

let userStorage = {
  /** 自定义一个存储的 key 值 */
  name: "user",

  /** 使用展开运算符继承 storage */
  ...sessionStorage,

  /** 扩展方法 */
  getToken: function () {
    return this.get()?.token;
  },
};

export { userStorage };
