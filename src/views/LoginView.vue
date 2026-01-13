<template>
  <div class="login-container">
    <div class="background-overlay"></div>
    <div class="login-card">
      <div class="logo">
        <span class="logo-text">CINE<span>PASS</span></span>
      </div>
      <h2>Welcome Back</h2>
      <p>Log in to book your next cinematic experience</p>
      
      <form @submit.prevent="handleLogin">
        <div class="input-group">
          <label>Email or Username</label>
          <input type="text" v-model="email" placeholder="Enter your email" required />
        </div>
        <div class="input-group">
          <label>Password</label>
          <input type="password" v-model="password" placeholder="••••••••" required />
        </div>
        <div class="options">
          <label class="remember">
            <input type="checkbox" /> Remember me
          </label>
          <a href="#">Forgot password?</a>
        </div>
        <button type="submit" class="login-btn">Sign In</button>
      </form>
      
      <div class="footer">
        New to CinePass? <a href="#">Create an account</a>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'LoginView',
  data() {
    return {
      email: '',
      password: ''
    }
  },
  methods: {
    handleLogin() {
      // Simulate login and redirect to main page
      console.log('Logging in...', this.email);
      this.$router.push('/main');
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
  background: url('https://images.unsplash.com/photo-1489599849927-2ee91cede3ba?q=80&w=2070&auto=format&fit=crop') no-repeat center center/cover;
  position: relative;
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
