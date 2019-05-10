<template>
  <div class="container">
    <img src="@/assets/logo.png" alt>
    <h3>Wooder</h3>
    <h4>Create a group</h4>
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
      <b-form-group label="Group Name:" label-for="group-name">
        <b-form-input
          id="group-name"
          type="text"
          v-model="form.name"
          required
          placeholder="Enter group name"
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
      <b-button type="submit" variant="success" block>Submit</b-button>
      <br>
      <b-link href="/group/login">Back to login</b-link>
    </b-form>
  </div>
</template>

<script>
export default {
  data() {
    return {
      form: {
        email: "",
        name: "",
        password: ""
      },
      show: true
    };
  },
  methods: {
    onSubmit(evt) {
      evt.preventDefault();

      this.axios
        .post("/web/group/create", {
          email: this.form.email,
          name: this.form.name,
          password: this.form.password
        })
        .then(response => {
          if (response && response.status == 200) {
            this.$router.push("../");
          }
        })
        .catch(error => {
          if (error.response) {
            alert(error.response.data.message);
          }
        });
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