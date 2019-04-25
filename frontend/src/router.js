import Vue from 'vue'
import Router from 'vue-router'
import Login from '@/group/Login.vue'
import Home from '@/group/Register.vue'
import SessionOut from '@/components/SessionOut'

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
            component: Home
        }, {
            path: '/group/home',
            name: 'Group Home',
            component: () => import('@/group/Home.vue'),
            children: [
                {
                    path: '/group/home/projects',
                    name: 'Group Projects',
                    component: require('@/group/Projects.vue').default
                }, {
                    path: '/group/home/members',
                    name: 'Group Members',
                    component: require('@/group/Members.vue').default
                }
            ]
        }, {
            path: '/sessionOut',
            name: 'sessionOut',
            component: SessionOut
        }
    ]
})
