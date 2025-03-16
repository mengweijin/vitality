import "@/styles/index.css";

// 将多个模块的 import() 放入数组
const modules = [import("@/scripts/http.js"), import("@/scripts/admin.js")];

// 等待所有模块加载完成
Promise.all(modules)
  .then(([http, admin]) => {
    // 所有模块加载完成后执行此回调
    http.ajaxSetup();
    admin.render();
  })
  .catch((error) => {
    console.error(" 模块加载失败:", error);
  });
