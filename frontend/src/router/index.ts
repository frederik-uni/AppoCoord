import { createRouter, createWebHistory } from 'vue-router'
import CreateView from '../views/CreateView.vue'
import ReplyView from "@/views/ReplyView.vue";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      redirect: '/create',
    },
    {
      path: '/create',
      name: 'home',
      component: CreateView,
    },
    {
      path: '/info',
      name: 'Reply',
      component: ReplyView,
    },
  ],
})

export default router
