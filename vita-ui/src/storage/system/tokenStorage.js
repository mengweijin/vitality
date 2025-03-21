const VT_STORAGE = import.meta.env.VITE_STORAGE;

let tokenStorage = {

  name: "token",
  
  get: function () {
    return layui.data(VT_STORAGE)[this.name];
  },

  set: function (data) {
    layui.data(VT_STORAGE, { key: this.name, value: data });
  },

  del: function () {
    layui.data(VT_STORAGE, { key: this.name, remove: true });
  },

};

export { tokenStorage };
