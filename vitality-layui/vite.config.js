import { defineConfig, loadEnv } from "vite";
import { resolve } from "path";
import legacy from "@vitejs/plugin-legacy";
import copyPlugin from 'rollup-plugin-copy';

// https://cn.vitejs.dev/config/
export default defineConfig(({ mode }) => {
  // 根据当前工作目录中的 `mode` 加载 .env 文件
  // 以 process.cwd() 作为环境变量文件的根目录。
  // 设置第三个参数为 '' 来加载所有环境变量，而不管是否有
  // `VITE_` 前缀。
  const env = loadEnv(mode, process.cwd(), "");
  return {
    // 项目根目录（index.html 文件所在的位置）。可以是一个绝对路径，或者一个相对于该配置文件本身的相对路径。
    root: process.cwd(),
    /**
     * 资源路径前缀：base 值为所有静态资源添加统一的前缀路径。例如，配置 base: '/my-app/' 后，所有资源路径会变为 /my-app/assets/xxx。这对部署到子目录或 CDN 的场景至关重要。
     * 与路由配置协同：若项目使用 Vue Router 的 history 模式，需同时配置路由的 base 选项，确保前端路由路径与静态资源路径一致，避免页面空白或资源加载失败。
     * 环境区分：可通过 .env 文件（如 .env.production ）动态设置 base，实现开发环境与生产环境路径的自动切换
     *
     * 注意事项
     * 斜杠结尾：base 值需以斜杠结尾（如 /subdir/），否则可能引发路径错误。
     * 与路由同步：若使用 Vue Router，需设置 createWebHistory('/subdir/')，使路由路径与资源路径匹配10。
     * 缓存问题：修改 base 后需清理浏览器缓存，避免旧路径资源被强缓存影响加载
     */
    base: env.VITE_PUBLIC_PATH,
    resolve: {
      // 目录别名
      alias: {
        // 目录 → src
        "@": resolve(__dirname, "src"),
        "@assets": resolve(__dirname, "src/assets"),
        "@scripts": resolve(__dirname, "src/scripts"),
        "@styles": resolve(__dirname, "src/styles"),
        "@views": resolve(__dirname, "src/views"),
      },
    },
    // 设为 false 可以避免 Vite 清屏而错过在终端中打印某些关键信息。
    clearScreen: false,
    build: {
      outDir: "dist",
    },
    server: {
      // 端口号
      port: 5173,
      host: "0.0.0.0",
      // 开发服务器启动时，自动在浏览器中打开应用程序。
      open: false,
      proxy: {
        [env.VITE_API_BASE]: {
          target: "http://localhost:8080",
          changeOrigin: true,
          rewrite: (path) =>
            path.replace(new RegExp(`^\\${env.VITE_API_BASE}`), ""),
        },
        // 代理 websockets 或 socket.io 写法：
        // ws://localhost:5173/socket.io -> ws://localhost:5174/socket.io
        // 在使用 `rewriteWsOrigin` 时要特别谨慎，因为这可能会让代理服务器暴露在 CSRF 攻击之下
        // '/websockets': {
        //   target: 'ws://localhost:5174',
        //   ws: true,
        //   rewriteWsOrigin: true,
        // },
      },
    },
    plugins: [
      legacy({
        targets: ["defaults", "not IE 11"],
      }),
      copyPlugin({
        targets: [
          // 格式：{ src: '源路径', dest: '目标路径' }
          { src: 'login.html', dest: 'dist' },
          // 复制 views 下的 文件到 dist/views 
          { src: 'views/**/*', dest: 'dist/views' },
        ],
        // 在打包的某个声明周期执行复制。 buildStart, buildEnd, generateBundle, writeBundle
        hook: 'writeBundle'
      }),
    ],
    build: {
      // https://cn.vitejs.dev/guide/build.html#browser-compatibility
      target: "es2015",
      // https://cn.vitejs.dev/config/build-options.html#build-assetsdir
      assetsDir: "assets",
      // https://cn.vitejs.dev/config/build-options.html#build-assetsinlinelimit
      // 小于此阈值的导入或引用资源将内联为 base64 编码，以避免额外的 http 请求。设置为 0 可以完全禁用此项。
      assetsInlineLimit: 0,
      sourcemap: false,
      // 消除打包大小超过500kb警告
      chunkSizeWarningLimit: 4000,
      rollupOptions: {
        input: {
          // 入口文件
          index: "index.html"
        },
        // 静态资源分类打包
        output: {
          chunkFileNames: "static/js/[name]-[hash].js",
          entryFileNames: "static/js/[name]-[hash].js",
          assetFileNames: "static/[ext]/[name]-[hash].[ext]"
        }
      }
    },
  };
});
