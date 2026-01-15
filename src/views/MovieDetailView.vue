<template>
  <div class="movie-detail-view" v-if="movie">
    <!-- Backdrop & Header -->
    <div class="backdrop" :style="{ backgroundImage: `linear-gradient(to bottom, rgba(0,0,0,0.2), #0f0f0f), url(${movie.backdrop})` }"></div>

    <div class="content">
      <div class="movie-header">
        <div class="poster-container">
          <img :src="movie.poster" :alt="movie.title" class="poster" />
        </div>
        <div class="info">
          <h1>{{ movie.title }}</h1>
          <div class="meta-tags">
            <span class="tag">{{ movie.rating }}</span>
            <span class="tag">{{ movie.duration }}</span>
            <span class="tag">{{ movie.imdbRating }} IMDB</span>
          </div>
          <p class="genres">{{ movie.genre.join(' • ') }}</p>
          <p class="release-date">Released: {{ formatDate(movie.releaseDate) }}</p>
          
          <div class="plot">
            <h3>Synopsis</h3>
            <p>{{ movie.description }}</p>
          </div>

          <div class="cast">
            <h3>Cast</h3>
            <div class="cast-list">
              <div v-for="actor in movie.cast" :key="actor.name" class="actor">
                <div class="actor-avatar">{{ getInitials(actor.name) }}</div>
                <div>
                  <div class="actor-name">{{ actor.name }}</div>
                  <div class="actor-role">{{ actor.role }}</div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>

      <!-- Showtimes Section -->
      <section class="showtimes-section">
        <h2>Select Showtime</h2>
        <div class="date-selector">
          <button class="date-btn active">
            <span class="day-name">Today</span>
            <span class="day-num">14</span>
          </button>
          <button class="date-btn">
            <span class="day-name">Thu</span>
            <span class="day-num">15</span>
          </button>
          <button class="date-btn">
            <span class="day-name">Fri</span>
            <span class="day-num">16</span>
          </button>
        </div>

        <div class="halls-list">
          <div v-if="movieShowtimes.length === 0" class="no-showtimes">
            No showtimes available for this movie today.
          </div>
          <div v-else class="showtimes-grid">
            <router-link 
              v-for="showtime in movieShowtimes" 
              :key="showtime.id"
              :to="'/booking/' + showtime.id"
              class="time-slot"
            >
              <span class="time">{{ showtime.time }}</span>
              <span class="hall">{{ showtime.hall }}</span>
              <span class="price">${{ showtime.price.toFixed(2) }}</span>
            </router-link>
          </div>
        </div>
      </section>
    </div>
  </div>
</template>

<script>
import { movies, showtimes } from '../model/mockData.js'

export default {
  name: 'MovieDetailView',
  data() {
    return {
      movie: null,
      movieShowtimes: []
    }
  },
  created() {
    const movieId = parseInt(this.$route.params.id);
    this.movie = movies.find(m => m.id === movieId);
    this.movieShowtimes = showtimes.filter(s => s.movieId === movieId);
  },
  methods: {
    formatDate(dateStr) {
      const options = { year: 'numeric', month: 'long', day: 'numeric' };
      return new Date(dateStr).toLocaleDateString('en-US', options);
    },
    getInitials(name) {
      return name.split(' ').map(n => n[0]).join('').slice(0, 2);
    }
  }
}
</script>

<style scoped>
.movie-detail-view {
  min-height: 100vh;
  position: relative;
  padding-bottom: 4rem;
}

.backdrop {
  height: 60vh;
  background-size: cover;
  background-position: center top;
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  z-index: 0;
  mask-image: linear-gradient(to bottom, black 50%, transparent 100%);
  -webkit-mask-image: linear-gradient(to bottom, black 50%, transparent 100%);
}

.content {
  position: relative;
  z-index: 10;
  max-width: 1200px;
  margin: 0 auto;
  padding: 15rem 2rem 2rem;
}

.movie-header {
  display: flex;
  gap: 3rem;
  margin-bottom: 4rem;
}

.poster-container {
  flex-shrink: 0;
  width: 300px;
  border-radius: 12px;
  box-shadow: 0 20px 40px rgba(0,0,0,0.6);
  overflow: hidden;
}

.poster {
  width: 100%;
  display: block;
}

.info {
  color: white;
  padding-top: 2rem;
}

.info h1 {
  font-size: 3rem;
  font-weight: 800;
  margin-bottom: 1rem;
  text-shadow: 0 2px 10px rgba(0,0,0,0.5);
}

.meta-tags {
  display: flex;
  gap: 0.8rem;
  margin-bottom: 1rem;
}

.tag {
  background: rgba(255, 255, 255, 0.15);
  backdrop-filter: blur(10px);
  padding: 0.2rem 0.6rem;
  border-radius: 4px;
  font-size: 0.85rem;
  font-weight: 600;
}

.genres {
  color: var(--primary);
  font-weight: 600;
  margin-bottom: 0.5rem;
}

.release-date {
  color: rgba(255, 255, 255, 0.6);
  font-size: 0.9rem;
  margin-bottom: 2rem;
}

.plot h3, .cast h3 {
  font-size: 1.2rem;
  margin-bottom: 0.8rem;
  color: white;
  border-bottom: 2px solid var(--primary);
  display: inline-block;
  padding-bottom: 0.2rem;
}

.plot p {
  line-height: 1.7;
  color: rgba(255, 255, 255, 0.8);
  margin-bottom: 2rem;
  max-width: 700px;
}

.cast-list {
  display: flex;
  gap: 1.5rem;
  flex-wrap: wrap;
}

.actor {
  display: flex;
  align-items: center;
  gap: 0.8rem;
  background: rgba(255, 255, 255, 0.05);
  padding: 0.5rem 1rem;
  border-radius: 8px;
}

.actor-avatar {
  width: 32px;
  height: 32px;
  background: var(--primary);
  border-radius: 50%;
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 0.8rem;
  font-weight: 700;
  color: white;
}

.actor-name {
  font-weight: 600;
  font-size: 0.9rem;
}

.actor-role {
  font-size: 0.8rem;
  color: rgba(255, 255, 255, 0.5);
}

/* Showtimes */
.showtimes-section {
  background: rgba(255, 255, 255, 0.03);
  border-radius: 16px;
  padding: 2rem;
  border: 1px solid rgba(255, 255, 255, 0.05);
}

.showtimes-section h2 {
  margin-bottom: 1.5rem;
  font-size: 1.5rem;
}

.date-selector {
  display: flex;
  gap: 1rem;
  margin-bottom: 2rem;
  border-bottom: 1px solid rgba(255, 255, 255, 0.1);
  padding-bottom: 1rem;
}

.date-btn {
  background: transparent;
  border: none;
  color: rgba(255, 255, 255, 0.5);
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 0.5rem 1rem;
  border-radius: 8px;
  transition: all 0.3s;
}

.date-btn:hover, .date-btn.active {
  background: var(--primary);
  color: white;
}

.day-num {
  font-size: 1.2rem;
  font-weight: 700;
}

.day-name {
  font-size: 0.8rem;
  text-transform: uppercase;
}

.showtimes-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(140px, 1fr));
  gap: 1rem;
}

.time-slot {
  background: rgba(255, 255, 255, 0.05);
  border: 1px solid rgba(255, 255, 255, 0.1);
  padding: 1rem;
  border-radius: 8px;
  text-align: center;
  text-decoration: none;
  color: white;
  transition: all 0.3s;
  display: flex;
  flex-direction: column;
  gap: 0.3rem;
}

.time-slot:hover {
  background: white;
  color: black;
  transform: translateY(-2px);
}

.time-slot .time {
  font-size: 1.1rem;
  font-weight: 700;
}

.time-slot .hall {
  font-size: 0.8rem;
  opacity: 0.7;
}

.time-slot .price {
  font-size: 0.9rem;
  color: var(--primary);
  font-weight: 600;
}

.time-slot:hover .price {
  color: #c40812;
}

.no-showtimes {
  padding: 2rem;
  text-align: center;
  color: rgba(255, 255, 255, 0.5);
  font-style: italic;
}

@media (max-width: 900px) {
  .movie-header {
    flex-direction: column;
    align-items: center;
    text-align: center;
  }

  .meta-tags, .cast-list {
    justify-content: center;
  }
}
</style>
