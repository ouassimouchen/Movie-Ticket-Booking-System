import { createRouter, createWebHistory } from 'vue-router'
import LoginView from '../views/LoginView.vue'
import MainView from '../views/MainView.vue'
import HomeView from '../views/HomeView.vue'
import MovieDetailView from '../views/MovieDetailView.vue'
import BookingView from '../views/BookingView.vue'
import CheckoutView from '../views/CheckoutView.vue'
import UserDashboard from '../views/UserDashboard.vue'
import UserLogin from '../views/UserLogin.vue'
import UserSignup from '../views/UserSignup.vue'
import OffersView from '../views/OffersView.vue'
import ExperiencesView from '../views/ExperiencesView.vue'

const routes = [
    {
        path: '/',
        name: 'home',
        component: HomeView
    },
    {
        path: '/login',
        name: 'user-login',
        component: UserLogin
    },
    {
        path: '/signup',
        name: 'user-signup',
        component: UserSignup
    },
    {
        path: '/movie/:id',
        name: 'movie-detail',
        component: MovieDetailView
    },
    {
        path: '/booking/:sessionId',
        name: 'booking',
        component: BookingView
    },
    {
        path: '/checkout',
        name: 'checkout',
        component: CheckoutView
    },
    {
        path: '/my-dashboard',
        name: 'user-dashboard',
        component: UserDashboard
    },
    {
        path: '/admin/login',
        name: 'admin-login',
        component: LoginView
    },
    {
        path: '/admin/dashboard',
        name: 'admin-dashboard',
        component: MainView
    },
    {
        path: '/offers',
        name: 'offers',
        component: OffersView
    },
    {
        path: '/experiences',
        name: 'experiences',
        component: ExperiencesView
    }
]

const router = createRouter({
    history: createWebHistory(),
    routes,
    scrollBehavior(to, from, savedPosition) {
        if (savedPosition) {
            return savedPosition
        } else {
            return { top: 0 }
        }
    }
})

export default router
