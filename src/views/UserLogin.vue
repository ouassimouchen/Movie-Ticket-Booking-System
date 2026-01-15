<template>
  <div class="login-container">
    <div class="back-link-wrapper">
      <router-link to="/" class="back-link">
        <i class="fas fa-arrow-left"></i> Back to Home
      </router-link>
    </div>
    <div class="background-overlay"></div>
    <div class="login-card">
      <div class="logo">
        <span class="logo-text">CINE<span>PASS</span></span>
      </div>
      <h2>Internal User Login</h2>
      <p>Sign in to access your dashboard</p>
      
      <form @submit.prevent="handleLogin">
        <div class="input-group">
          <label>Email</label>
          <input type="email" v-model="email" placeholder="john@example.com" required />
        </div>
        <div class="input-group">
          <label>Password</label>
          <input type="password" v-model="password" placeholder="Any password works" required />
        </div>
        <div class="options">
          <label class="remember">
            <input type="checkbox" /> Remember me
          </label>
          <a href="#">Forgot password?</a>
        </div>
        <button type="submit" class="login-btn">
          <span v-if="loading">Logging in...</span>
          <span v-else>Sign In</span>
        </button>
      </form>
      
      <div class="footer">
        Don't have an account? <a href="#">Sign up now</a>
      </div>
    </div>
  </div>
</template>

<script>
import { currentUser } from '../model/mockData.js'

export default {
  name: 'UserLogin',
  data() {
    return {
      email: '',
      password: '',
      loading: false
    }
  },
  methods: {
    handleLogin() {
      this.loading = true;
      // Simulate API delay
      setTimeout(() => {
        currentUser.name = "John Doe"; // Reset name just in case
        currentUser.email = this.email;
        currentUser.isLoggedIn = true;
        this.loading = false;
        this.$router.push('/');
      }, 1000);
    }
  }
}
</script>

<style scoped>
.login-container {
  height: 100vh;
  width: 100vw;
  display: flex;
  align-items: center;
  justify-content: center;
  background: url('https://images.unsplash.com/photo-1536440136628-849c177e76a1?q=80&w=2025&auto=format&fit=crop') no-repeat center center/cover;
  position: relative;
}

.back-link-wrapper {
  position: absolute;
  top: 2rem;
  left: 2rem;
  z-index: 20;
}

.back-link {
  color: white;
  text-decoration: none;
  font-weight: 600;
  display: flex;
  align-items: center;
  gap: 0.5rem;
  padding: 0.5rem 1rem;
  background: rgba(0,0,0,0.5);
  border-radius: 20px;
  backdrop-filter: blur(5px);
  transition: all 0.3s;
}

.back-link:hover {
  background: var(--primary);
}

.background-overlay {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: radial-gradient(circle, rgba(0,0,0,0.4) 0%, rgba(0,0,0,0.8) 100%);
  backdrop-filter: blur(8px);
}

.login-card {
  position: relative;
  z-index: 10;
  background: rgba(255, 255, 255, 0.05);
  backdrop-filter: blur(20px);
  border: 1px solid rgba(255, 255, 255, 0.1);
  padding: 3rem;
  border-radius: 24px;
  width: 100%;
  max-width: 450px;
  box-shadow: 0 25px 50px -12px rgba(0, 0, 0, 0.5);
  text-align: center;
}

.logo {
  margin-bottom: 2rem;
}

.logo-text {
  font-size: 2.5rem;
  font-weight: 800;
  letter-spacing: -1px;
}

.logo-text span {
  color: #e50914;
}

h2 {
  font-size: 1.75rem;
  margin-bottom: 0.5rem;
  font-weight: 700;
}

p {
  color: rgba(255, 255, 255, 0.6);
  margin-bottom: 2.5rem;
  font-size: 0.9rem;
}

.input-group {
  text-align: left;
  margin-bottom: 1.5rem;
}

.input-group label {
  display: block;
  font-size: 0.85rem;
  font-weight: 600;
  margin-bottom: 0.5rem;
  color: rgba(255, 255, 255, 0.8);
}

.input-group input {
  width: 100%;
  padding: 1rem 1.25rem;
  background: rgba(255, 255, 255, 0.05);
  border: 1px solid rgba(255, 255, 255, 0.1);
  border-radius: 12px;
  color: white;
  transition: all 0.3s ease;
}

.input-group input:focus {
  background: rgba(255, 255, 255, 0.1);
  border-color: #e50914;
  box-shadow: 0 0 0 4px rgba(229, 9, 20, 0.2);
}

.options {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 2rem;
  font-size: 0.85rem;
}

.remember {
  display: flex;
  align-items: center;
  gap: 0.5rem;
  color: rgba(255, 255, 255, 0.7);
  cursor: pointer;
}

.options a {
  color: #e50914;
  text-decoration: none;
  font-weight: 600;
}

.login-btn {
  width: 100%;
  padding: 1rem;
  background: #e50914;
  color: white;
  border-radius: 12px;
  font-size: 1rem;
  font-weight: 700;
  transition: all 0.3s ease;
  box-shadow: 0 4px 6px -1px rgba(229, 9, 20, 0.4);
}

.login-btn:hover {
  background: #f40a16;
  transform: translateY(-2px);
  box-shadow: 0 10px 15px -3px rgba(229, 9, 20, 0.5);
}

.footer {
  margin-top: 2rem;
  font-size: 0.85rem;
  color: rgba(255, 255, 255, 0.6);
}

.footer a {
  color: white;
  text-decoration: none;
  font-weight: 700;
}

.footer a:hover {
  color: #e50914;
}
</style>
