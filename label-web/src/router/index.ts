import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router'
import Layout from '@/layout/index.vue'

const routes: RouteRecordRaw[] = [
  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    children: [
      {
        path: 'dashboard',
        name: 'Dashboard',
        component: () => import('@/views/dashboard/index.vue'),
        meta: { title: '首页', icon: 'HomeFilled' }
      }
    ]
  },
  {
    path: '/datasource',
    component: Layout,
    redirect: '/datasource/list',
    meta: { title: '数据源管理', icon: 'Connection' },
    children: [
      {
        path: 'list',
        name: 'DatasourceList',
        component: () => import('@/views/datasource/list.vue'),
        meta: { title: '数据源列表', icon: 'List' }
      }
    ]
  },
  {
    path: '/label',
    component: Layout,
    redirect: '/label/category',
    meta: { title: '标签管理', icon: 'PriceTag' },
    children: [
      {
        path: 'category',
        name: 'LabelCategory',
        component: () => import('@/views/label/category.vue'),
        meta: { title: '标签分类', icon: 'FolderOpened' }
      },
      {
        path: 'list',
        name: 'LabelList',
        component: () => import('@/views/label/list.vue'),
        meta: { title: '标签列表', icon: 'List' }
      },
      {
        path: 'rule',
        name: 'LabelRule',
        component: () => import('@/views/label/rule.vue'),
        meta: { title: '标签规则', icon: 'Edit' }
      }
    ]
  },
  {
    path: '/compute',
    component: Layout,
    redirect: '/compute/task',
    meta: { title: '计算任务', icon: 'Timer' },
    children: [
      {
        path: 'task',
        name: 'ComputeTask',
        component: () => import('@/views/compute/task.vue'),
        meta: { title: '任务列表', icon: 'List' }
      }
    ]
  },
  {
    path: '/user',
    component: Layout,
    redirect: '/user/portrait',
    meta: { title: '用户画像', icon: 'User' },
    children: [
      {
        path: 'portrait',
        name: 'UserPortrait',
        component: () => import('@/views/user/portrait.vue'),
        meta: { title: '画像查询', icon: 'Search' }
      }
    ]
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router