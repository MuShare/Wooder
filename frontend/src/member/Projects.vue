<template>
    <div class="container">
        <b-card-group columns>
            <b-card v-for="project in projects" :key="project.id">
                <h2>{{ project.name }}</h2>
            </b-card>
        </b-card-group>
    </div>
</template>

<script>
    export default {
        data() {
            return {
                projects: []
            }
        },
        mounted() {
            this.axios.get('/web/project/list').then(response => {
                if (response && response.status == 200) {
                    this.projects = response.data.result.projects
                }
            }).catch(error => {
                if (error.response) {
                    alert(error.response.data.message)
                }
            })
        }
    }
</script>