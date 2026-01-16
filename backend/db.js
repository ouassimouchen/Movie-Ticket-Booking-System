const Database = require('better-sqlite3');
const path = require('path');

const db = new Database(path.join(__dirname, 'database.sqlite'));

// Create users table
db.exec(`
  CREATE TABLE IF NOT EXISTS users (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL,
    email TEXT UNIQUE NOT NULL,
    password TEXT NOT NULL,
    role TEXT DEFAULT 'user',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
  )
`);

// Create bookings table
db.exec(`
  CREATE TABLE IF NOT EXISTS bookings (
    id TEXT PRIMARY KEY,
    user_id INTEGER,
    movie_id INTEGER,
    movie_title TEXT,
    movie_poster TEXT,
    session_hall TEXT,
    session_time TEXT,
    seats TEXT,
    total REAL,
    booking_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    status TEXT DEFAULT 'Confirmed',
    FOREIGN KEY (user_id) REFERENCES users (id)
  )
`);

module.exports = db;
