<template>
  <div class="container">
    <b-breadcrumb :items="items"></b-breadcrumb>
    <b-row>
      <b-col cols="8">
        <b-list-group>
          <b-list-group-item v-for="folder in textfolders" :key="folder.id">
            <router-link :to="`/member/home/textfolder/${folder.id}`">{{ folder.name }}</router-link>
          </b-list-group-item>
        </b-list-group>
        <br>
        <div>
          <b-button block variant="light" size="lg" v-b-modal.add-folder>Create Folder</b-button>
          <b-modal id="add-folder" ref="addFolder" title="New Folder" @ok="addFolder">
            <b-form-group
              label-cols-sm="4"
              label-cols-lg="3"
              label="Folder Name"
              label-for="folder-name"
            >
              <b-form-input id="folder-name" v-model="textfolderForm.name"></b-form-input>
            </b-form-group>
          </b-modal>
        </div>
      </b-col>
      <b-col cols="4">
        <b-list-group>
          <b-list-group-item
            v-for="language in languages"
            :key="language.id"
          >{{ language.name }}, {{ language.identifier }}</b-list-group-item>
        </b-list-group>
        <br>
        <div>
          <b-button block variant="light" size="lg" v-b-modal.add-language>Create Language</b-button>
          <b-modal id="add-language" ref="addLanguage" title="New Language" @ok="addLanguage">
            <form ref="addLanguageForm">
              <b-form-group
                label-cols-sm="4"
                label-cols-lg="3"
                label="Identifier"
                label-for="folder-name"
              >
                <b-form-input
                  id="language-identifier"
                  type="text"
                  v-model="languageForm.identifier"
                ></b-form-input>
              </b-form-group>
              <b-form-group
                label-cols-sm="4"
                label-cols-lg="3"
                label="Language Name"
                label-for="folder-name"
              >
                <b-form-input id="language-name" type="text" v-model="languageForm.name"></b-form-input>
              </b-form-group>
            </form>
            <b-alert
              v-model="languageForm.invalid"
              variant="danger"
              dismissible
            >A valid language identifier and name is required!</b-alert>
          </b-modal>
        </div>
      </b-col>
    </b-row>
  </div>
</template>

<script>
import { getToken, baseUrl, handleError } from "../utils";
export default {
  data() {
    return {
      items: [
        {
          text: "Projects",
          to: "/member/home/projects"
        },
        {
          text: "Project",
          active: true
        }
      ],
      textfolderForm: {
        name: "",
        reset() {
          this.name = "";
        }
      },
      languageForm: {
        invalid: false,
        identifier: "",
        name: "",
        checkValidity() {
          return this.identifier != "" && this.name != "";
        },
        reset() {
          this.identifier = "";
          this.name = "";
        }
      },
      textfolders: [],
      languages: []
    };
  },
  mounted() {
    this.loadeProjectInfo();
    this.loadTextFolders();
    this.loadLanguages();
  },
  methods: {
    loadeProjectInfo() {
      const url = `${baseUrl}/web/project/${this.$route.params.projectId}`;
      fetch(url, {
        headers: {
          Authorization: getToken(),
          "Content-Type": "application/json"
        }
      })
        .then(handleError)
        .then(json => {
          this.items[1].text = json.name;
        })
        .catch(error => {
          if (error.response) {
            alert(error.response.data.message);
          }
        });
    },

    loadTextFolders() {
      const url = `${baseUrl}/web/project/${
        this.$route.params.projectId
      }/textfolder/list`;
      fetch(url, {
        headers: {
          Authorization: getToken(),
          "Content-Type": "application/json"
        }
      })
        .then(handleError)
        .then(json => {
          this.textfolders = json.textFolderListItems;
        })
        .catch(error => {
          if (error.response) {
            alert(error.response.data.message);
          }
        });
    },
    addFolder(event) {
      event.preventDefault();
      if (this.textfolderForm.name == "") {
        return;
      }
      const url = `${baseUrl}/web/project/${
        this.$route.params.projectId
      }/textfolder/add?name=${this.textfolderForm.name}`;
      fetch(url, {
        headers: {
          Authorization: getToken(),
          "Content-Type": "application/json"
        },
        method: "POST"
      })
        .then(handleError)
        .then(json => {
          if (json.succeed) {
            this.$refs.addFolder.hide();
            this.loadTextFolders();
            this.textfolderForm.reset();
          }
        })
        .catch(error => {
          if (error.response) {
            alert(error.response.data.message);
          }
        });
    },
    loadLanguages() {
      const url = `${baseUrl}/web/project/${
        this.$route.params.projectId
      }/language/list`;
      fetch(url, {
        headers: {
          Authorization: getToken(),
          "Content-Type": "application/json"
        }
      })
        .then(handleError)
        .then(json => {
          this.languages = json.languageListItems;
        })
        .catch(error => {
          if (error.response) {
            alert(error.response.data.message);
          }
        });
    },
    addLanguage(event) {
      event.preventDefault();

      this.languageForm.invalid = !this.languageForm.checkValidity();
      if (this.languageForm.invalid) {
        return;
      }

      const url = `${baseUrl}/web/project/${
        this.$route.params.projectId
      }/language/add?identifier=${this.languageForm.identifier}&name=${
        this.languageForm.name
      }`;
      fetch(url, {
        headers: {
          Authorization: getToken(),
          "Content-Type": "application/json"
        },
        method: "POST"
      })
        .then(handleError)
        .then(json => {
          if (json.succeed) {
            this.$refs.addLanguage.hide();
            this.loadLanguages();
            this.languageForm.reset();
          }
        })
        .catch(error => {
          if (error.response) {
            alert(error.response.data.message);
          }
        });
      // this.axios
      //   .post(
      //     "/web/project/" + this.$route.params.projectId + "/language/add",
      //     {
      //       identifier: this.languageForm.identifier,
      //       name: this.languageForm.name
      //     }
      //   )
      //   .then(response => {
      //     if (response && response.status == 200) {
      //       this.$refs.addLanguage.hide();
      //       this.loadLanguages();
      //       this.languageForm.reset();
      //     }
      //   })
      //   .catch(error => {
      //     if (error.response) {
      //       alert(error.response.data.message);
      //     }
      //   });
    }
  }
};
</script>