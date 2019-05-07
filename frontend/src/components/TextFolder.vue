<template>
  <div class="container">
    <b-breadcrumb :items="items"></b-breadcrumb>
    <b-row>
      <b-col cols="4">
        <b-list-group>
          <b-list-group-item
            v-for="text in texts"
            :key="text.id"
            href="#"
            @click="editText(text.id)"
          >{{ text.identifier }}</b-list-group-item>
        </b-list-group>
        <br>
        <div>
          <b-button block variant="light" size="lg" v-b-modal.add-text>Add Text</b-button>
          <b-modal id="add-text" ref="addText" title="New Text" @ok="addText">
            <b-form-group
              label-cols-sm="4"
              label-cols-lg="3"
              label="Identifier"
              label-for="add-text-identifier"
            >
              <b-form-input
                id="add-text-identifier"
                v-model="textForm.identifier"
                :state="validation"
              ></b-form-input>
            </b-form-group>
          </b-modal>
        </div>
      </b-col>
      <b-col cols="8">
        <b-form-group label="Text Identifier" label-for="text-identifier" class="text-left">
          <b-form-input
            id="text-identifier"
            type="text"
            v-model="editingText.identifier"
            :state="identifierValidation"
          ></b-form-input>
        </b-form-group>
        <b-form-group label="Text Name" label-for="text-name" class="text-left">
          <b-form-input id="text-name" type="text" v-model="editingText.name"></b-form-input>
        </b-form-group>
        <b-form-group label="Supported Platforms" class="text-left">
          <b-form-checkbox-group v-model="editingText.platforms" :options="options" switches></b-form-checkbox-group>
        </b-form-group>
        <b-form-group
          v-for="content in editingText.contents"
          :key="content.id"
          :label="content.language.identifier + ', ' + content.language.name"
          class="text-left"
        >
          <b-form-input type="text" v-model="content.string"></b-form-input>
        </b-form-group>
        <b-button block variant="outline-success" size="lg" @click="saveEditingText">Save Text</b-button>
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
          text: "Project"
        },
        {
          text: "TextFolder",
          active: true
        }
      ],
      texts: [],
      textForm: {
        identifier: "",
        reset() {
          this.identifier = "";
        }
      },
      editingText: {
        identifier: "",
        name: "",
        platforms: ["android", "ios"]
      },
      options: [
        { text: "Android", value: "android" },
        { text: "iOS", value: "ios" }
      ]
    };
  },
  computed: {
    validation() {
      const identifier = this.textForm.identifier;
      return identifier != "" && !identifier.includes(" ");
    },
    textfolderId() {
      return this.$route.params.textfolderId;
    },
    identifierValidation() {
      const identifier = this.editingText.identifier;
      return identifier != "" && !identifier.includes(" ");
    }
  },
  mounted() {
    this.loadTextFolder();
    this.loadTexts();
  },
  methods: {
    loadTextFolder() {
      const url = `${baseUrl}/web/textfolder/${
        this.$route.params.textfolderId
      }`;
      fetch(url, {
        headers: {
          Authorization: getToken(),
          "Content-Type": "application/json"
        }
      })
        .then(handleError)
        .then(textfolder => {
          this.items[1].text = textfolder.projectInfoResponse.name;
          this.items[1].to =
            "/member/home/project/" + textfolder.projectInfoResponse.id;
          this.items[2].text = textfolder.name;
        })
        .catch(error => {
          if (error.response) {
            alert(error.response.data.message);
          }
        });
    },
    loadTexts() {
      const url = `${baseUrl}/web/textfolder/${
        this.$route.params.textfolderId
      }/text/list`;
      console.log(url);
      fetch(url, {
        headers: {
          Authorization: getToken(),
          "Content-Type": "application/json"
        }
      })
        .then(handleError)
        .then(textListResponse => {
          this.texts = textListResponse.textResponses;
        })
        .catch(error => {
          if (error.response) {
            alert(error.response.data.message);
          }
        });
    },
    addText(event) {
      event.preventDefault();

      if (!this.validation) {
        return;
      }

      const url = `${baseUrl}/web/textfolder/${
        this.$route.params.textfolderId
      }/text/add?identifier=${this.textForm.identifier}`;
      fetch(url, {
        headers: {
          Authorization: getToken(),
          "Content-Type": "application/json"
        },
        method: "POST"
      })
        .then(handleError)
        .then(operationResponse => {
          if (operationResponse.succeed) {
            this.$refs.addText.hide();
            this.loadTexts();
            this.textForm.reset();
          }
        })
        .catch(error => {
          if (error.response) {
            alert(error.response.data.message);
          }
        });

      // this.axios
      //   .post("/web/textfolder/" + this.textfolderId + "/text/add", {
      //     identifier: this.textForm.identifier
      //   })
      //   .then(response => {
      //     if (response && response.status == 200) {
      //       this.$refs.addText.hide();
      //       this.loadTexts();
      //       this.textForm.reset();
      //     }
      //   })
      //   .catch(error => {
      //     if (error.response) {
      //       alert(error.response.data.message);
      //     }
      //   });
    },
    editText(textId) {
      const url = `${baseUrl}/web/text/${textId}`;
      fetch(url, {
        headers: {
          Authorization: getToken(),
          "Content-Type": "application/json"
        }
      })
        .then(handleError)
        .then(textResponse => {
          this.editText = textResponse;
        })
        .catch(error => {
          if (error.response) {
            alert(error.response.data.message);
          }
        });

      // this.axios
      //   .get("/web/text/" + textId)
      //   .then(response => {
      //     if (response && response.status == 200) {
      //       this.editingText = response.data.result.text;
      //     }
      //   })
      //   .catch(error => {
      //     if (error.response) {
      //       alert(error.response.data.message);
      //     }
      //   });
    },
    saveEditingText() {
      const url = `${baseUrl}/web/text/edit`;
      fetch(url, {
        headers: {
          Authorization: getToken(),
          "Content-Type": "application/json"
        },
        method: "POST",
        data: JSON.stringify(this.editText)
      })
        .then(handleError)
        .then(operationResponse => {
          if (operationResponse.succeed) {
            this.$bvToast.toast(
              this.editingText.identifier + " has been saved successfully!",
              {
                title: "Tip",
                autoHideDelay: 2000
              }
            );
          }
        })
        .catch(error => {
          if (error.response) {
            alert(error.response.data.message);
          }
        });

      // this.axios({
      //   method: "post",
      //   url: "/web/text/edit",
      //   headers: {
      //     "Content-Type": "application/json"
      //   },
      //   transformRequest: data => {
      //     return data;
      //   },
      //   data: JSON.stringify(this.editingText)
      // })
      //   .then(response => {
      //     if (response && response.status == 200) {
      //       this.$bvToast.toast(
      //         this.editingText.identifier + " has been saved successfully!",
      //         {
      //           title: "Tip",
      //           autoHideDelay: 2000
      //         }
      //       );
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