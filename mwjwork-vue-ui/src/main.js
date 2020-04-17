// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import router from './router/index'
import 'es6-promise/auto'
import Vuex from 'vuex'
import store from './store/index'
import ElementUI from 'element-ui'
import dayjs from 'dayjs'
import axios from 'axios'
import {get, post, patch, put, deletes } from './utils/http'
import './assets/iconfont/iconfont.js'
import App from './App'

import 'element-ui/lib/theme-chalk/index.css'
import './assets/style/mengweijin.css'

import VideoPlayer from 'vue-video-player'
import 'video.js/dist/video-js.css'
Vue.use(VideoPlayer)

Vue.config.productionTip = false

Vue.use(ElementUI)
Vue.use(Vuex)

Vue.prototype.$dayjs = dayjs
Vue.prototype.$axios = axios
Vue.prototype.$get = get
Vue.prototype.$post = post
Vue.prototype.$patch = patch
Vue.prototype.$put = put
Vue.prototype.$delete = deletes

new Vue({
    el: '#app',
    router,
    store,
    render: h => h(App)
})