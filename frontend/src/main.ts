import './assets/main.css'

import { createApp } from 'vue'
import routes from '~pages'
import App from './App.vue'
import {createRouter, createWebHistory} from "vue-router";

const app = createApp(App)


const router = createRouter({
  history: createWebHistory(),
  routes,
});

router.beforeEach((to, from, next) => {
  if (to.path === '/') {
    next('/create')
  } else {
    next()
  }
})

app.use(router)


app.mount('#app')
