<template>
    <div class="container">
        <img src="@/assets/logo.png" alt="">
        <h3>Wooder</h3>
        <b-form @submit="onSubmit" @reset="onReset" v-if="show">
            <b-form-group label="Email address:" label-for="group-email">
                <b-form-input id="group-email" type="email" v-model="form.email" required placeholder="Enter email address">
                </b-form-input>
            </b-form-group>
            <b-form-group label="Password:" label-for="group-password">
                <b-form-input id="group-password" type="password" v-model="form.password" required placeholder="Enter password">
                </b-form-input>
            </b-form-group>
            <b-button type="submit" variant="success" block>Submit</b-button>
        </b-form>
    </div>
</template>

<script>
    export default {
        data() {
            return {
                form: {
                    email: '',
                    name: ''
                },
                show: true
            }
        },
        methods: {
            onSubmit(evt) {
                evt.preventDefault();

                var params = new URLSearchParams()
                params.append('email', this.form.email)
                params.append('password', this.form.password)

                this.axios.post('/web/group/login', params).then(response => {
                    if (response && response.status == 200) {

                    }
                }).catch(error => {
                    if (error.response) {
                        alert(error.response.data.message);
                    }
                })
            },
            onReset() {}
        }
    }
</script>

<style scoped>
    .container {
        max-width: 300px;
    }

    .container img {
        max-width: 200px;
    }
</style>