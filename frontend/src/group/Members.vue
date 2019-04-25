<template>
    <div>
        <div>
            <b-button block variant="light" size="lg" v-b-modal.add-member>Create a Member</b-button>
            <b-modal id="add-member" ref="addMember" title="New Member" @ok="handleOk">
                <form ref="addProjectForm" @submit.stop.prevent="addMember">
                    <b-form-group label-cols-sm="4" label-cols-lg="3" label="Email" label-for="member-email"
                                  :state="form.state" invalid-feedback="Email address is required">
                        <b-form-input id="member-email" type="email" v-model="form.email" required></b-form-input>
                    </b-form-group>
                    <b-form-group label-cols-sm="4" label-cols-lg="3" label="Name" label-for="member-name"
                                  :state="form.state" invalid-feedback="Name is required">
                        <b-form-input id="member-name" type="text" v-model="form.name" required></b-form-input>
                    </b-form-group>
                    <b-form-group label-cols-sm="4" label-cols-lg="3" label="Password" label-for="member-password"
                                  :state="form.state" invalid-feedback="Password is required">
                        <b-form-input id="member-password" type="text" v-model="form.password" required></b-form-input>
                    </b-form-group>
                    <b-alert v-model="form.invalid" variant="danger" dismissible>
                        A valid email, members name and password is required!
                    </b-alert>
                </form>
            </b-modal>
        </div>
    </div>
</template>

<script>
    export default {
        data() {
            return {
                form: {
                    invalid: false,
                    email: '',
                    name: '',
                    password: ''
                }
            }
        },
        methods: {
            checkFormValidity() {
                const valid = this.$refs.addProjectForm.checkValidity()
                this.form.invalid = !valid
                return valid
            },
            handleOk(bvModalEvt) {
                // Prevent modal from closing
                bvModalEvt.preventDefault()
                // Trigger submit handler
                this.addMember()
            },
            addMember() {
                if (!this.checkFormValidity()) {
                    return
                }
                this.axios.post('/web/member/add', {
                    email: this.form.email,
                    name: this.form.name,
                    password: this.form.password
                }).then(response => {
                    console.log(response)
                    if (response && response.status == 200) {
                        this.$refs.addMember.hide()
                    }
                }).catch(error => {
                    if (error.response) {
                        alert(error.response.data.message)
                    }
                })
            }
        }
    }
</script>