import { createRouter, createWebHistory } from 'vue-router'
import type { RouteRecordRaw } from 'vue-router'
import BasicLayout from '@/layouts/BasicLayout.vue'

const routes: RouteRecordRaw[] = [
    {
        path: '/',
        component: BasicLayout,
        children: [
            {
                path: '',
                name: 'Home',
                component: () => import('@/views/Home.vue'),
                meta: { title: '文档管理' }
            },
            {
                path: 'test',
                name: 'Test',
                component: () => import('@/views/Test.vue'),
                meta: { title: '测试页面' }
            }
        ]
    },
    {
        path: '/editor',
        name: 'Editor',
        component: () => import('@/views/Editor.vue'),
        meta: { title: '文档编辑' }
    }
]

const router = createRouter({
    history: createWebHistory(),
    routes
})

export default router 