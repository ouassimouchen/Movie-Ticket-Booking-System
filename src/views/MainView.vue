<template>
  <div class="main-container">
    <nav class="navbar">
      <div class="logo">
        <span class="logo-text">CINE<span>PASS</span></span>
      </div>
      <div class="user-info">
        <span>Admin View</span>
        <button @click="logout" class="logout-btn">Logout</button>
      </div>
    </nav>

    <main class="content">
      <header class="page-header">
        <h1>Dashboard - Administration</h1>
        <p>Monitor and manage movie ticket bookings and user data</p>
      </header>

      <div class="dashboard-tabs">
        <button :class="{ active: currentTab === 'users' }" @click="currentTab = 'users'">
          <i class="fas fa-users"></i> Users
        </button>
        <button :class="{ active: currentTab === 'movies' }" @click="currentTab = 'movies'">
          <i class="fas fa-film"></i> Movies
        </button>
        <button :class="{ active: currentTab === 'bookings' }" @click="currentTab = 'bookings'">
          <i class="fas fa-ticket-alt"></i> Bookings
        </button>
      </div>

      <div v-if="loading" class="loading-state">
        <i class="fas fa-spinner fa-spin"></i>
        <span>Fetching data from database...</span>
      </div>

      <div v-else class="data-card">
        <!-- Movies View -->
        <template v-if="currentTab === 'movies'">
          <div class="card-header">
            <h3>Movies</h3>
            <button class="add-btn" @click="openAddMovie">
               <i class="fas fa-plus"></i> Add Movie
            </button>
          </div>
          <div class="table-container">
            <table>
              <thead>
                <tr>
                  <th>Poster</th>
                  <th>Title</th>
                  <th>Rating</th>
                  <th>Duration</th>
                  <th>Actions</th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="movie in allMovies" :key="movie.id">
                  <td><img :src="movie.poster" class="table-poster" /></td>
                  <td><strong>{{ movie.title }}</strong></td>
                  <td>{{ movie.rating }}</td>
                  <td>{{ movie.duration }}</td>
                  <td class="actions">
                    <button class="edit-btn" @click="openEditMovie(movie)" title="Edit Movie">
                      <i class="fas fa-edit"></i>
                    </button>
                    <button class="delete-btn" @click="confirmDeleteMovie(movie)" title="Delete Movie">
                      <i class="fas fa-trash"></i>
                    </button>
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
        </template>
        <!-- Users View -->
        <template v-else-if="currentTab === 'users'">
          <div class="card-header">
            <h3>User List</h3>
            <span class="badge">{{ users.length }} Total</span>
          </div>
          <div class="table-container">
            <table>
              <thead>
                <tr>
                  <th>ID</th>
                  <th>Name</th>
                  <th>Email</th>
                  <th>Role</th>
                  <th>Created At</th>
                  <th>Actions</th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="user in users" :key="user.id">
                  <td>{{ user.id }}</td>
                  <td class="user-name">{{ user.name }}</td>
                  <td class="user-email">{{ user.email }}</td>
                  <td>
                    <span :class="['role-pill', user.role]">{{ user.role }}</span>
                  </td>
                  <td class="date">{{ formatDate(user.created_at) }}</td>
                  <td class="actions">
                    <button class="edit-btn" @click="openEditModal(user)" title="Edit User">
                      <i class="fas fa-edit"></i>
                    </button>
                    <button class="delete-btn" @click="confirmDelete(user)" title="Delete User">
                      <i class="fas fa-trash"></i>
                    </button>
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
        </template>

        <!-- Bookings View -->
        <template v-else-if="currentTab === 'bookings'">
          <div class="card-header">
            <h3>All Bookings</h3>
            <span class="badge">{{ bookings.length }} Total</span>
          </div>
          <div class="table-container">
            <table>
              <thead>
                <tr>
                  <th>ID</th>
                  <th>Movie</th>
                  <th>User ID</th>
                  <th>Hall/Time</th>
                  <th>Total</th>
                  <th>Date</th>
                  <th>Actions</th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="booking in bookings" :key="booking.id">
                  <td>{{ booking.id }}</td>
                  <td>
                    <div class="movie-cell">
                      <strong>{{ booking.movieTitle }}</strong>
                    </div>
                  </td>
                  <td>{{ booking.userId }}</td>
                  <td>{{ booking.sessionHall }} - {{ booking.sessionTime }}</td>
                  <td class="price">${{ booking.total.toFixed(2) }}</td>
                  <td class="date">{{ formatDate(booking.bookingDate) }}</td>
                  <td class="actions">
                    <button class="delete-btn" @click="confirmDeleteBooking(booking)" title="Delete Booking">
                      <i class="fas fa-trash"></i>
                    </button>
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
        </template>
      </div>
    </main>

    <!-- Edit Modal -->
    <div v-if="showModal" class="modal-overlay" @click.self="showModal = false">
      <div class="modal">
        <div class="modal-header">
          <h3>Edit User</h3>
          <button class="close-btn" @click="showModal = false">&times;</button>
        </div>
        <form @submit.prevent="updateUser">
          <div class="form-group">
            <label>Name</label>
            <input type="text" v-model="editForm.name" required />
          </div>
          <div class="form-group">
            <label>Email</label>
            <input type="email" v-model="editForm.email" required />
          </div>
          <div class="form-group">
            <label>Role</label>
            <select v-model="editForm.role">
              <option value="user">User</option>
              <option value="admin">Admin</option>
            </select>
          </div>
          <div class="modal-footer">
            <button type="button" class="cancel-btn" @click="showModal = false">Cancel</button>
            <button type="submit" class="save-btn" :disabled="saving">
              <span v-if="saving">Saving...</span>
              <span v-else>Save Changes</span>
            </button>
          </div>
        </form>
      </div>
    </div>

    <!-- Delete Confirmation Modal -->
    <div v-if="showDeleteModal" class="modal-overlay" @click.self="showDeleteModal = false">
      <div class="modal delete-modal">
        <div class="modal-header">
          <h3>Confirm Deletion</h3>
          <button class="close-btn" @click="showDeleteModal = false">&times;</button>
        </div>
        <div class="modal-body">
          <p>Are you sure you want to delete user <strong>{{ userToDelete?.name }}</strong>?</p>
          <p class="warning-text">This action cannot be undone and will also remove all associated bookings.</p>
        </div>
        <div class="modal-footer">
          <button type="button" class="cancel-btn" @click="showDeleteModal = false">Cancel</button>
          <button type="button" class="delete-confirm-btn" @click="executeDelete" :disabled="deleting">
            <span v-if="deleting">Deleting...</span>
            <span v-else>Yes, Delete User</span>
          </button>
        </div>
      </div>
    </div>

    <!-- Delete Booking Modal -->
    <div v-if="showDeleteBookingModal" class="modal-overlay" @click.self="showDeleteBookingModal = false">
      <div class="modal delete-modal">
        <div class="modal-header">
          <h3>Delete Booking</h3>
          <button class="close-btn" @click="showDeleteBookingModal = false">&times;</button>
        </div>
        <div class="modal-body">
          <p>Are you sure you want to delete this booking for <strong>{{ bookingToDelete?.movieTitle }}</strong>?</p>
          <p>Customer ID: {{ bookingToDelete?.userId }}</p>
        </div>
        <div class="modal-footer">
          <button type="button" class="cancel-btn" @click="showDeleteBookingModal = false">Cancel</button>
          <button type="button" class="delete-confirm-btn" @click="executeDeleteBooking" :disabled="deleting">
            <span v-if="deleting">Deleting...</span>
            <span v-else>Confirm Deletion</span>
          </button>
        </div>
      </div>
    </div>
    <!-- Movie Modal -->
    <div v-if="showMovieModal" class="modal-overlay" @click.self="showMovieModal = false">
      <div class="modal movie-modal">
        <div class="modal-header">
          <h3>{{ movieForm.id ? 'Edit Movie' : 'Add New Movie' }}</h3>
          <button class="close-btn" @click="showMovieModal = false">&times;</button>
        </div>
        <form @submit.prevent="saveMovie">
          <div class="form-row">
            <div class="form-group">
              <label>Title</label>
              <input type="text" v-model="movieForm.title" required />
            </div>
            <div class="form-group">
              <label>Rating</label>
              <select v-model="movieForm.rating">
                <option>G</option>
                <option>PG</option>
                <option>PG-13</option>
                <option>R</option>
              </select>
            </div>
          </div>
          <div class="form-row">
             <div class="form-group">
              <label>Duration</label>
              <input type="text" v-model="movieForm.duration" placeholder="e.g., 2h 30m" required />
            </div>
            <div class="form-group">
              <label>IMDB Rating</label>
              <input type="number" step="0.1" v-model="movieForm.imdbRating" required />
            </div>
          </div>
          <div class="form-group">
            <label>Poster URL</label>
            <input type="text" v-model="movieForm.poster" required />
          </div>
          <div class="form-group">
            <label>Backdrop URL</label>
            <input type="text" v-model="movieForm.backdrop" required />
          </div>
          <div class="form-group">
            <label>Description</label>
            <textarea v-model="movieForm.description" rows="3"></textarea>
          </div>
          <div class="form-row">
             <div class="form-group">
               <label>Genres (JSON array)</label>
               <input type="text" v-model="movieForm.genre" />
             </div>
             <div class="form-group">
               <label>Cast (JSON array)</label>
               <input type="text" v-model="movieForm.cast" />
             </div>
          </div>
          <div class="modal-footer">
            <button type="button" class="cancel-btn" @click="showMovieModal = false">Cancel</button>
            <button type="submit" class="save-btn" :disabled="saving">
              <span v-if="saving">Saving...</span>
              <span v-else>Save Movie</span>
            </button>
          </div>
        </form>
      </div>
    </div>

    <!-- Delete Movie Modal -->
    <div v-if="showDeleteMovieModal" class="modal-overlay" @click.self="showDeleteMovieModal = false">
      <div class="modal delete-modal">
        <div class="modal-header">
          <h3>Delete Movie</h3>
          <button class="close-btn" @click="showDeleteMovieModal = false">&times;</button>
        </div>
        <div class="modal-body">
          <p>Are you sure you want to delete <strong>{{ movieToDelete?.title }}</strong>?</p>
          <p class="warning-text">This will delete all showtimes associated with this movie.</p>
        </div>
        <div class="modal-footer">
          <button type="button" class="cancel-btn" @click="showDeleteMovieModal = false">Cancel</button>
          <button type="button" class="delete-confirm-btn" @click="executeDeleteMovie" :disabled="deleting">
            <span v-if="deleting">Deleting...</span>
            <span v-else>Confirm Deletion</span>
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import api from '../api';

export default {
  name: 'MainView',
  data() {
    return {
      users: [],
      bookings: [],
      allMovies: [],
      currentTab: 'users',
      loading: true,
      showModal: false,
      showDeleteModal: false,
      showDeleteBookingModal: false,
      showMovieModal: false,
      showDeleteMovieModal: false,
      userToDelete: null,
      bookingToDelete: null,
      movieToDelete: null,
      saving: false,
      deleting: false,
      editForm: {
        id: null,
        name: '',
        email: '',
        role: ''
      },
      movieForm: {
        id: null,
        title: '',
        poster: '',
        backdrop: '',
        rating: 'PG-13',
        duration: '',
        description: '',
        genre: '[]',
        cast: '[]',
        trailer: ''
      }
    }
  },
  async created() {
    // Basic Admin Check
    const userData = JSON.parse(localStorage.getItem('user') || '{}');
    if (userData.role !== 'admin') {
      alert('Access denied. Admin role required.');
      this.$router.push('/');
      return;
    }
    await Promise.all([this.fetchUsers(), this.fetchBookings(), this.fetchMovies()]);
  },
  methods: {
    async fetchMovies() {
       try {
         const response = await api.get('/movies');
         this.allMovies = response.data;
       } catch (err) {
         console.error('Error fetching movies:', err);
       }
    },
    async fetchUsers() {
      try {
        const response = await api.get('/admin/users');
        this.users = response.data;
      } catch (err) {
        console.error('Error fetching users:', err);
      }
    },
    async fetchBookings() {
      this.loading = true;
      try {
        const response = await api.get('/admin/bookings');
        this.bookings = response.data;
        console.log('Fetched bookings:', this.bookings);
      } catch (err) {
        console.error('Error fetching bookings:', err);
        alert('Failed to fetch bookings: ' + (err.response?.data?.message || err.message));
      } finally {
        this.loading = false;
      }
    },
    formatDate(dateStr) {
      if (!dateStr) return '-';
      const options = { year: 'numeric', month: 'short', day: 'numeric', hour: '2-digit', minute: '2-digit' };
      return new Date(dateStr).toLocaleDateString('en-US', options);
    },
    openEditModal(user) {
      this.editForm = { ...user };
      this.showModal = true;
    },
    async updateUser() {
      this.saving = true;
      try {
        await api.put(`/admin/users/${this.editForm.id}`, {
          name: this.editForm.name,
          email: this.editForm.email,
          role: this.editForm.role
        });
        alert('User updated successfully');
        this.showModal = false;
        await this.fetchUsers();
      } catch (err) {
        console.error('Error updating user:', err);
        alert('Failed to update user: ' + (err.response?.data?.message || err.message));
      } finally {
        this.saving = false;
      }
    },
    confirmDelete(user) {
      this.userToDelete = user;
      this.showDeleteModal = true;
    },
    async executeDelete() {
      if (!this.userToDelete) return;
      this.deleting = true;
      try {
        await api.delete(`/admin/users/${this.userToDelete.id}`);
        alert('User deleted successfully');
        this.showDeleteModal = false;
        this.userToDelete = null;
        await Promise.all([this.fetchUsers(), this.fetchBookings()]);
      } catch (err) {
        console.error('Error deleting user:', err);
        alert('Failed to delete user: ' + (err.response?.data?.message || err.message));
      } finally {
        this.deleting = false;
      }
    },
    confirmDeleteBooking(booking) {
      this.bookingToDelete = booking;
      this.showDeleteBookingModal = true;
    },
    async executeDeleteBooking() {
      if (!this.bookingToDelete) return;
      this.deleting = true;
      try {
        await api.delete(`/admin/bookings/${this.bookingToDelete.id}`);
        alert('Booking deleted successfully');
        this.showDeleteBookingModal = false;
        this.bookingToDelete = null;
        await this.fetchBookings();
      } catch (err) {
        console.error('Error deleting booking:', err);
        alert('Failed to delete booking: ' + (err.response?.data?.message || err.message));
      } finally {
        this.deleting = false;
      }
    },
    openAddMovie() {
      this.movieForm = {
        id: null,
        title: '',
        poster: '',
        backdrop: '',
        rating: 'PG-13',
        duration: '',
        description: '',
        genre: '["Action"]',
        cast: '[]',
        trailer: ''
      };
      this.showMovieModal = true;
    },
    openEditMovie(movie) {
      this.movieForm = { 
        ...movie,
        genre: JSON.stringify(movie.genre),
        cast: JSON.stringify(movie.cast)
      };
      this.showMovieModal = true;
    },
    async saveMovie() {
      this.saving = true;
      try {
        const payload = {
            ...this.movieForm,
            // Genre and Cast should be strings in DB if we are using AdminMovieController directly
            // But if we want it to be clean, the AdminMovieController expects the Entity.
            // Movie Entity has genre and cast as Strings.
        };

        if (this.movieForm.id) {
          await api.put(`/admin/movies/${this.movieForm.id}`, payload);
        } else {
          await api.post('/admin/movies', payload);
        }
        alert('Movie saved successfully');
        this.showMovieModal = false;
        await this.fetchMovies();
      } catch (err) {
        console.error('Error saving movie:', err);
        alert('Failed to save movie: ' + (err.response?.data?.message || err.message));
      } finally {
        this.saving = false;
      }
    },
    confirmDeleteMovie(movie) {
        this.movieToDelete = movie;
        this.showDeleteMovieModal = true;
    },
    async executeDeleteMovie() {
        if (!this.movieToDelete) return;
        this.deleting = true;
        try {
            await api.delete(`/admin/movies/${this.movieToDelete.id}`);
            alert('Movie deleted successfully');
            this.showDeleteMovieModal = false;
            await this.fetchMovies();
        } catch (err) {
            alert('Failed to delete movie');
        } finally {
            this.deleting = false;
        }
    },
    logout() {
      localStorage.removeItem('token');
      localStorage.removeItem('user');
      this.$router.push('/');
    }
  }
}
</script>

<style scoped>
.main-container {
  min-height: 100vh;
  background-color: #0c0c0c;
  color: white;
}

.navbar {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 1rem 4rem;
  background: rgba(255, 255, 255, 0.02);
  border-bottom: 1px solid rgba(255, 255, 255, 0.1);
  backdrop-filter: blur(10px);
  position: sticky;
  top: 0;
  z-index: 100;
}

.logo-text {
  font-size: 1.5rem;
  font-weight: 800;
}

.logo-text span {
  color: #e50914;
}

.user-info {
  display: flex;
  align-items: center;
  gap: 1.5rem;
}

.logout-btn {
  background: transparent;
  color: #e50914;
  border: 1px solid #e50914;
  padding: 0.5rem 1rem;
  border-radius: 8px;
  font-weight: 600;
  transition: all 0.3s ease;
}

.logout-btn:hover {
  background: #e50914;
  color: white;
}

.page-header p {
  color: rgba(255, 255, 255, 0.6);
}

.dashboard-tabs {
  display: flex;
  gap: 1rem;
  margin-bottom: 2rem;
}

.dashboard-tabs button {
  background: rgba(255, 255, 255, 0.05);
  border: 1px solid rgba(255, 255, 255, 0.1);
  color: rgba(255, 255, 255, 0.6);
  padding: 0.8rem 2rem;
  border-radius: 12px;
  font-weight: 600;
  cursor: pointer;
  transition: all 0.3s ease;
  display: flex;
  align-items: center;
  gap: 0.8rem;
}

.dashboard-tabs button:hover {
  background: rgba(255, 255, 255, 0.1);
  color: white;
}

.dashboard-tabs button.active {
  background: #e50914;
  color: white;
  border-color: #e50914;
}

.price {
  color: #10b981;
  font-weight: 700;
}

.movie-cell {
  display: flex;
  flex-direction: column;
}

.table-poster {
  width: 40px;
  height: 60px;
  object-fit: cover;
  border-radius: 4px;
}

.add-btn {
  background: #10b981;
  color: white;
  border: none;
  padding: 0.5rem 1.2rem;
  border-radius: 8px;
  font-weight: 600;
  cursor: pointer;
}

.movie-modal {
    max-width: 600px !important;
}

textarea {
    width: 100%;
    padding: 0.8rem 1rem;
    background: rgba(255, 255, 255, 0.05);
    border: 1px solid rgba(255, 255, 255, 0.1);
    border-radius: 10px;
    color: white;
    font-size: 1rem;
    font-family: inherit;
    resize: none;
}

.loading-state {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 1rem;
  padding: 4rem;
  color: rgba(255, 255, 255, 0.5);
}

.loading-state i {
  font-size: 2rem;
  color: #e50914;
}

.data-card {
  background: rgba(255, 255, 255, 0.03);
  border: 1px solid rgba(255, 255, 255, 0.08);
  border-radius: 20px;
  overflow: hidden;
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.3);
}

.card-header {
  padding: 1.5rem 2rem;
  display: flex;
  justify-content: space-between;
  align-items: center;
  border-bottom: 1px solid rgba(255, 255, 255, 0.08);
}

.badge {
  background: rgba(229, 9, 20, 0.2);
  color: #e50914;
  padding: 0.25rem 0.75rem;
  border-radius: 20px;
  font-size: 0.8rem;
  font-weight: 700;
}

.table-container {
  overflow-x: auto;
}

table {
  width: 100%;
  border-collapse: collapse;
  text-align: left;
}

th {
  padding: 1.25rem 2rem;
  font-size: 0.85rem;
  text-transform: uppercase;
  letter-spacing: 1px;
  color: rgba(255, 255, 255, 0.5);
  font-weight: 600;
  border-bottom: 1px solid rgba(255, 255, 255, 0.08);
}

td {
  padding: 1.25rem 2rem;
  border-bottom: 1px solid rgba(255, 255, 255, 0.03);
  color: rgba(255, 255, 255, 0.8);
}

tr:hover {
  background: rgba(255, 255, 255, 0.02);
}

.user-name {
  color: white;
  font-weight: 600;
}

.user-email {
  color: rgba(255, 255, 255, 0.4);
  font-size: 0.85rem;
}

.role-pill {
  padding: 0.2rem 0.6rem;
  border-radius: 6px;
  font-size: 0.75rem;
  font-weight: 700;
  text-transform: uppercase;
}

.role-pill.admin {
  background: rgba(229, 9, 20, 0.2);
  color: #e50914;
}

.role-pill.user {
  background: rgba(255, 255, 255, 0.1);
  color: white;
}

.date {
  font-family: monospace;
  color: rgba(255, 255, 255, 0.6);
}

.actions {
  display: flex;
  gap: 0.5rem;
}

.actions button {
  background: rgba(255, 255, 255, 0.05);
  border: none;
  color: white;
  width: 32px;
  height: 32px;
  border-radius: 6px;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  transition: all 0.2s;
}

.edit-btn:hover {
  background: #3b82f6;
  color: white;
}

.delete-btn:hover {
  background: #ef4444;
  color: white;
}

/* Modal Styles */
.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0,0,0,0.8);
  backdrop-filter: blur(5px);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 1000;
}

.modal {
  background: #181818;
  border: 1px solid rgba(255, 255, 255, 0.1);
  border-radius: 20px;
  width: 100%;
  max-width: 450px;
  padding: 2rem;
  box-shadow: 0 25px 50px -12px rgba(0, 0, 0, 0.5);
}

.modal-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 2rem;
}

.modal-header h3 {
  font-size: 1.5rem;
  margin: 0;
}

.close-btn {
  background: transparent;
  border: none;
  color: rgba(255, 255, 255, 0.5);
  font-size: 1.5rem;
  cursor: pointer;
}

.form-group {
  margin-bottom: 1.5rem;
}

.form-group label {
  display: block;
  font-size: 0.85rem;
  margin-bottom: 0.5rem;
  color: rgba(255, 255, 255, 0.6);
}

.form-group input, .form-group select {
  width: 100%;
  padding: 0.8rem 1rem;
  background: rgba(255, 255, 255, 0.05);
  border: 1px solid rgba(255, 255, 255, 0.1);
  border-radius: 10px;
  color: white;
  font-size: 1rem;
}

.modal-footer {
  display: flex;
  justify-content: flex-end;
  gap: 1rem;
  margin-top: 2rem;
}

.cancel-btn, .save-btn {
  padding: 0.8rem 1.5rem;
  border-radius: 10px;
  font-weight: 600;
  cursor: pointer;
  transition: all 0.3s;
}

.cancel-btn {
  background: rgba(255, 255, 255, 0.05);
  color: white;
  border: 1px solid rgba(255, 255, 255, 0.1);
}

.save-btn {
  background: #e50914;
  color: white;
  border: none;
}

.save-btn:disabled, .delete-confirm-btn:disabled {
  opacity: 0.5;
  cursor: not-allowed;
}

.delete-confirm-btn {
  padding: 0.8rem 1.5rem;
  border-radius: 10px;
  font-weight: 600;
  cursor: pointer;
  transition: all 0.3s;
  background: #ef4444;
  color: white;
  border: none;
}

.delete-confirm-btn:hover {
  background: #dc2626;
}

.warning-text {
  color: #ef4444;
  font-size: 0.85rem;
  margin-top: 1rem;
}

.modal-body {
  margin-bottom: 2rem;
}

@media (max-width: 768px) {
  .content {
    padding: 1.5rem;
  }
  .navbar {
    padding: 1rem 1.5rem;
  }
}
</style>
