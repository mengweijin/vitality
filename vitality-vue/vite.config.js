import { defineConfig, loadEnv } from 'vite';
import { resolve } from 'path';
import vue from '@vitejs/plugin-vue';
import svgLoader from 'vite-svg-loader';
import legacy from '@vitejs/plugin-legacy';

export default ({ mode }) => {
  const env = loadEnv(mode, process.cwd());
  // https://vite.dev/config/
  return defineConfig({
    resolve: {
      // 目录别名
      alias: {
        // 目录 → src 
        '@': resolve(__dirname, 'src'),
        '@assets': resolve(__dirname, 'src/assets'),
        '@components': resolve(__dirname, 'src/components'),
        '@utils': resolve(__dirname, 'src/utils'),
        '@views': resolve(__dirname, 'src/views'),
      }
    },
    server: {
      // 端口号
      port: env.VITE_PORT,
      host: '0.0.0.0',
      proxy: {
        [env.VITE_API_BASE]: {
          target: env.VITE_API_TARGET,
          changeOrigin: true,
          rewrite: (path) => path.replace(new  RegExp(`^\\${env.VITE_API_BASE}`), '')
        }
      }
    },
    plugins: [
      vue(),
      svgLoader(),
      legacy({
        targets: ['defaults', 'not IE 11'],
      }),
    ],
  });
}
