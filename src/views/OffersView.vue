<template>
  <div class="offers-view">
    <div class="hero-section">
      <div class="hero-content">
        <h1>Exclusive <span>Offers</span></h1>
        <p>Grab the best deals on movies, snacks, and cinematic experiences.</p>
      </div>
    </div>

    <div class="offers-grid">
      <div v-for="offer in offers" :key="offer.id" class="offer-card" :style="{ '--color': offer.color }">
        <div class="card-image">
          <img :src="offer.image" :alt="offer.title" />
          <div class="discount-badge">{{ offer.discount }}</div>
        </div>
        <div class="card-body">
          <div class="card-meta">
            <span class="category">{{ offer.category }}</span>
            <span class="validity">Valid till {{ offer.expiry }}</span>
          </div>
          <h3>{{ offer.title }}</h3>
          <p>{{ offer.description }}</p>
          <button class="apply-btn">Claim Offer</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'OffersView',
  data() {
    return {
      offers: [
        {
          id: 1,
          title: "Student Discount",
          description: "Get flat 25% off on all movie tickets. Just show your valid student ID at the counter or verify online.",
          discount: "25% OFF",
          category: "Education",
          expiry: "31 Dec 2024",
          image: "https://images.unsplash.com/photo-1523240795612-9a054b0db644?q=80&w=2070&auto=format&fit=crop",
          color: "#3b82f6"
        },
        {
          id: 2,
          title: "Family Sunday Bundle",
          description: "4 tickets, 2 large popcorns, and 4 sodas for a special price. Perfect for a family outing.",
          discount: "BUNDLE DEAL",
          category: "Family",
          expiry: "Every Sunday",
          image: "https://images.unsplash.com/photo-1536440136628-849c177e76a1?q=80&w=2025&auto=format&fit=crop",
          color: "#ef4444"
        },
        {
          id: 3,
          title: "BOGO with CinePass Bank",
          description: "Buy one movie ticket and get the second one absolutely free with CinePass Credit Cards.",
          discount: "BUY 1 GET 1",
          category: "Bank Offer",
          expiry: "Limited Time",
          image: "https://images.unsplash.com/photo-1563986768609-322da13575f3?q=80&w=2070&auto=format&fit=crop",
          color: "#10b981"
        },
        {
          id: 4,
          title: "Late Night Special",
          description: "Flat $5 tickets for all shows starting after 10 PM on weekdays. Experience more for less.",
          discount: "$5 ONLY",
          category: "Time-Based",
          expiry: "Mon - Thu",
          image: "https://images.unsplash.com/photo-1485090916855-2ec2360f065e?q=80&w=2070&auto=format&fit=crop",
          color: "#8b5cf6"
        }
      ]
    }
  }
}
</script>

<style scoped>
.offers-view {
  padding-top: 80px;
  min-height: 100vh;
  background: #0a0a0a;
  color: white;
}

.hero-section {
  height: 40vh;
  display: flex;
  align-items: center;
  justify-content: center;
  text-align: center;
  background: linear-gradient(rgba(0,0,0,0.6), rgba(10,10,10,1)), url('https://images.unsplash.com/photo-1485846234645-a62644f84728?q=80&w=2059&auto=format&fit=crop');
  background-size: cover;
  background-position: center;
}

.hero-content h1 {
  font-size: 3.5rem;
  margin-bottom: 1rem;
}

.hero-content h1 span {
  color: #e50914;
}

.hero-content p {
  color: rgba(255, 255, 255, 0.7);
  font-size: 1.2rem;
}

.offers-grid {
  max-width: 1200px;
  margin: -50px auto 4rem;
  padding: 0 2rem;
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
  gap: 2rem;
}

.offer-card {
  background: rgba(255, 255, 255, 0.05);
  border-radius: 20px;
  overflow: hidden;
  border: 1px solid rgba(255, 255, 255, 0.1);
  transition: all 0.3s ease;
  backdrop-filter: blur(10px);
}

.offer-card:hover {
  transform: translateY(-10px);
  border-color: var(--color);
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.5);
}

.card-image {
  height: 200px;
  position: relative;
}

.card-image img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.discount-badge {
  position: absolute;
  top: 1rem;
  right: 1rem;
  background: var(--color);
  color: white;
  padding: 0.5rem 1rem;
  border-radius: 50px;
  font-weight: 700;
  font-size: 0.8rem;
  box-shadow: 0 4px 10px rgba(0,0,0,0.3);
}

.card-body {
  padding: 1.5rem;
}

.card-meta {
  display: flex;
  justify-content: space-between;
  margin-bottom: 1rem;
  font-size: 0.8rem;
}

.category {
  color: var(--color);
  font-weight: 600;
  text-transform: uppercase;
}

.validity {
  color: rgba(255, 255, 255, 0.5);
}

.card-body h3 {
  font-size: 1.4rem;
  margin-bottom: 1rem;
}

.card-body p {
  color: rgba(255, 255, 255, 0.7);
  font-size: 0.95rem;
  line-height: 1.6;
  margin-bottom: 1.5rem;
  height: 4.8rem;
  overflow: hidden;
  display: -webkit-box;
  line-clamp: 3;
  -webkit-line-clamp: 3;
  -webkit-box-orient: vertical;
}

.apply-btn {
  width: 100%;
  padding: 0.8rem;
  background: transparent;
  color: white;
  border: 1px solid var(--color);
  border-radius: 10px;
  font-weight: 600;
  transition: all 0.3s;
}

.apply-btn:hover {
  background: var(--color);
  box-shadow: 0 5px 15px rgba(0,0,0,0.3);
}

@media (max-width: 768px) {
  .hero-content h1 {
    font-size: 2.5rem;
  }
}
</style>
