import Vue from 'vue'
import App from './App'
import router from './router'
import BootstrapVue from 'bootstrap-vue'
import VueGitHubButtons from 'vue-github-buttons'
import {library} from '@fortawesome/fontawesome-svg-core'
import {fas} from '@fortawesome/free-solid-svg-icons'
import {FontAwesomeIcon} from '@fortawesome/vue-fontawesome'
// Stylesheet
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'
import 'vue-github-buttons/dist/vue-github-buttons.css';


Vue.config.productionTip = false

library.add(fas)

Vue.component('font-awesome-icon', FontAwesomeIcon)
Vue.use(BootstrapVue);
Vue.use(VueGitHubButtons, {useCache: false});

/* eslint-disable no-new*/
new Vue({
  el: '#app',
  router,
  render: h => h(App)
})
/* eslint-disable no-new
new Vue({
  el: '#app',
  components: { App },
  template: '<App/>'
})*/
