<template>
  <div class="home-view">
    <!-- Hero Section -->
    <section class="hero" v-if="featuredMovie" :style="{ backgroundImage: `linear-gradient(to bottom, rgba(0,0,0,0.3), #0f0f0f), url(${featuredMovie.backdrop})` }">
      <div class="hero-content">
        <span class="tag">Now Trending</span>
        <h1>{{ featuredMovie.title }}</h1>
        <div class="meta">
          <span class="rating">{{ featuredMovie.rating }}</span>
          <span>{{ featuredMovie.duration }}</span>
          <span>{{ featuredMovie.genre.join(', ') }}</span>
        </div>
        <p class="description">{{ featuredMovie.description }}</p>
        <div class="actions">
          <router-link :to="'/movie/' + featuredMovie.id" class="btn btn-primary">
            <i class="fas fa-ticket-alt"></i> Book Tickets
          </router-link>
          <button class="btn btn-secondary">
            <i class="fas fa-play"></i> Watch Trailer
          </button>
        </div>
      </div>
    </section>

    <!-- Movie Grid -->
    <section class="movies-section">
      <div class="section-header">
        <h2>Now Showing</h2>
        <div class="filters">
          <button class="active">All</button>
          <button>Action</button>
          <button>Sci-Fi</button>
          <button>Drama</button>
        </div>
      </div>
      
      <div class="movie-grid">
        <div v-for="movie in movies" :key="movie.id" class="movie-card">
          <router-link :to="'/movie/' + movie.id" class="card-link">
            <div class="poster-wrapper">
              <img :src="movie.poster" :alt="movie.title" loading="lazy" />
              <div class="overlay">
                <button class="book-btn">Book Now</button>
              </div>
            </div>
            <div class="card-info">
              <h3>{{ movie.title }}</h3>
              <div class="card-meta">
                <span>{{ movie.genre[0] }}</span>
                <span class="star"><i class="fas fa-star"></i> {{ movie.imdbRating }}</span>
              </div>
            </div>
          </router-link>
        </div>
      </div>
    </section>
  </div>
</template>

<script>
import { movies } from '../model/mockData.js'

export default {
  name: 'HomeView',
  data() {
    return {
      movies: movies
    }
  },
  computed: {
    featuredMovie() {
      return this.movies[0]; // Just picking the first one for now
    }
  }
}
</script>

<style scoped>
.hero {
  height: 85vh;
  background-size: cover;
  background-position: center;
  position: relative;
  display: flex;
  align-items: center;
  padding: 0 4rem;
  margin-top: -80px; /* Offset navbar */
}

.hero-content {
  max-width: 600px;
  z-index: 10;
  padding-top: 60px;
}

.tag {
  background: var(--primary);
  color: white;
  padding: 0.3rem 0.8rem;
  border-radius: 4px;
  font-size: 0.8rem;
  font-weight: 600;
  text-transform: uppercase;
  margin-bottom: 1rem;
  display: inline-block;
}

.hero h1 {
  font-size: 4rem;
  line-height: 1.1;
  margin-bottom: 1rem;
  font-weight: 800;
}

.meta {
  display: flex;
  gap: 1.5rem;
  margin-bottom: 1.5rem;
  font-size: 1rem;
  color: rgba(255, 255, 255, 0.8);
}

.rating {
  border: 1px solid rgba(255, 255, 255, 0.5);
  padding: 0.1rem 0.5rem;
  border-radius: 4px;
}

.description {
  font-size: 1.1rem;
  color: rgba(255, 255, 255, 0.7);
  margin-bottom: 2rem;
  line-height: 1.6;
}

.actions {
  display: flex;
  gap: 1rem;
}

.btn {
  padding: 0.8rem 2rem;
  border-radius: 8px;
  font-weight: 600;
  font-size: 1rem;
  transition: all 0.3s ease;
  display: flex;
  align-items: center;
  gap: 0.5rem;
  text-decoration: none;
}

.btn-primary {
  background: var(--primary);
  color: white;
}

.btn-primary:hover {
  background: #c40812;
  transform: translateY(-2px);
}

.btn-secondary {
  background: rgba(255, 255, 255, 0.1);
  color: white;
  backdrop-filter: blur(10px);
}

.btn-secondary:hover {
  background: rgba(255, 255, 255, 0.2);
}

/* Movies Section */
.movies-section {
  padding: 4rem;
}

.section-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 2rem;
}

.section-header h2 {
  font-size: 1.8rem;
  font-weight: 700;
}

.filters {
  display: flex;
  gap: 1rem;
}

.filters button {
  background: transparent;
  color: rgba(255, 255, 255, 0.5);
  padding: 0.5rem 1rem;
  border-radius: 20px;
  border: 1px solid rgba(255, 255, 255, 0.1);
  transition: all 0.3s;
}

.filters button:hover, .filters button.active {
  background: rgba(255, 255, 255, 0.1);
  color: white;
  border-color: white;
}

.movie-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(200px, 1fr));
  gap: 2rem;
}

.movie-card {
  transition: transform 0.3s ease;
}

.movie-card:hover {
  transform: translateY(-10px);
}

.card-link {
  text-decoration: none;
  color: inherit;
}

.poster-wrapper {
  position: relative;
  border-radius: 12px;
  overflow: hidden;
  margin-bottom: 1rem;
  aspect-ratio: 2/3;
}

.poster-wrapper img {
  width: 100%;
  height: 100%;
  object-fit: cover;
  transition: transform 0.5s ease;
}

.movie-card:hover .poster-wrapper img {
  transform: scale(1.05);
}

.overlay {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  opacity: 0;
  transition: opacity 0.3s ease;
}

.movie-card:hover .overlay {
  opacity: 1;
}

.book-btn {
  background: var(--primary);
  color: white;
  padding: 0.8rem 1.5rem;
  border-radius: 25px;
  font-weight: 600;
  transform: translateY(20px);
  transition: transform 0.3s ease;
}

.movie-card:hover .book-btn {
  transform: translateY(0);
}

.card-info h3 {
  font-size: 1.1rem;
  margin-bottom: 0.3rem;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.card-meta {
  display: flex;
  justify-content: space-between;
  font-size: 0.9rem;
  color: rgba(255, 255, 255, 0.5);
}

.star {
  color: #eab308;
}

@media (max-width: 768px) {
  .hero {
    padding: 0 1.5rem;
    height: 70vh;
  }
  
  .hero h1 {
    font-size: 2.5rem;
  }
  
  .movies-section {
    padding: 2rem 1.5rem;
  }
  
  .movie-grid {
    grid-template-columns: repeat(2, 1fr);
    gap: 1rem;
  }
}
</style>
