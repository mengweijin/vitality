# video-downloader-ui

> video-downloader-ui

## Build Setup

``` bash
# install dependencies
npm install

# serve with hot reload at localhost:8080
npm run dev

# build for production with minification
npm run build

# build for production and view the bundle analyzer report
npm run build --report

# run unit tests
npm run unit

# run e2e tests
npm run e2e

# run all tests
npm test
```

For a detailed explanation on how things work, check out the [guide](http://vuejs-templates.github.io/webpack/) and [docs for vue-loader](http://vuejs.github.io/vue-loader).


## 准备
1. 使用taobao npm 源
$ npm install -g cnpm --registry=https://registry.npm.taobao.org

2. 配置vue环境变量（添加到path）
D:\Program\nodejs\node_global

3. 全局安装vue-cli
``` bash
$ cnpm install -g @vue/cli
```

## 初始化工程
``` bash
1. 查看可以使用的vue模板列表
$ vue list

2. 选择webpack模板, 根据提示设置配置，回车
$ vue init webpack {project name}

3. 测试初始化工程, 浏览器访问
$ npm run dev

4. 安装npm-check, 用来检查npm依赖包是否有更新
$ cnpm install -g npm-check
```
5. 通过npm-check一次性升级所有全局包或项目依赖包的版本
https://blog.csdn.net/pcaxb/article/details/81773475
谨慎操作，可能导致包版本不兼容，启动失败

## 常见命令
```
1. 运行项目
$ npm run dev

2. 强制清理
$ npm cache clean --force

3. 升级npm的版本到最新版（到指定版本）
$ cnpm install -g npm
$ cnpm install -g npm@6.14.4

4. 查看当前使用组件的版本
$ cnpm list element-ui

5. 升级组件到最新版（指定版本）
$ cnpm update element-ui --save
$ cnpm update element-ui@2.13.0 --save

6. 卸载组件
$ cnpm uninstall element-ui

7. 列举包可使用的版本
$ cnpm view element-ui versions

8. 安装常用组件
$ cnpm install element-ui --save
$ cnpm install vue-router --save
# vuex 依赖 Promise, 需要es6-promise, 在使用 Vuex 之前的一个地方import 'es6-promise/auto'后，window.Promise 会自动可用
$ cnpm install es6-promise --save
$ cnpm install vuex --save
$ cnpm install dayjs --save
$ cnpm install axios --save
# 增加了一些安全性的查询字符串解析和序列化字符串的库
$ cnpm install qs --save

9. 在src目录下创建名字为router的目录，然后在router文件夹下创建index.js
```

