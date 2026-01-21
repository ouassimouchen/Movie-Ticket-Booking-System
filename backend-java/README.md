# CinePass Java Backend

This is the migrated Java Spring Boot backend for the CinePass application.

## Prerequisites

- **Java Development Kit (JDK) 8** or higher.
- **Maven** (optional, wrapper provided if initialized properly, otherwise install Maven).

## Project Structure

- `src/main/java`: Source code
- `src/main/resources`: Configuration (`application.properties`)
- `pom.xml`: Maven dependencies

## Configuration

The application is configured to run on port **5000** and connects to the existing SQLite database at `../backend/database.sqlite`.

## Running the Application

1. **Navigate to the backend directory:**
   ```bash
   cd backend-java
   ```

2. **Run with Maven:**
   ```bash
   mvn spring-boot:run
   ```

   *If you do not have Maven installed, please install it (e.g., `brew install maven` on macOS).*

3. **Verify:**
   The server should start on port 5000.
   Endpoint: `http://localhost:5000/api/health`

## API Endpoints

- `POST /api/auth/login`: Login user
- `POST /api/auth/register`: Register user
- `GET /api/bookings`: Get user bookings
- `POST /api/bookings`: Create booking
- `GET /api/admin/users`: Admin get users

## Note on Frontend

The frontend code requires no major changes as the API contract has been preserved, including the JSON structure for Login response and Booking objects.
