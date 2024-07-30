import {createRouter, createWebHashHistory} from 'vue-router'
import Greetings from "@/components/Greetings.vue";

const router = createRouter({
    history: createWebHashHistory(process.env.VUE_ROUTER_BASE),
    routes: [
        {
            path: '/',
            name: 'home',
            component: Greetings
        },
        {
            path: '/register',
            name: 'register',
            component: () => import('../views/Register.vue')
        },
        {
            path: '/users',
            name: 'users',
            component: () => import('../views/DisplayView.vue')
        },
        {
            path: '/users/:id',
            name: 'ViewUser',
            component: () => import('../views/ViewUser.vue'),
            props: true // Allow passing route parameters as props
        },
        {
            path: '/error',
            name: 'error',
            component: () => import('../views/ErrorView.vue'),
            props: route => ({message: route.query.message}) // Pass query parameters as props
        },
        {
            path: '/:catchAll(.*)', redirect: '/error'// Redirect all unmatched routes to error page
        }

    ]
})

export default router
