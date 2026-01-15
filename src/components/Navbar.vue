<template>
  <nav class="navbar" :class="{ scrolled: isScrolled }">
    <div class="logo">
      <router-link to="/">
        <span class="logo-text">CINE<span>PASS</span></span>
      </router-link>
    </div>
    
    <div class="nav-links">
      <router-link to="/">Movies</router-link>
      <router-link to="/experiences">Experiences</router-link>
      <router-link to="/offers">Offers</router-link>
    </div>

    <div class="user-actions">
      <div class="search-bar">
        <input type="text" placeholder="Search movies..." />
        <i class="fas fa-search"></i>
      </div>
      
      <router-link v-if="user.isLoggedIn" to="/my-dashboard" class="user-profile">
        <img :src="'https://ui-avatars.com/api/?name=' + user.name + '&background=e50914&color=fff'" alt="User" />
      </router-link>
      
      <router-link v-else to="/login" class="login-btn">
        Sign In
      </router-link>
    </div>
  </nav>
</template>

<script>
import { currentUser } from '../model/mockData.js'

export default {
  name: 'Navbar',
  data() {
    return {
      isScrolled: false,
      user: currentUser
    }
  },
  mounted() {
    window.addEventListener('scroll', this.handleScroll);
  },
  unmounted() {
    window.removeEventListener('scroll', this.handleScroll);
  },
  methods: {
    handleScroll() {
      this.isScrolled = window.scrollY > 50;
    }
  }
}
</script>

<style scoped>
.navbar {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 1.5rem 4rem;
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  z-index: 1000;
  transition: all 0.4s ease;
  background: linear-gradient(to bottom, rgba(0,0,0,0.8), transparent);
}

.navbar.scrolled {
  background: rgba(12, 12, 12, 0.95);
  backdrop-filter: blur(10px);
  padding: 1rem 4rem;
  box-shadow: 0 4px 30px rgba(0, 0, 0, 0.5);
}

.logo-text {
  font-size: 1.8rem;
  font-weight: 800;
  color: white;
  text-decoration: none;
  letter-spacing: -1px;
}

.logo-text span {
  color: var(--primary);
}

.nav-links {
  display: flex;
  gap: 2.5rem;
}

.nav-links a {
  color: rgba(255, 255, 255, 0.7);
  text-decoration: none;
  font-weight: 500;
  font-size: 0.95rem;
  transition: color 0.3s;
  position: relative;
}

.nav-links a:hover, .nav-links a.router-link-active {
  color: white;
}

.nav-links a::after {
  content: '';
  position: absolute;
  bottom: -5px;
  left: 0;
  width: 0;
  height: 2px;
  background: var(--primary);
  transition: width 0.3s ease;
}

.nav-links a:hover::after, .nav-links a.router-link-active::after {
  width: 100%;
}

.user-actions {
  display: flex;
  align-items: center;
  gap: 1.5rem;
}

.search-bar {
  position: relative;
}

.search-bar input {
  background: rgba(255, 255, 255, 0.1);
  border: 1px solid rgba(255, 255, 255, 0.1);
  border-radius: 20px;
  padding: 0.5rem 1rem 0.5rem 2.5rem;
  color: white;
  font-size: 0.9rem;
  width: 200px;
  transition: all 0.3s;
}

.search-bar input:focus {
  background: rgba(255, 255, 255, 0.15);
  border-color: rgba(255, 255, 255, 0.3);
  width: 250px;
}

.user-profile img {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  border: 2px solid transparent;
  transition: border-color 0.3s;
}

.user-profile:hover img {
  border-color: var(--primary);
}

@media (max-width: 768px) {
  .navbar {
    padding: 1rem 1.5rem;
  }
  
  .nav-links, .search-bar {
    display: none;
  }
}
</style>
/* Login Button in Navbar */ .login-btn { background: #e50914; color: white; padding: 0.5rem 1.2rem; border-radius: 6px; text-decoration: none; font-weight: 600; font-size: 0.9rem; transition: all 0.3s; } .login-btn:hover { background: #c40812; }
