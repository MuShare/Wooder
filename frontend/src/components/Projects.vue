<template>
  <div class="container">
    <b-card-group columns>
      <b-card v-for="project in projects.projectInfoResponses" :key="project.id">
        <h2>
          <router-link :to="`/member/home/project/${ project.id }`">{{ project.name }}</router-link>
        </h2>
      </b-card>
    </b-card-group>
    <div>
      <b-button block variant="light" size="lg" v-b-modal.add-project>Create a Project</b-button>
    </div>
    <b-modal id="add-project" ref="addProject" title="New Project" @ok="addProject">
      <b-form-group
        label-cols-sm="4"
        label-cols-lg="3"
        label="Project Name"
        label-for="project-name"
      >
        <b-form-input id="project-name" v-model="form.name"></b-form-input>
      </b-form-group>
      <b-form-group
        label-cols-sm="4"
        label-cols-lg="3"
        label="Project Description"
        label-for="project-description"
      >
        <b-form-input id="project-description" v-model="form.description"></b-form-input>
      </b-form-group>
    </b-modal>
  </div>
</template>

<script>
import { getToken, baseUrl, handleError } from "../utils";
export default {
  data() {
    return {
      projects: {
          totalCount: 0,
          projectInfoResponses: []
      },
      form: {
        name: "",
        description: "",
        groupId: ""
      }
    };
  },
  mounted() {
    this.loadProjects();
  },
  methods: {
    addProject() {
      const url = `${baseUrl}/web/project/add`;
      fetch(url, {
        headers: {
          Authorization: getToken(),
          "Content-Type": "application/json"
        },
        method: "PUT",
        body: JSON.stringify(this.form)
      })
        .then(response => {
          if (response && response.status == 200) {
            this.$refs.addProject.hide();
            this.loadProjects();
          }
        })
        .catch(error => {
          if (error.response) {
            alert(error.response.data.message);
          }
        });
    },
    loadProjects() {
      const url = `${baseUrl}/web/project/list?pageNumber=0&pageSize=500`;
      fetch(url, {
        headers: { Authorization: getToken() }
      })
        .then(handleError)
        .then(json => {
          this.projects = json;
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