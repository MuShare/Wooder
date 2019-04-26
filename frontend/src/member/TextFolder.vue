<template>
    <div class="container">
        <b-breadcrumb :items="items"></b-breadcrumb>
        <b-row>
            <b-col cols="4">
                <b-list-group>
                    <b-list-group-item v-for="text in texts" :key="text.id">
                        {{ text.identifier }}
                    </b-list-group-item>
                </b-list-group>
                <br>
                <div>
                    <b-button block variant="light" size="lg" v-b-modal.add-text>Add Text</b-button>
                    <b-modal id="add-text" ref="addText" title="New Text" @ok="addText">
                        <b-form-group label-cols-sm="4" label-cols-lg="3" label="Identifier" label-for="text-identifier">
                            <b-form-input id="text-identifier" v-model="textForm.identifier" :state="validation"></b-form-input>
                        </b-form-group>
                    </b-modal>
                </div>
            </b-col>
            <b-col cols="8"></b-col>
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
                        text: 'Project'
                    }, {
                        text: 'TextFolder',
                        active: true
                    }
                ],
                texts: [],
                textForm: {
                    identifier: '',
                    reset() {
                        this.identifier = ''
                    }
                }
            }
        },
        computed: {
            validation() {
                const identifier = this.textForm.identifier
                return identifier != '' && !identifier.includes(' ')
            },
            textfolderId() {
                return this.$route.params.textfolderId
            }
        },
        mounted() {
            this.axios.get('/web/textfolder/' + this.textfolderId).then(response => {
                if (response && response.status == 200) {
                    const textfolder = response.data.result.textfolder
                    this.items[1].text = textfolder.project.name
                    this.items[1].to = '/member/home/project/' + textfolder.project.id
                    this.items[2].text = textfolder.name
                }
            }).catch(error => {
                if (error.response) {
                    alert(error.response.data.message)
                }
            })

            this.loadTexts()
        },
        methods: {
            loadTexts() {
                this.axios.get('/web/textfolder/' + this.textfolderId + '/text/list').then(response => {
                    if (response && response.status == 200) {
                        this.texts = response.data.result.texts
                    }
                }).catch(error => {
                    if (error.response) {
                        alert(error.response.data.message)
                    }
                })
            },
            addText(event) {
                event.preventDefault()

                if (!this.validation) {
                    return
                }

                this.axios.post('/web/textfolder/' + this.textfolderId + '/text/add', {
                    identifier: this.textForm.identifier
                }).then(response => {
                    if (response && response.status == 200) {
                        this.$refs.addText.hide()
                        this.loadTexts()
                        this.textForm.reset()
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