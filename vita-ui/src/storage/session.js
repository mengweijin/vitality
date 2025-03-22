const VT_STORAGE = import.meta.env.VITE_STORAGE;

let sessionStorage = {
  name: "session",
  get: function () {
    return layui.sessionData(VT_STORAGE)[this.name];
  },

  set: function (data) {
    layui.sessionData(VT_STORAGE, { key: this.name, value: data });
  },

  del: function () {
    layui.sessionData(VT_STORAGE, { key: this.name, remove: true });
  },

};

export { sessionStorage };
