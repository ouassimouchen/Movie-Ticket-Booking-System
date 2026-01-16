<template>
  <div class="user-dashboard">
    <div class="dashboard-container">
      <!-- Sidebar -->
      <aside class="dashboard-sidebar">
        <div class="profile-header">
          <img src="https://ui-avatars.com/api/?name=John+Doe&background=e50914&color=fff" alt="User" class="avatar" />
          <h3>{{ user.name }}</h3>
          <p>{{ user.email }}</p>
        </div>
        
        <nav class="dashboard-nav">
          <a href="#" class="active"><i class="fas fa-ticket-alt"></i> My Bookings</a>
          <a href="#"><i class="fas fa-heart"></i> Watchlist</a>
          <a href="#"><i class="fas fa-cog"></i> Settings</a>
          <a href="#" class="logout" @click.prevent="handleLogout"><i class="fas fa-sign-out-alt"></i> Logout</a>
        </nav>
      </aside>

      <!-- Main Content -->
      <main class="dashboard-content">
        <header class="content-header">
          <h1>My Bookings</h1>
          <div class="tabs">
            <button class="tab active">Upcoming</button>
            <button class="tab">Past</button>
          </div>
        </header>

        <div class="bookings-list">
          <div v-if="bookings.length === 0" class="empty-state">
            <i class="fas fa-ticket-alt"></i>
            <p>You haven't booked any tickets yet.</p>
            <router-link to="/" class="btn-book">Book a Movie</router-link>
          </div>

          <div v-else class="booking-card" v-for="booking in bookings" :key="booking.id">
            <div class="card-poster">
              <img :src="booking.movie.poster" alt="Poster">
            </div>
            
            <div class="card-details">
              <div class="header">
                <h3>{{ booking.movie.title }}</h3>
                <span class="status" :class="booking.status.toLowerCase()">{{ booking.status }}</span>
              </div>
              
              <div class="info-grid">
                <div class="info-item">
                  <span class="label">Date</span>
                  <span class="value">{{ formatDate(booking.booking_date) }}</span>
                </div>
                <div class="info-item">
                  <span class="label">Time</span>
                  <span class="value">{{ booking.session.time }}</span>
                </div>
                <div class="info-item">
                  <span class="label">Hall</span>
                  <span class="value">{{ booking.session.hall }}</span>
                </div>
                <div class="info-item">
                  <span class="label">Seats</span>
                  <span class="value">{{ booking.seats.map(s => s.id).join(', ') }}</span>
                </div>
              </div>

              <div class="card-footer">
                <div class="booking-id">ID: {{ booking.id }}</div>
                <div class="total-price">${{ booking.total.toFixed(2) }}</div>
              </div>
            </div>
            
            <div class="card-actions">
              <button class="btn-qr"><i class="fas fa-qrcode"></i></button>
            </div>
          </div>
        </div>
      </main>
    </div>
  </div>
</template>

<script>
import api from '../api';

export default {
  name: 'UserDashboard',
  data() {
    return {
      user: JSON.parse(localStorage.getItem('user') || '{"name": "Guest", "email": ""}'),
      bookings: []
    }
  },
  async created() {
    if (!localStorage.getItem('token')) {
      this.$router.push('/login');
      return;
    }
    
    try {
      const response = await api.get('/bookings');
      this.bookings = response.data;
    } catch (err) {
      console.error('Error fetching bookings:', err);
      if (err.response?.status === 401 || err.response?.status === 403) {
        localStorage.removeItem('token');
        localStorage.removeItem('user');
        this.$router.push('/login');
      }
    }
  },
  methods: {
    formatDate(dateStr) {
      const options = { year: 'numeric', month: 'short', day: 'numeric' };
      return new Date(dateStr).toLocaleDateString('en-US', options);
    },
    handleLogout() {
      localStorage.removeItem('token');
      localStorage.removeItem('user');
      this.$router.push('/');
    }
  }
}
</script>

<style scoped>
.user-dashboard {
  padding-top: 80px;
  min-height: 100vh;
  padding-bottom: 4rem;
}

.dashboard-container {
  max-width: 1200px;
  margin: 0 auto;
  display: flex;
  gap: 3rem;
  padding: 2rem;
}

/* Sidebar */
.dashboard-sidebar {
  width: 280px;
  flex-shrink: 0;
  background: rgba(255, 255, 255, 0.05);
  border-radius: 16px;
  padding: 2rem;
  height: fit-content;
  border: 1px solid rgba(255, 255, 255, 0.1);
}

.profile-header {
  text-align: center;
  margin-bottom: 2rem;
  padding-bottom: 2rem;
  border-bottom: 1px solid rgba(255, 255, 255, 0.1);
}

.avatar {
  width: 100px;
  height: 100px;
  border-radius: 50%;
  margin-bottom: 1rem;
  border: 3px solid var(--primary);
}

.profile-header h3 {
  font-size: 1.2rem;
  margin-bottom: 0.2rem;
}

.profile-header p {
  color: rgba(255, 255, 255, 0.5);
  font-size: 0.9rem;
}

.dashboard-nav {
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
}

.dashboard-nav a {
  display: flex;
  align-items: center;
  gap: 1rem;
  padding: 1rem;
  color: rgba(255, 255, 255, 0.7);
  text-decoration: none;
  border-radius: 8px;
  transition: all 0.3s;
}

.dashboard-nav a:hover, .dashboard-nav a.active {
  background: rgba(255, 255, 255, 0.1);
  color: white;
}

.dashboard-nav a.active {
  border-left: 3px solid var(--primary);
}

.dashboard-nav a.logout {
  color: #ef4444;
  margin-top: 1rem;
}

.dashboard-nav a.logout:hover {
  background: rgba(239, 68, 68, 0.1);
}

/* Content */
.dashboard-content {
  flex: 1;
}

.content-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 2rem;
}

.tabs {
  display: flex;
  gap: 1rem;
  background: rgba(255, 255, 255, 0.05);
  padding: 0.3rem;
  border-radius: 8px;
}

.tab {
  padding: 0.5rem 1.5rem;
  background: transparent;
  color: rgba(255, 255, 255, 0.6);
  border-radius: 6px;
  font-size: 0.9rem;
}

.tab.active {
  background: rgba(255, 255, 255, 0.1);
  color: white;
}

/* Bookings List */
.bookings-list {
  display: flex;
  flex-direction: column;
  gap: 1.5rem;
}

.empty-state {
  text-align: center;
  padding: 4rem;
  background: rgba(255, 255, 255, 0.03);
  border-radius: 16px;
  color: rgba(255, 255, 255, 0.5);
}

.empty-state i {
  font-size: 3rem;
  margin-bottom: 1rem;
  opacity: 0.3;
}

.btn-book {
  display: inline-block;
  margin-top: 1.5rem;
  background: var(--primary);
  color: white;
  padding: 0.8rem 2rem;
  border-radius: 8px;
  text-decoration: none;
  font-weight: 600;
}

.booking-card {
  display: flex;
  background: rgba(255, 255, 255, 0.05);
  border-radius: 12px;
  overflow: hidden;
  border: 1px solid rgba(255, 255, 255, 0.1);
  transition: transform 0.3s;
}

.booking-card:hover {
  transform: translateY(-2px);
  background: rgba(255, 255, 255, 0.08);
}

.card-poster {
  width: 120px;
}

.card-poster img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.card-details {
  flex: 1;
  padding: 1.5rem;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
}

.card-details .header {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  margin-bottom: 1rem;
}

.status {
  font-size: 0.8rem;
  padding: 0.2rem 0.6rem;
  border-radius: 4px;
  font-weight: 600;
}

.status.confirmed {
  background: rgba(34, 197, 94, 0.2);
  color: #22c55e;
}

.info-grid {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 1rem;
  margin-bottom: 1rem;
}

.info-item {
  display: flex;
  flex-direction: column;
  gap: 0.2rem;
}

.info-item .label {
  font-size: 0.8rem;
  color: rgba(255, 255, 255, 0.5);
}

.info-item .value {
  font-size: 0.95rem;
  font-weight: 500;
}

.card-footer {
  display: flex;
  justify-content: space-between;
  align-items: center;
  border-top: 1px solid rgba(255, 255, 255, 0.1);
  padding-top: 1rem;
  font-size: 0.9rem;
  color: rgba(255, 255, 255, 0.5);
}

.total-price {
  color: white;
  font-weight: 700;
  font-size: 1.1rem;
}

.card-actions {
  display: flex;
  align-items: center;
  padding: 1rem;
  border-left: 1px solid rgba(255, 255, 255, 0.1);
}

.btn-qr {
  background: white;
  color: black;
  width: 50px;
  height: 50px;
  border-radius: 8px;
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 1.5rem;
  transition: all 0.3s;
}

.btn-qr:hover {
  transform: scale(1.1);
}

@media (max-width: 768px) {
  .dashboard-container {
    flex-direction: column;
    padding: 1rem;
  }
  
  .dashboard-sidebar {
    width: 100%;
  }

  .booking-card {
    flex-direction: column;
  }

  .card-poster {
    width: 100%;
    height: 150px;
  }

  .card-actions {
    border-left: none;
    border-top: 1px solid rgba(255, 255, 255, 0.1);
    justify-content: center;
  }
}
</style>
