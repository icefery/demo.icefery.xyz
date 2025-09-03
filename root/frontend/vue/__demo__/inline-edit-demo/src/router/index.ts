import { createRouter, createWebHistory } from 'vue-router'
import InlineEditTableElement from '@/views/InlineEditElement.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: InlineEditTableElement
    }
  ]
})

export default router
