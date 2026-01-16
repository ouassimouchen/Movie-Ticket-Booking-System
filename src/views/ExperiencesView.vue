<template>
  <div class="experiences-view">
    <div class="hero-section">
      <div class="hero-overlay"></div>
      <div class="hero-content">
        <span class="eyebrow">Level Up Your Movie Night</span>
        <h1>Beyond the <span>Screen</span></h1>
        <p>From crystal-clear IMAX to immersive 4DX, discover a new dimension of cinema.</p>
      </div>
    </div>

    <div class="experience-list">
      <section v-for="(exp, index) in experiences" :key="exp.id" 
               class="experience-section" :class="{ 'reverse': index % 2 !== 0 }">
        <div class="experience-image">
          <img :src="exp.image" :alt="exp.title" />
          <div class="experience-badge">{{ exp.badge }}</div>
        </div>
        <div class="experience-content">
          <div class="accent-line"></div>
          <h2>{{ exp.title }}</h2>
          <p class="description">{{ exp.description }}</p>
          
          <div class="features">
            <div v-for="feature in exp.features" :key="feature" class="feature-tag">
              <i class="fas fa-check"></i> {{ feature }}
            </div>
          </div>
          
          <div class="price-hint">Tickets starting from {{ exp.price }}</div>
          <button class="explore-btn">See Locations</button>
        </div>
      </section>
    </div>

    <div class="cta-section">
      <h2>Ready for an <span>Unforgettable</span> Night?</h2>
      <p>Join millions of movie lovers and experience the magic today.</p>
      <router-link to="/" class="book-now-btn">Book Tickets Now</router-link>
    </div>
  </div>
</template>

<script>
export default {
  name: 'ExperiencesView',
  data() {
    return {
      experiences: [
        {
          id: 1,
          title: "IMAX Experience",
          badge: "THE ULTIMATE",
          description: "Crystal clear images and our 12,000-watt, laser-aligned digital sound system will make you feel like you're in the movie. The huge screen fills your peripheral vision.",
          image: "/images/imax.jpg",
          features: ["Laser Projection", "12-Channel Audio", "Expanded Aspect Ratio", "Comfort Seating"],
          price: "$18.50"
        },
        {
          id: 2,
          title: "4DX Immersive",
          badge: "FEEL THE ACTION",
          description: "Motion seats, scents, wind, and lightning! 4DX goes beyond the 3D frame to involve all your senses. Every punch, every explosion, felt in real-time.",
          image: "https://images.unsplash.com/photo-1595769816263-9b910be24d5f?q=80&w=2070&auto=format&fit=crop",
          features: ["Motion Seats", "Environmental Effects", "Tactile Feedback", "Surround Sound"],
          price: "$22.00"
        },
        {
          id: 3,
          title: "Gold Class / VIP",
          badge: "PREMIUM LUXURY",
          description: "Full recliner seats, personal service at the push of a button, and a premium gourmet menu. The most luxurious way to enjoy your favorite blockbusters.",
          image: "https://images.unsplash.com/photo-1505686994434-e3cc5abf1330?q=80&w=2072&auto=format&fit=crop",
          features: ["Full Recliners", "Private Menu", "Waiter Service", "Exclusive Lounge"],
          price: "$25.00"
        },
        {
          id: 4,
          title: "Standard Theater",
          badge: "CLASSIC COMFORT",
          description: "Enjoy movies the classic way. High-quality projection and tiered seating ensure every user has a great view and clear audio.",
          image: "https://images.unsplash.com/photo-1440404653325-ab127d49abc1?q=80&w=2070&auto=format&fit=crop",
          features: ["Tiered Seating", "4K Projection", "Dolby Digital", "Affordable Price"],
          price: "$12.00"
        }
      ]
    }
  }
}
</script>

<style scoped>
.experiences-view {
  padding-top: 80px;
  background: #050505;
  color: white;
  overflow-x: hidden;
}

.hero-section {
  position: relative;
  height: 60vh;
  display: flex;
  align-items: center;
  justify-content: center;
  text-align: center;
  background: url('https://images.unsplash.com/photo-1478720568477-152d9b164e26?q=80&w=2070&auto=format&fit=crop') fixed center center/cover;
}

.hero-overlay {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: linear-gradient(rgba(0,0,0,0.4), rgba(5,5,5,1));
}

.hero-content {
  position: relative;
  z-index: 10;
  max-width: 800px;
  padding: 0 2rem;
}

.eyebrow {
  color: #e50914;
  font-weight: 700;
  letter-spacing: 4px;
  text-transform: uppercase;
  font-size: 0.95rem;
  margin-bottom: 1.5rem;
  display: block;
}

.hero-content h1 {
  font-size: 4rem;
  margin-bottom: 1.5rem;
  font-weight: 800;
}

.hero-content h1 span {
  color: #e50914;
}

.hero-content p {
  color: rgba(255, 255, 255, 0.7);
  font-size: 1.25rem;
  line-height: 1.5;
}

.experience-list {
  max-width: 1400px;
  margin: 4rem auto;
  padding: 0 2rem;
}

.experience-section {
  display: flex;
  align-items: center;
  gap: 5rem;
  margin-bottom: 8rem;
}

.experience-section.reverse {
  flex-direction: row-reverse;
}

.experience-image {
  flex: 1.2;
  position: relative;
  border-radius: 24px;
  overflow: hidden;
  box-shadow: 0 20px 40px rgba(0,0,0,0.5);
  transition: transform 0.5s cubic-bezier(0.175, 0.885, 0.32, 1.275);
}

.experience-image:hover {
  transform: scale(1.02);
}

.experience-image img {
  width: 100%;
  height: 500px;
  object-fit: cover;
}

.experience-badge {
  position: absolute;
  bottom: 2rem;
  left: 2rem;
  background: rgba(229, 9, 20, 0.9);
  color: white;
  padding: 0.6rem 1.2rem;
  border-radius: 8px;
  font-weight: 800;
  font-size: 0.85rem;
  letter-spacing: 1px;
}

.experience-content {
  flex: 0.8;
}

.accent-line {
  width: 60px;
  height: 4px;
  background: #e50914;
  margin-bottom: 1.5rem;
  border-radius: 2px;
}

.experience-content h2 {
  font-size: 2.5rem;
  margin-bottom: 1.5rem;
}

.description {
  color: rgba(255, 255, 255, 0.7);
  font-size: 1.1rem;
  line-height: 1.8;
  margin-bottom: 2rem;
}

.features {
  display: flex;
  flex-wrap: wrap;
  gap: 1rem;
  margin-bottom: 2.5rem;
}

.feature-tag {
  background: rgba(255, 255, 255, 0.1);
  padding: 0.5rem 1rem;
  border-radius: 50px;
  font-size: 0.9rem;
  display: flex;
  align-items: center;
  gap: 0.5rem;
  border: 1px solid rgba(255, 255, 255, 0.05);
}

.feature-tag i {
  color: #22c55e;
  font-size: 0.8rem;
}

.price-hint {
  font-weight: 600;
  color: rgba(255, 255, 255, 0.5);
  margin-bottom: 1.5rem;
  font-size: 0.9rem;
}

.explore-btn {
  padding: 1rem 2.5rem;
  background: white;
  color: black;
  border-radius: 12px;
  font-weight: 700;
  transition: all 0.3s;
}

.explore-btn:hover {
  background: #e50914;
  color: white;
  transform: translateY(-2px);
  box-shadow: 0 10px 20px rgba(229, 9, 20, 0.3);
}

.cta-section {
  background: linear-gradient(rgba(229, 9, 20, 0.1), transparent);
  padding: 8rem 2rem;
  text-align: center;
  border-top: 1px solid rgba(229, 9, 20, 0.1);
}

.cta-section h2 {
  font-size: 3rem;
  margin-bottom: 1rem;
}

.cta-section span {
  color: #e50914;
}

.cta-section p {
  color: rgba(255, 255, 255, 0.6);
  margin-bottom: 3rem;
  font-size: 1.2rem;
}

.book-now-btn {
  display: inline-block;
  background: #e50914;
  color: white;
  padding: 1.2rem 3rem;
  border-radius: 50px;
  font-weight: 700;
  font-size: 1.1rem;
  text-decoration: none;
  transition: all 0.3s;
}

.book-now-btn:hover {
  transform: scale(1.05);
  box-shadow: 0 0 30px rgba(229, 9, 20, 0.4);
}

@media (max-width: 1024px) {
  .experience-section, .experience-section.reverse {
    flex-direction: column;
    gap: 3rem;
  }
  
  .experience-image img {
    height: 400px;
  }
}

@media (max-width: 768px) {
  .hero-content h1 {
    font-size: 2.8rem;
  }
  
  .experience-content h2 {
    font-size: 2rem;
  }
}
</style>
