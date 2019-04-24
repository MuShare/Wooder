<template>
    <div class="container">
        <b-navbar toggleable="lg" variant="light">
            <b-navbar-brand href="#">Woody Group</b-navbar-brand>

            <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>

            <b-collapse id="nav-collapse" is-nav>
                <b-navbar-nav>
                    <b-nav-item href="#">Projects</b-nav-item>
                    <b-nav-item href="#">Members</b-nav-item>
                </b-navbar-nav>

                <b-navbar-nav class="ml-auto">
                    <b-nav-item-dropdown right>
                        <template slot="button-content"><em>Group Name</em></template>
                        <b-dropdown-item href="#">Settings</b-dropdown-item>
                        <b-dropdown-item href="#">Sign Out</b-dropdown-item>
                    </b-nav-item-dropdown>
                </b-navbar-nav>
            </b-collapse>
        </b-navbar>
        <br>
        <b-card-group columns>
            <b-card>
                <h2>MuShare</h2>
            </b-card>
            <b-card>
                <h2>Rinrin</h2>
            </b-card>
            <b-card>
                <h2>Athene</h2>
            </b-card>
            <b-card>
                <h2>Tipstar</h2>
            </b-card>
            <b-card>
                <h2>RxBinding</h2>
            </b-card>
        </b-card-group>
        <div>
            <b-button block variant="light" size="lg" v-b-modal.add-project>Create a Project</b-button>
            <b-modal id="add-project" ref="addProject" title="New Project" @ok="addProject">
                <b-form-group label-cols-sm="4" label-cols-lg="3" label="Project Name" label-for="project-name">
                    <b-form-input id="project-name" v-model="project.name"></b-form-input>
                </b-form-group>
            </b-modal>
        </div>
    </div>
</template>

<script>
    export default {
        data() {
            return {
                project: {
                    name: ''
                }
            }
        },
        methods: {
            addProject() {
                var params = new URLSearchParams()
                params.append('name', this.project.name)

                this.axios.post('/web/project/add', params).then(response => {
                    if (response && response.status == 200) {
                        this.$refs.addProject.hide()
                    }
                }).catch(error => {
                    if (error.response) {
                        alert(error.response.data.message);
                    }
                })
            }
        }
    }
</script>
