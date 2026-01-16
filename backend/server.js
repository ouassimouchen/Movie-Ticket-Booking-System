require('dotenv').config();
const express = require('express');
const cors = require('cors');
const helmet = require('helmet');
const jwt = require('jsonwebtoken');
const authRoutes = require('./auth');
const db = require('./db');

// Enable foreign keys for this connection
db.pragma('foreign_keys = ON');

const app = express();
const PORT = process.env.PORT || 5000;
const JWT_SECRET = process.env.JWT_SECRET || 'cinepass_secret_key_123';

app.use(helmet());
app.use(cors());
app.use(express.json());

// Middleware to authenticate JWT
const authenticateToken = (req, res, next) => {
    const authHeader = req.headers['authorization'];
    const token = authHeader && authHeader.split(' ')[1];

    if (!token) return res.status(401).json({ message: 'No token provided' });

    jwt.verify(token, JWT_SECRET, (err, user) => {
        if (err) return res.status(403).json({ message: 'Invalid token' });
        req.user = user;
        next();
    });
};

// Middleware to check if user is admin
const isAdmin = (req, res, next) => {
    console.log(`[AUTH] Checking admin role for user: ${req.user.email}, Role in token: ${req.user.role}`);
    if (req.user && req.user.role === 'admin') {
        next();
    } else {
        console.log(`[AUTH] Access denied: User ${req.user.email} is not an admin`);
        res.status(403).json({ message: 'Access denied. Admin role required.' });
    }
};

// Routes
app.use('/api/auth', authRoutes);

// Booking Routes
app.post('/api/bookings', authenticateToken, (req, res) => {
    const { id, movie_id, movie_title, movie_poster, session_hall, session_time, seats, total } = req.body;
    const user_id = req.user.id;
    console.log(`[BOOKING] Attempting to create booking ${id} for user ${user_id}`);

    try {
        const stmt = db.prepare(`
            INSERT INTO bookings (id, user_id, movie_id, movie_title, movie_poster, session_hall, session_time, seats, total)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
        `);
        stmt.run(id, user_id, movie_id, movie_title, movie_poster, session_hall, session_time, JSON.stringify(seats), total);
        console.log(`[BOOKING] Successfully created booking ${id}`);
        res.status(201).json({ message: 'Booking successful', bookingId: id });
    } catch (err) {
        res.status(500).json({ message: 'Error creating booking', error: err.message });
    }
});

app.get('/api/bookings', authenticateToken, (req, res) => {
    const user_id = req.user.id;
    console.log(`[BOOKING] Fetching bookings for user ${user_id}`);
    try {
        const bookings = db.prepare('SELECT * FROM bookings WHERE user_id = ? ORDER BY booking_date DESC').all(user_id);
        console.log(`[BOOKING] Found ${bookings.length} bookings for user ${user_id}`);
        // Parse seats JSON string
        const parsedBookings = bookings.map(b => ({
            ...b,
            seats: JSON.parse(b.seats),
            movie: { title: b.movie_title, poster: b.movie_poster },
            session: { hall: b.session_hall, time: b.session_time }
        }));
        res.json(parsedBookings);
    } catch (err) {
        res.status(500).json({ message: 'Error fetching bookings', error: err.message });
    }
});

// Admin User Management Routes
app.get('/api/admin/users', authenticateToken, isAdmin, (req, res) => {
    try {
        const users = db.prepare('SELECT id, name, email, role, created_at FROM users').all();
        res.json(users);
    } catch (err) {
        res.status(500).json({ message: 'Error fetching users', error: err.message });
    }
});

app.put('/api/admin/users/:id', authenticateToken, isAdmin, (req, res) => {
    const { name, email, role } = req.body;
    const { id } = req.params;
    try {
        const stmt = db.prepare('UPDATE users SET name = ?, email = ?, role = ? WHERE id = ?');
        stmt.run(name, email, role, id);
        res.json({ message: 'User updated successfully' });
    } catch (err) {
        res.status(500).json({ message: 'Error updating user', error: err.message });
    }
});

app.delete('/api/admin/users/:id', authenticateToken, isAdmin, (req, res) => {
    const { id } = req.params;
    console.log(`[ADMIN] User ${req.user.id} attempting to delete user ${id}`);

    try {
        // First delete associated bookings to avoid constraint issues if not cascading
        const deleteBookings = db.prepare('DELETE FROM bookings WHERE user_id = ?');
        deleteBookings.run(id);

        const stmt = db.prepare('DELETE FROM users WHERE id = ?');
        const result = stmt.run(id);

        if (result.changes === 0) {
            console.log(`[ADMIN] Delete failed: User ${id} not found`);
            return res.status(404).json({ message: 'User not found' });
        }

        console.log(`[ADMIN] Successfully deleted user ${id}`);
        res.json({ message: 'User deleted successfully' });
    } catch (err) {
        console.error(`[ADMIN] Error deleting user ${id}:`, err);
        res.status(500).json({ message: 'Error deleting user', error: err.message });
    }
});

app.get('/api/health', (req, res) => {
    res.json({ status: 'OK', message: 'CinePass API is running' });
});

app.listen(PORT, () => {
    console.log(`Server is running on port ${PORT}`);
});
