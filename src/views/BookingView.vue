<template>
  <div class="booking-view" v-if="session && movie">
    <div class="booking-container">
      <!-- Left: Seat Map -->
      <div class="seat-selection">
        <header class="selection-header">
          <router-link :to="'/movie/' + movie.id" class="back-link">
            <i class="fas fa-arrow-left"></i> Back
          </router-link>
          <div class="session-info">
            <h2>{{ movie.title }}</h2>
            <p>{{ session.hall }} • {{ session.time }}</p>
          </div>
        </header>

        <div class="screen-container">
          <div class="screen"></div>
          <p>SCREEN THIS WAY</p>
        </div>

        <div class="seats-grid">
          <div v-for="(row, rIndex) in seats" :key="rIndex" class="seat-row">
            <span class="row-label">{{ String.fromCharCode(65 + rIndex) }}</span>
            <div 
              v-for="(seat, cIndex) in row" 
              :key="cIndex"
              class="seat"
              :class="{ 
                'occupied': seat.status === 'occupied', 
                'selected': seat.status === 'selected',
                'vip': rIndex > 5
              }"
              @click="toggleSeat(rIndex, cIndex)"
            >
              <span class="seat-num">{{ cIndex + 1 }}</span>
            </div>
          </div>
        </div>

        <div class="legend">
          <div class="legend-item">
            <div class="seat available"></div>
            <span>Available</span>
          </div>
          <div class="legend-item">
            <div class="seat selected"></div>
            <span>Selected</span>
          </div>
          <div class="legend-item">
            <div class="seat occupied"></div>
            <span>Occupied</span>
          </div>
          <div class="legend-item">
            <div class="seat vip available"></div>
            <span>VIP ($20.00)</span>
          </div>
        </div>
      </div>

      <!-- Right: Summary -->
      <div class="booking-summary">
        <h3>Booking Summary</h3>
        <div class="summary-details">
          <div class="detail-row">
            <span>Movie</span>
            <span>{{ movie.title }}</span>
          </div>
          <div class="detail-row">
            <span>Cinema</span>
            <span>CinePass Central</span>
          </div>
          <div class="detail-row">
            <span>Date</span>
            <span>Today, 14 Nov</span>
          </div>
          <div class="detail-row">
            <span>Time</span>
            <span>{{ session.time }}</span>
          </div>
          <div class="detail-row">
            <span>Hall</span>
            <span>{{ session.hall }}</span>
          </div>
          
          <div class="divider"></div>
          
          <div class="selected-seats" v-if="selectedSeats.length > 0">
            <span>Selected Seats</span>
            <div class="seats-list">
              <span v-for="s in selectedSeats" :key="s.id" class="seat-tag">
                {{ s.id }}
              </span>
            </div>
          </div>

          <div class="divider"></div>

          <div class="price-breakdown">
            <div class="detail-row">
              <span>Tickets ({{ selectedSeats.length }})</span>
              <span>${{ totalPrice.toFixed(2) }}</span>
            </div>
            <div class="detail-row">
              <span>Booking Fee</span>
              <span>${{ bookingFee.toFixed(2) }}</span>
            </div>
            <div class="detail-row total">
              <span>Total</span>
              <span>${{ (totalPrice + bookingFee).toFixed(2) }}</span>
            </div>
          </div>

          <button 
            class="checkout-btn" 
            :disabled="selectedSeats.length === 0"
            @click="proceedToCheckout"
          >
            Proceed to Checkout
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import api from '../api';

export default {
  name: 'BookingView',
  data() {
    return {
      session: null,
      movie: null,
      seats: [], // 2D array
      rows: 8,
      cols: 10,
      bookingFee: 1.50
    }
  },
  async created() {
    const sessionId = this.$route.params.sessionId;
    try {
      const sessionRes = await api.get(`/movies/showtimes/${sessionId}`);
      this.session = sessionRes.data;
      if (this.session && this.session.movie) {
        // The Showtime entity in Java has the Movie object
        this.movie = this.session.movie;
        // The Movie object from Hibernate might have genre/cast as strings
        // But our MovieController converts them. 
        // Wait, if I get it from MovieController.getShowtimeById, the 'movie' field is the Entity.
        // Let's ensure consistency. 
        this.generateSeats();
      }
    } catch (err) {
      console.error('Error fetching booking session:', err);
    }
  },
  computed: {
    selectedSeats() {
      const selected = [];
      this.seats.forEach((row, rIndex) => {
        row.forEach((seat, cIndex) => {
          if (seat.status === 'selected') {
            const rowLabel = String.fromCharCode(65 + rIndex);
            selected.push({
              id: `${rowLabel}${cIndex + 1}`,
              price: this.getSeatPrice(rIndex)
            });
          }
        });
      });
      return selected;
    },
    totalPrice() {
      return this.selectedSeats.reduce((acc, seat) => acc + seat.price, 0);
    }
  },
  methods: {
    generateSeats() {
      // Create a grid
      for (let r = 0; r < this.rows; r++) {
        const row = [];
        for (let c = 0; c < this.cols; c++) {
          // Randomly occupy some seats
          const isOccupied = Math.random() < 0.2; // 20% occupied
          row.push({
            status: isOccupied ? 'occupied' : 'available'
          });
        }
        this.seats.push(row);
      }
    },
    getSeatPrice(rowIndex) {
      // VIP rows (last 2 rows) are more expensive
      if (rowIndex >= this.rows - 2) {
        return this.session.price + 5; // $5 extra for VIP
      }
      return this.session.price;
    },
    toggleSeat(rIndex, cIndex) {
      const seat = this.seats[rIndex][cIndex];
      if (seat.status === 'occupied') return;
      
      if (seat.status === 'selected') {
        seat.status = 'available';
      } else {
        // Limit to 8 seats
        if (this.selectedSeats.length >= 8) {
          alert("You can only book up to 8 tickets.");
          return;
        }
        seat.status = 'selected';
      }
    },
    proceedToCheckout() {
      // Check if user is logged in
      if (!localStorage.getItem('token')) {
        alert("Please log in to book tickets.");
        this.$router.push('/login');
        return;
      }

      const bookingDetails = {
        movie: this.movie,
        session: this.session,
        seats: this.selectedSeats,
        subtotal: this.totalPrice,
        bookingFee: this.bookingFee,
        total: this.totalPrice + this.bookingFee
      };
      
      localStorage.setItem('pendingBooking', JSON.stringify(bookingDetails));
      this.$router.push('/checkout');
    }
  }
}
</script>

<style scoped>
.booking-view {
  padding-top: 80px; /* Navbar height */
  min-height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
}

.booking-container {
  display: flex;
  width: 100%;
  max-width: 1200px;
  gap: 2rem;
  padding: 2rem;
}

.seat-selection {
  flex: 1;
  background: rgba(255, 255, 255, 0.05);
  border-radius: 20px;
  padding: 2rem;
  border: 1px solid rgba(255, 255, 255, 0.1);
}

.selection-header {
  display: flex;
  align-items: center;
  gap: 2rem;
  margin-bottom: 3rem;
  border-bottom: 1px solid rgba(255, 255, 255, 0.1);
  padding-bottom: 1.5rem;
}

.back-link {
  color: rgba(255, 255, 255, 0.7);
  text-decoration: none;
  font-size: 0.9rem;
  display: flex;
  align-items: center;
  gap: 0.5rem;
  transition: color 0.3s;
}

.back-link:hover {
  color: white;
}

.session-info h2 {
  font-size: 1.5rem;
  margin-bottom: 0.2rem;
}

.session-info p {
  color: rgba(255, 255, 255, 0.5);
  font-size: 0.9rem;
}

.screen-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-bottom: 3rem;
}

.screen {
  height: 60px;
  width: 80%;
  background: linear-gradient(to bottom, rgba(255, 255, 255, 0.2), transparent);
  border-top: 4px solid white;
  border-radius: 50% 50% 0 0 / 20px 20px 0 0;
  box-shadow: 0 -20px 40px rgba(255, 255, 255, 0.1);
  margin-bottom: 1rem;
}

.screen-container p {
  color: rgba(255, 255, 255, 0.3);
  font-size: 0.8rem;
  letter-spacing: 2px;
}

.seats-grid {
  display: flex;
  flex-direction: column;
  gap: 1rem;
  align-items: center;
  margin-bottom: 3rem;
}

.seat-row {
  display: flex;
  gap: 0.8rem;
  align-items: center;
}

.row-label {
  color: rgba(255, 255, 255, 0.3);
  font-size: 0.9rem;
  width: 20px;
}

.seat {
  width: 32px;
  height: 32px;
  background: rgba(255, 255, 255, 0.2);
  border-radius: 8px 8px 4px 4px;
  cursor: pointer;
  transition: all 0.2s;
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 0.7rem;
  color: transparent;
  position: relative;
}

.seat:hover:not(.occupied) {
  background: rgba(255, 255, 255, 0.5);
  transform: scale(1.1);
}

.seat.selected {
  background: var(--primary);
  color: white;
  box-shadow: 0 0 10px var(--primary);
}

.seat.occupied {
  background: rgba(255, 255, 255, 0.1);
  cursor: not-allowed;
  opacity: 0.5;
}

.seat.vip:not(.occupied):not(.selected) {
  border: 1px solid #eab308;
}

.legend {
  display: flex;
  justify-content: center;
  gap: 2rem;
  margin-top: 2rem;
}

.legend-item {
  display: flex;
  align-items: center;
  gap: 0.5rem;
  font-size: 0.9rem;
  color: rgba(255, 255, 255, 0.6);
}

/* Booking Summary */
.booking-summary {
  width: 350px;
  background: rgba(255, 255, 255, 0.95);
  border-radius: 20px;
  padding: 2rem;
  color: #1a1a1a; /* Dark text for contrast */
  height: fit-content;
  position: sticky;
  top: 100px;
}

.booking-summary h3 {
  font-size: 1.2rem;
  margin-bottom: 2rem;
  border-bottom: 1px solid rgba(0, 0, 0, 0.1);
  padding-bottom: 1rem;
}

.detail-row {
  display: flex;
  justify-content: space-between;
  margin-bottom: 1rem;
  font-size: 0.95rem;
}

.detail-row span:first-child {
  color: #666;
}

.detail-row span:last-child {
  font-weight: 600;
  text-align: right;
}

.divider {
  height: 1px;
  background: rgba(0, 0, 0, 0.1);
  margin: 1.5rem 0;
}

.seats-list {
  display: flex;
  flex-wrap: wrap;
  gap: 0.5rem;
  margin-top: 0.5rem;
}

.seat-tag {
  background: #f0f0f0;
  padding: 0.2rem 0.5rem;
  border-radius: 4px;
  font-size: 0.85rem;
  font-weight: 600;
}

.detail-row.total {
  font-size: 1.2rem;
  font-weight: 800;
  color: var(--primary);
}

.checkout-btn {
  width: 100%;
  padding: 1rem;
  background: var(--primary);
  color: white;
  border-radius: 12px;
  font-weight: 700;
  font-size: 1rem;
  margin-top: 1.5rem;
  transition: all 0.3s;
}

.checkout-btn:hover:not(:disabled) {
  background: #c40812;
  transform: translateY(-2px);
  box-shadow: 0 5px 15px rgba(229, 9, 20, 0.3);
}

.checkout-btn:disabled {
  background: #ccc;
  cursor: not-allowed;
}

@media (max-width: 900px) {
  .booking-container {
    flex-direction: column;
  }
  
  .booking-summary {
    width: 100%;
    position: static;
  }
}
</style>
