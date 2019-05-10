<template>
  <div class="container">
    <b-navbar toggleable="lg" variant="light">
      <b-navbar-brand href="#">{{ member.groupName }}</b-navbar-brand>

      <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>

      <b-collapse id="nav-collapse" is-nav>
        <b-navbar-nav>
          <b-nav-item>
            <router-link to="/member/home/projects">Projects</router-link>
          </b-nav-item>
        </b-navbar-nav>

        <b-navbar-nav class="ml-auto">
          <b-nav-item-dropdown right>
            <template slot="button-content">
              <em>{{ member.username }}</em>
            </template>
            <b-dropdown-item href="#">Settings</b-dropdown-item>
            <b-dropdown-item href="#">Sign Out</b-dropdown-item>
          </b-nav-item-dropdown>
        </b-navbar-nav>
      </b-collapse>
    </b-navbar>
    <br>
    <router-view></router-view>
  </div>
</template>

<script>
import { getToken, baseUrl, handleError } from "../utils";
export default {
  data() {
    return {
      member: {
        username: "",
        groupName: ""
      }
    };
  },
  mounted() {
    this.loadMemberInfo();
  },
  methods: {
    loadMemberInfo() {
      const url = `${baseUrl}/web/user/info`;
      fetch(url, {
        headers: { Authorization: getToken() }
      })
        .then(handleError)
        .then(json => {
          this.member = json;
        })
        .catch(error => {
          if (error.response) {
            alert(error.response.data.message);
          }
        });
    }
  }
};
</script>