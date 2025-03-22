const VT_STORAGE = `${import.meta.env.VITE_STORAGE_PREFIX}-storage`;

const VT_STORAGE_TYPE_SESSION = "session";
const VT_STORAGE_TYPE_LOCAL = "local";

let storage = {
  /** 存储名称 */
  name: "storage",
  /**存储类型。可选值为[session, local]，分别对应 [sessionStorage, localStorage] */
  type: VT_STORAGE_TYPE_SESSION,

  get: function () {
    if(VT_STORAGE_TYPE_LOCAL === this.type) {
      return layui.data(VT_STORAGE)[this.name];
    } else {
      return layui.sessionData(VT_STORAGE)[this.name];
    }
  },

  set: function (data) {
    if(VT_STORAGE_TYPE_LOCAL === this.type) {
      layui.data(VT_STORAGE, { key: this.name, value: data });
    } else {
      layui.sessionData(VT_STORAGE, { key: this.name, value: data });
    }
  },

  del: function () {
    if(VT_STORAGE_TYPE_LOCAL === this.type) {
      layui.data(VT_STORAGE, { key: this.name, remove: true });
    } else {
      layui.sessionData(VT_STORAGE, { key: this.name, remove: true });
    }
  },

};

export { VT_STORAGE_TYPE_SESSION, VT_STORAGE_TYPE_LOCAL, storage };
