<template>
    <div class="container">
        <b-breadcrumb :items="items"></b-breadcrumb>
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
                ]
            }
        }, mounted() {
            this.axios.get('/web/textfolder/' + this.$route.params.textfolderId).then(response => {
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
        }
    }
</script>