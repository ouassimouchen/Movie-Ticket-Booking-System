<template>
  <div class="main-container">
    <nav class="navbar">
      <div class="logo">
        <span class="logo-text">CINE<span>PASS</span></span>
      </div>
      <div class="user-info">
        <span>Admin User</span>
        <button @click="logout" class="logout-btn">Logout</button>
      </div>
    </nav>

    <main class="content">
      <header class="page-header">
        <h1>Dashboard - User Management</h1>
        <p>Monitor and manage movie ticket bookings and user data</p>
      </header>

      <div class="data-card">
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
                <th>Region ID</th>
                <th>Status</th>
                <th>Role</th>
                <th>Created Time</th>
                <th>Updated Time</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="user in users" :key="user.id">
                <td>{{ user.id }}</td>
                <td class="user-name">{{ user.name }}</td>
                <td class="user-email">{{ user.email }}</td>
                <td>{{ user.regionId }}</td>
                <td>
                  <span :class="['status-pill', user.status.toLowerCase()]">{{ user.status }}</span>
                </td>
                <td>{{ user.role }}</td>
                <td>{{ user.createdTime }}</td>
                <td class="date">{{ user.updatedTime }}</td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
    </main>
  </div>
</template>

<script>
export default {
  name: 'MainView',
  data() {
    return {
      users: [
        { id: 1, name: 'Lukas Schneider', email: 'lukas.s@example.de', regionId: 'DE-784', status: 'Active', role: 'Admin', createdTime: '12:40:50', updatedTime: '2025-11-09' },
        { id: 2, name: 'Emma Lefebvre', email: 'emma.l@example.fr', regionId: 'FR-796', status: 'Pending', role: 'Editor', createdTime: '09:22:22', updatedTime: '2020-05-05' },
        { id: 3, name: 'Matteo Rossi', email: 'matteo.r@example.it', regionId: 'IT-35', status: 'Active', role: 'Viewer', createdTime: '17:06:24', updatedTime: '2014-02-01' },
        { id: 4, name: 'Sofia Andersson', email: 'sofia.a@example.se', regionId: 'SE-489', status: 'Active', role: 'Admin', createdTime: '14:26:38', updatedTime: '2014-11-17' },
        { id: 5, name: 'Oliver Smith', email: 'oliver.s@example.uk', regionId: 'UK-252', status: 'Inactive', role: 'Viewer', createdTime: '14:26:29', updatedTime: '2008-08-03' },
        { id: 6, name: 'Amélie Dubois', email: 'amelie.d@example.fr', regionId: 'FR-597', status: 'Active', role: 'Editor', createdTime: '12:29:31', updatedTime: '2015-09-09' },
        { id: 7, name: 'Hans Müller', email: 'hans.m@example.de', regionId: 'DE-12', status: 'Active', role: 'Viewer', createdTime: '10:15:00', updatedTime: '2023-12-01' },
        { id: 8, name: 'Elena Garcia', email: 'elena.g@example.es', regionId: 'ES-45', status: 'Pending', role: 'Editor', createdTime: '11:30:15', updatedTime: '2024-01-15' }
      ]
    }
  },
  mounted() {
    // Print data to console as shown in the user's screenshot
    console.log('MainView.vue:9');
    console.log('(', this.users.length, ')', JSON.parse(JSON.stringify(this.users)));
  },
  methods: {
    logout() {
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

.content {
  padding: 3rem 4rem;
  max-width: 1400px;
  margin: 0 auto;
}

.page-header {
  margin-bottom: 3rem;
}

.page-header h1 {
  font-size: 2.5rem;
  margin-bottom: 0.5rem;
}

.page-header p {
  color: rgba(255, 255, 255, 0.6);
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

.status-pill {
  padding: 0.2rem 0.6rem;
  border-radius: 6px;
  font-size: 0.75rem;
  font-weight: 600;
}

.status-pill.active {
  background: rgba(34, 197, 94, 0.1);
  color: #22c55e;
}

.status-pill.pending {
  background: rgba(234, 179, 8, 0.1);
  color: #eab308;
}

.status-pill.inactive {
  background: rgba(239, 68, 68, 0.1);
  color: #ef4444;
}

.date {
  font-family: monospace;
  color: rgba(255, 255, 255, 0.6);
}
</style>
