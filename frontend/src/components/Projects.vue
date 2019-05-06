<template>
    <div class="container">
        <b-card-group columns>
            <!-- <b-card v-for="project in projects" :key="project.id"> -->
                <!-- <h2><router-link :to="`/member/home/project/${ project.id }`">{{ project.name }}</router-link></h2> -->
            <!-- </b-card> -->
        </b-card-group>
    </div>
</template>

<script>
import { getToken, baseUrl, handleError } from "../utils";
    export default {
        data() {
            return {
                projects: []
            }
        },
        mounted() {
            // this.axios.get('/web/project/list').then(response => {
            //     if (response && response.status == 200) {
            //         this.projects = response.data.result.projects
            //     }
            // }).catch(error => {
            //     if (error.response) {
            //         alert(error.response.data.message)
            //     }
            // })
            const url = `${baseUrl}/web/project/list?pageNumber=0&pageSize=500`;
            fetch(url, {
                headers: {Authorization: getToken()}
            })
            .then(handleError)
            .then(json =>{
                console.log(json);
            })
            .catch(error => {
                if (error.response) {
                    alert(error.response.data.message)
                }
            });
        }
    }
</script>