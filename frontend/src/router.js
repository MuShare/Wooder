import Vue from "vue";
import Router from "vue-router";
import MemberLogin from "@/components/Login.vue";
import SessionOut from "@/components/SessionOut";

Vue.use(Router);

export default new Router({
  mode: "history",
  base: process.env.BASE_URL,
  routes: [
    {
      path: "/",
      name: "Group Member Login",
      component: MemberLogin
    },
    {
      path: "/sessionOut",
      name: "sessionOut",
      component: SessionOut
    },
    {
      path: "/group/register",
      name: "Group Registeration",
      component: () => import("@/components/Register.vue")
    },
    {
      path: "/member/home",
      name: "Member Home",
      component: () => import("@/components/Home.vue"),
      children: [
        {
          path: "/member/home/projects",
          component: require("@/components/Projects.vue").default
        },
        {
          path: "/member/home/project/:projectId",
          component: require("@/components/Project.vue").default
        },
        {
          path: "/member/home/textfolder/:textfolderId",
          component: require("@/components/TextFolder.vue").default
        }
      ]
    }
  ]
});
