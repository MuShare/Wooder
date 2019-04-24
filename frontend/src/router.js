import Vue from 'vue'
import Router from 'vue-router'
import Login from '@/group/Login.vue'

Vue.use(Router)

export default new Router({
    mode: 'history',
    base: process.env.BASE_URL,
    routes: [
        {
            path: '/',
            name: 'login',
            component: Login
        }, {
            path: '/group/register',
            name: 'Group Registeration',
            // route level code-splitting
            // this generates a separate chunk (about.[hash].js) for this route
            // which is lazy-loaded when the route is visited.
            component: () => import(/* webpackChunkName: "about" */ '@/group/Register.vue')
        }, {
            path: '/group/home',
            name: 'Group Home',
            component: () => import('@/group/Home.vue')
        }
    ]
})
