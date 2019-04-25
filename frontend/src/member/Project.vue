<template>
    <div class="container">
        <b-breadcrumb :items="items"></b-breadcrumb>
        <b-row>
            <b-col cols="4">
                <b-list-group>
                    <b-list-group-item v-for="folder in textfolders" :key="folder.id">
                        {{ folder.name }}
                    </b-list-group-item>
                </b-list-group>
                <br>
                <div>
                    <b-button block variant="light" size="lg" v-b-modal.add-folder>Create Folder</b-button>
                    <b-modal id="add-folder" ref="addFolder" title="New Folder" @ok="addFolder">
                        <b-form-group label-cols-sm="4" label-cols-lg="3" label="Folder Name" label-for="folder-name">
                            <b-form-input id="folder-name" v-model="form.name"></b-form-input>
                        </b-form-group>
                    </b-modal>
                </div>
            </b-col>
            <b-col cols="8">col-8</b-col>

        </b-row>
    </div>
</template>

<script>
    export default {
        data() {
            return {
                items: [
                    {
                        text: 'Projects',
                        to: '/member/home/projects'
                    }, {
                        text: 'Project',
                        active: true
                    }
                ],
                form: {
                    name: ''
                },
                textfolders: []
            }
        },
        mounted() {
            this.items[1].text = this.$route.params.projectId
            this.loadTextFolders()
        },
        methods: {
            loadTextFolders() {
                this.axios.get('/web/project/' + this.$route.params.projectId + '/textfolder/list').then(response => {
                    if (response && response.status == 200) {
                        this.textfolders = response.data.result.textfolders
                        console.log(this.textfolders)
                    }
                }).catch(error => {
                    if (error.response) {
                        alert(error.response.data.message)
                    }
                })
            },
            addFolder(bvModalEvt) {
                bvModalEvt.preventDefault()
                if (this.form.name == '') {
                    return
                }

                this.axios.post('/web/project/' + this.$route.params.projectId + '/textfolder/add', {
                    name: this.form.name
                }).then(response => {
                    if (response && response.status == 200) {
                        this.$refs.addFolder.hide()
                        this.loadTextFolders()
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