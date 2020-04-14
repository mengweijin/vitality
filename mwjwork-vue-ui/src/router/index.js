import Vue from 'vue'
import Router from 'vue-router'
import Layout from '@/layout/index'
import MengweijinHeader from '@/layout/mengweijinHeader'
import Header from '@/layout/header'
import Footer from '@/layout/footer'

/**
 * 重写路由的push方法
 */
const routerPush = Router.prototype.push
Router.prototype.push = function push(location) {
    return routerPush.call(this, location).catch(error => error)
}

Vue.use(Router)

// 公共路由
export const Routers = [{
        path: '',
        component: Layout,
        children: [{
                path: '/index',
                components: {
                    mengweijinHeader: MengweijinHeader,
                    header: Header,
                    default: () =>
                        import ('@/views/index'),
                    footer: Footer
                }
            },
            {
                path: '/video-downloader/task',
                components: {
                    mengweijinHeader: MengweijinHeader,
                    header: Header,
                    default: () =>
                        import ('@/views/videoDownloader/index'),
                    footer: Footer
                }
            }
        ]
    },
    {
        path: '/video-downloader/play/:taskId',
        components: {
            mengweijinHeader: MengweijinHeader,
            default: () =>
                import ('@/views/videoDownloader/play'),
            footer: Footer
        }
    },
    {
        path: '/404',
        component: () =>
            import ('@/views/error/404'),
    }

]

export default new Router({
    // 去掉url中的#
    mode: 'history',
    scrollBehavior: () => ({ y: 0 }),
    routes: Routers
})