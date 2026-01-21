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
      <router-link v-if="user.isLoggedIn" to="/my-dashboard">My Bookings</router-link>
    </div>

    <div class="user-actions">
      <div class="search-bar">
        <input type="text" v-model="store.searchQuery" placeholder="Search movies..." />
        <i class="fas fa-search"></i>
        
        <!-- Search Results Dropdown -->
        <div v-if="filteredResults.length > 0" class="results-dropdown">
          <div 
            v-for="movie in filteredResults" 
            :key="movie.id" 
            class="result-item"
            @click="selectMovie(movie.id)"
          >
            <img :src="movie.poster" :alt="movie.title" class="result-poster" />
            <div class="result-info">
              <span class="result-title">{{ movie.title }}</span>
              <span class="result-genre">{{ movie.genre.join(', ') }}</span>
            </div>
          </div>
        </div>
      </div>
      
      <div v-if="user.isLoggedIn" class="user-profile-group">
        <router-link to="/my-dashboard" class="user-profile">
          <img :src="'https://ui-avatars.com/api/?name=' + user.name + '&background=e50914&color=fff'" alt="User" />
        </router-link>
        <button @click="handleLogout" class="logout-btn" title="Logout">
          <i class="fas fa-sign-out-alt"></i>
        </button>
      </div>
      
      <router-link v-else to="/login" class="login-btn">
        Sign In
      </router-link>
    </div>
  </nav>
</template>

<script>

import { store } from '../store';
import api from '../api';

export default {
  name: 'Navbar',
  data() {
    return {
      isScrolled: false,
      allMovies: [],
      store
    }
  },
  async created() {
    try {
      const response = await api.get('/movies');
      this.allMovies = response.data;
    } catch (err) {
      console.error('Error fetching movies for navbar:', err);
    }
  },
  computed: {
    user() {
      return {
        isLoggedIn: !!localStorage.getItem('token'),
        name: JSON.parse(localStorage.getItem('user') || '{}').name || ''
      }
    },
    filteredResults() {
      if (!this.store.searchQuery || this.store.searchQuery.length < 2) return [];
      const query = this.store.searchQuery.toLowerCase();
      return this.allMovies.filter(m => 
        m.title.toLowerCase().includes(query) || 
        m.genre.some(g => g.toLowerCase().includes(query))
      ).slice(0, 5); // Limit to 5 results
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
    },
    handleLogout() {
      localStorage.removeItem('token');
      localStorage.removeItem('user');
      window.location.reload();
    },
    selectMovie(movieId) {
      this.store.searchQuery = '';
      this.$router.push('/movie/' + movieId);
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

.results-dropdown {
  position: absolute;
  top: calc(100% + 10px);
  left: 0;
  right: 0;
  background: rgba(18, 18, 18, 0.95);
  backdrop-filter: blur(20px);
  border: 1px solid rgba(255, 255, 255, 0.1);
  border-radius: 12px;
  padding: 0.5rem;
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.5);
  overflow: hidden;
  z-index: 1001;
}

.result-item {
  display: flex;
  gap: 1rem;
  padding: 0.75rem;
  border-radius: 8px;
  cursor: pointer;
  transition: background 0.2s;
}

.result-item:hover {
  background: rgba(255, 255, 255, 0.05);
}

.result-poster {
  width: 40px;
  height: 60px;
  object-fit: cover;
  border-radius: 4px;
}

.result-info {
  display: flex;
  flex-direction: column;
  justify-content: center;
  overflow: hidden;
}

.result-title {
  color: white;
  font-weight: 600;
  font-size: 0.9rem;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.result-genre {
  color: rgba(255, 255, 255, 0.5);
  font-size: 0.75rem;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
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

.user-profile-group {
  display: flex;
  align-items: center;
  gap: 1rem;
}

.logout-btn {
  background: transparent;
  border: none;
  color: rgba(255, 255, 255, 0.6);
  font-size: 1.2rem;
  cursor: pointer;
  transition: color 0.3s;
  padding: 0.5rem;
}

.logout-btn:hover {
  color: var(--primary);
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
