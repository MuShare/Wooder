import Vue from 'vue'
import Router from 'vue-router'
import MemberLogin from '@/member/Login.vue'
import GroupLogin from '@/group/Login.vue'
import SessionOut from '@/components/SessionOut'

Vue.use(Router)

export default new Router({
    mode: 'history',
    base: process.env.BASE_URL,
    routes: [
        {
            path: '/',
            name: 'Group Member Login',
            component: MemberLogin
        }, {
            path: '/group/login',
            name: 'Group Manager Login',
            component: GroupLogin
        }, {
            path: '/sessionOut',
            name: 'sessionOut',
            component: SessionOut
        }, {
            path: '/group/register',
            name: 'Group Registeration',
            component: () => import('@/group/Register.vue')
        }, {
            path: '/group/home',
            name: 'Group Home',
            component: () => import('@/group/Home.vue'),
            children: [
                {
                    path: '/group/home/projects',
                    name: 'Projects of Group',
                    component: require('@/group/Projects.vue').default
                }, {
                    path: '/group/home/members',
                    name: 'Members of Group',
                    component: require('@/group/Members.vue').default
                }
            ]
        }, {
            path: '/member/home',
            name: 'Member Home',
            component: () => import('@/member/Home.vue'),
            children: [
                {
                    path: '/member/home/projects',
                    component: require('@/member/Projects.vue').default
                }, {
                    path: '/member/home/project/:projectId',
                    component: require('@/member/Project.vue').default
                }, {
                    path: '/member/home/textfolder/:textfolderId',
                    component: require('@/member/TextFolder.vue').default
                }
            ]
        }
    ]
})
