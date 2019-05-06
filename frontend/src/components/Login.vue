<template>
  <div class="container">
    <img src="@/assets/logo.png" alt>
    <h3>Wooder</h3>
    <b-form @submit="onSubmit" @reset="onReset" v-if="show">
      <b-form-group label="Email address:" label-for="group-email">
        <b-form-input
          id="group-email"
          type="email"
          v-model="form.email"
          required
          placeholder="Enter email address"
        ></b-form-input>
      </b-form-group>
      <b-form-group label="Password:" label-for="group-password">
        <b-form-input
          id="group-password"
          type="password"
          v-model="form.password"
          required
          placeholder="Enter password"
        ></b-form-input>
      </b-form-group>
      <b-button type="submit" variant="success" block>Login</b-button>
      <br>
      <b-link href="/group/login">Login with group manager</b-link>
    </b-form>
  </div>
</template>

<script>
import { TOKEN } from "../utils";

export default {
  data() {
    return {
      form: {
        email: "",
        name: ""
      },
      show: true
    };
  },
  methods: {
    onSubmit(evt) {
      evt.preventDefault();

      // this.axios("/wooder/login", {
      //   method: "post",
      //   data: {
      //     email: this.form.email,
      //     password: this.form.password
      //   }
      // })
      //   .then(response => {
      //     if (response && response.status == 200) {
      //       this.$router.push("/member/home/projects");
      //     }
      //   })
      //   .catch(error => {
      //     if (error.response) {
      //       alert(error.response.data.message);
      //     }
      //   });
      const data = {
        email: this.form.email,
        password: this.form.password
      };
      fetch("/wooder/login", {
        method: "POST",
        body: JSON.stringify(data)
      }).then(
        response => {
          if (response.status >= 200 && response.status < 300) {
            localStorage.setItem(TOKEN, response.headers.get("Authorization"));
            this.$router.push("/member/home/projects");
          } else {
            console.log("failed");
          }
        },
        error => {
          console.log(error);
        }
      );
    },
    onReset() {}
  }
};
</script>

<style scoped>
.container {
  max-width: 300px;
}

.container img {
  max-width: 200px;
}
</style>