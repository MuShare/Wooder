import Vue from 'vue'
import BootstrapVue from 'bootstrap-vue'
import axios from 'axios'
import VueAxios from 'vue-axios'
import qs from 'qs'

import App from './App.vue'
import router from './router'
import store from './store'

import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'

Vue.config.productionTip = false
Vue.use(BootstrapVue)
Vue.use(VueAxios, axios)

new Vue({
    router,
    store,
    render: h => h(App)
}).$mount('#app')

axios.defaults.transformRequest = function(data) {
    data = qs.stringify(data)
    return data
}
