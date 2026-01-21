<template>
  <div class="checkout-view">
    <div class="checkout-container" v-if="booking">
      <div class="checkout-header">
        <h1>Checkout</h1>
        <div class="timer">
          Time remaining: <span>{{ formatTime(timer) }}</span>
        </div>
      </div>

      <div class="content-grid">
        <!-- Payment Section -->
        <div class="payment-section">
          <h2>Payment Details</h2>
          <div class="payment-methods">
            <button class="method active">
              <i class="fas fa-credit-card"></i> Credit Card
            </button>
            <button class="method">
              <i class="fab fa-paypal"></i> PayPal
            </button>
            <button class="method">
              <i class="fab fa-apple"></i> Apple Pay
            </button>
          </div>

          <form @submit.prevent="processPayment" class="payment-form">
            <div class="form-group">
              <label>Cardholder Name</label>
              <input type="text" placeholder="John Doe" required>
            </div>
            
            <div class="form-group">
              <label>Card Number</label>
              <div class="input-with-icon">
                <i class="fas fa-credit-card"></i>
                <input type="text" placeholder="0000 0000 0000 0000" maxlength="19" required>
              </div>
            </div>

            <div class="form-row">
              <div class="form-group">
                <label>Expiry Date</label>
                <input type="text" placeholder="MM/YY" maxlength="5" required>
              </div>
              <div class="form-group">
                <label>CVV</label>
                <input type="password" placeholder="123" maxlength="3" required>
              </div>
            </div>

            <button type="submit" class="pay-btn" :disabled="processing">
              <span v-if="!processing">Pay ${{ booking.total.toFixed(2) }}</span>
              <span v-else><i class="fas fa-spinner fa-spin"></i> Processing...</span>
            </button>
          </form>
        </div>

        <!-- Order Summary -->
        <div class="order-summary">
          <h3>Order Summary</h3>
          <div class="movie-card-mini">
            <img :src="booking.movie.poster" alt="Poster">
            <div>
              <h4>{{ booking.movie.title }}</h4>
              <p>{{ booking.session.hall }}</p>
              <p>{{ booking.session.time }}</p>
            </div>
          </div>

          <div class="items-list">
            <div v-for="seat in booking.seats" :key="seat.id" class="item-row">
              <span>Seat {{ seat.id }}</span>
              <span>${{ seat.price.toFixed(2) }}</span>
            </div>
          </div>

          <div class="totals">
            <div class="row">
              <span>Subtotal</span>
              <span>${{ booking.subtotal.toFixed(2) }}</span>
            </div>
            <div class="row">
              <span>Booking Fee</span>
              <span>${{ booking.bookingFee.toFixed(2) }}</span>
            </div>
            <div class="row total">
              <span>Total</span>
              <span>${{ booking.total.toFixed(2) }}</span>
            </div>
          </div>
        </div>
      </div>
    </div>
    
    <div v-else class="empty-state">
      <h2>No pending booking found</h2>
      <router-link to="/" class="btn-home">Back to Home</router-link>
    </div>
  </div>
</template>

<script>
import api from '../api';

export default {
  name: 'CheckoutView',
  data() {
    return {
      booking: JSON.parse(localStorage.getItem('pendingBooking')),
      timer: 300, // 5 minutes
      processing: false
    }
  },
  async created() {
    // Redirect if no booking found
    if (!this.booking) {
      this.$router.push('/');
    }
  },
  mounted() {
    if (this.booking) {
      this.countdown = setInterval(() => {
        if (this.timer > 0) this.timer--;
      }, 1000);
    }
  },
  beforeUnmount() {
    clearInterval(this.countdown);
  },
  methods: {
    formatTime(seconds) {
      const m = Math.floor(seconds / 60);
      const s = seconds % 60;
      return `${m}:${s < 10 ? '0' : ''}${s}`;
    },
    async processPayment() {
      this.processing = true;
      try {
        // Simulate payment delay
        await new Promise(resolve => setTimeout(resolve, 2000));
        await this.completeBooking();
      } catch (err) {
        alert('Payment failed: ' + (err.response?.data?.message || err.message));
        this.processing = false;
      }
    },
    async completeBooking() {
      // Create detailed booking record
      const bookingId = 'B-' + Math.floor(Math.random() * 100000);
      
      const bookingData = {
        id: bookingId,
        movie_id: this.booking.movie.id,
        movie_title: this.booking.movie.title,
        movie_poster: this.booking.movie.poster,
        session_hall: this.booking.session.hall,
        session_time: this.booking.session.time,
        seats: this.booking.seats,
        total: this.booking.total
      };

      try {
        await api.post('/bookings', bookingData);
        
        // Clear temp booking
        localStorage.removeItem('pendingBooking');

        // Navigate to Dashboard
        this.$router.push('/my-dashboard');
      } catch (err) {
        console.error('Error saving booking:', err);
        throw err;
      }
    }
  }
}
</script>

<style scoped>
.checkout-view {
  padding-top: 80px;
  min-height: 100vh;
  display: flex;
  justify-content: center;
  padding-bottom: 4rem;
}

.checkout-container {
  width: 100%;
  max-width: 1000px;
  padding: 2rem;
}

.checkout-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 3rem;
}

.timer span {
  color: var(--primary);
  font-weight: 700;
  font-family: monospace;
  font-size: 1.2rem;
}

.content-grid {
  display: grid;
  grid-template-columns: 1.5fr 1fr;
  gap: 3rem;
}

.payment-section {
  background: rgba(255, 255, 255, 0.05);
  padding: 2rem;
  border-radius: 16px;
  border: 1px solid rgba(255, 255, 255, 0.1);
}

.payment-section h2 {
  margin-bottom: 2rem;
}

.payment-methods {
  display: flex;
  gap: 1rem;
  margin-bottom: 2rem;
}

.method {
  flex: 1;
  background: rgba(255, 255, 255, 0.05);
  border: 1px solid rgba(255, 255, 255, 0.1);
  padding: 1rem;
  border-radius: 8px;
  color: white;
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 0.5rem;
  transition: all 0.3s;
}

.method:hover, .method.active {
  background: rgba(229, 9, 20, 0.1);
  border-color: var(--primary);
  color: var(--primary);
}

.form-group {
  margin-bottom: 1.5rem;
}

.form-group label {
  display: block;
  font-size: 0.9rem;
  color: rgba(255, 255, 255, 0.7);
  margin-bottom: 0.5rem;
}

.form-group input {
  width: 100%;
  background: rgba(0, 0, 0, 0.3);
  border: 1px solid rgba(255, 255, 255, 0.2);
  padding: 0.8rem 1rem;
  border-radius: 8px;
  color: white;
  font-size: 1rem;
}

.form-group input:focus {
  border-color: var(--primary);
}

.input-with-icon {
  position: relative;
}

.input-with-icon i {
  position: absolute;
  left: 1rem;
  top: 50%;
  transform: translateY(-50%);
  color: rgba(255, 255, 255, 0.5);
}

.input-with-icon input {
  padding-left: 3rem;
}

.form-row {
  display: flex;
  gap: 1rem;
}

.pay-btn {
  width: 100%;
  padding: 1rem;
  background: var(--primary);
  color: white;
  border-radius: 8px;
  font-weight: 700;
  font-size: 1.1rem;
  margin-top: 1rem;
  transition: all 0.3s;
}

.pay-btn:hover:not(:disabled) {
  background: #c40812;
  box-shadow: 0 5px 20px rgba(229, 9, 20, 0.3);
}

.pay-btn:disabled {
  opacity: 0.7;
  cursor: wait;
}

/* Order Summary */
.order-summary {
  background: white;
  color: #1a1a1a;
  padding: 2rem;
  border-radius: 16px;
  height: fit-content;
}

.movie-card-mini {
  display: flex;
  gap: 1rem;
  margin-bottom: 2rem;
  padding-bottom: 1.5rem;
  border-bottom: 1px solid rgba(0,0,0,0.1);
}

.movie-card-mini img {
  width: 70px;
  border-radius: 6px;
}

.movie-card-mini h4 {
  font-size: 1.1rem;
  margin-bottom: 0.3rem;
}

.movie-card-mini p {
  font-size: 0.9rem;
  color: #666;
}

.items-list {
  margin-bottom: 2rem;
}

.item-row {
  display: flex;
  justify-content: space-between;
  margin-bottom: 0.8rem;
  font-size: 0.95rem;
  color: #555;
}

.totals .row {
  display: flex;
  justify-content: space-between;
  margin-bottom: 0.8rem;
  font-size: 1rem;
}

.totals .row.total {
  font-size: 1.3rem;
  font-weight: 800;
  color: var(--primary);
  margin-top: 1rem;
  padding-top: 1rem;
  border-top: 2px dashed rgba(0,0,0,0.1);
}

.empty-state {
  text-align: center;
  padding-top: 5rem;
}

.btn-home {
  display: inline-block;
  margin-top: 1rem;
  color: var(--primary);
  text-decoration: none;
}

@media (max-width: 768px) {
  .content-grid {
    grid-template-columns: 1fr;
  }
}
</style>
