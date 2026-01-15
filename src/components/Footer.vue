<template>
  <footer class="site-footer">
    <div class="footer-content">
      <div class="footer-brand">
        <span class="logo-text">CINE<span>PASS</span></span>
        <p>Experience movies like never before.</p>
      </div>
      
      <div class="footer-links">
        <div class="link-group">
          <h4>Movies</h4>
          <a href="#">Now Showing</a>
          <a href="#">Coming Soon</a>
          <a href="#">Cinemas</a>
        </div>
        <div class="link-group">
          <h4>Support</h4>
          <a href="#">Help Center</a>
          <a href="#">Contact Us</a>
          <a href="#">Privacy Policy</a>
        </div>
        <div class="link-group">
          <h4>Social</h4>
          <a href="#">Twitter</a>
          <a href="#">Instagram</a>
          <a href="#">Facebook</a>
        </div>
      </div>
    </div>
    <div class="footer-bottom">
      <p>&copy; 2024 CinePass. All rights reserved.</p>
    </div>
  </footer>
</template>

<script>
export default {
  name: 'Footer'
}
</script>

<style scoped>
.site-footer {
  background: #050505;
  padding: 4rem 4rem 2rem;
  border-top: 1px solid rgba(255, 255, 255, 0.05);
  margin-top: auto;
}

.footer-content {
  display: flex;
  justify-content: space-between;
  flex-wrap: wrap;
  gap: 4rem;
  max-width: 1400px;
  margin: 0 auto;
}

.logo-text {
  font-size: 1.5rem;
  font-weight: 800;
  color: white;
}

.logo-text span {
  color: var(--primary);
}

.footer-brand p {
  color: rgba(255, 255, 255, 0.5);
  margin-top: 1rem;
  max-width: 300px;
}

.footer-links {
  display: flex;
  gap: 4rem;
}

.link-group h4 {
  color: white;
  margin-bottom: 1.5rem;
  font-size: 1rem;
}

.link-group a {
  display: block;
  color: rgba(255, 255, 255, 0.5);
  text-decoration: none;
  margin-bottom: 0.8rem;
  font-size: 0.9rem;
  transition: color 0.3s;
}

.link-group a:hover {
  color: var(--primary);
}

.footer-bottom {
  border-top: 1px solid rgba(255, 255, 255, 0.05);
  margin-top: 4rem;
  padding-top: 2rem;
  text-align: center;
  color: rgba(255, 255, 255, 0.3);
  font-size: 0.85rem;
}

@media (max-width: 768px) {
  .site-footer {
    padding: 3rem 1.5rem;
  }
  
  .footer-links {
    gap: 2rem;
    flex-wrap: wrap;
  }
}
</style>
