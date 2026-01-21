package com.cinepass.backend.entity;

import javax.persistence.*;

@Entity
@Table(name = "bookings")
public class Booking {
    @Id
    private String id; // Bookings use UUID string in existing implementation

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "movie_id")
    private Integer movieId;

    @Column(name = "movie_title")
    private String movieTitle;

    @Column(name = "movie_poster")
    private String moviePoster;

    @Column(name = "session_hall")
    private String sessionHall;

    @Column(name = "session_time")
    private String sessionTime;

    private String seats; // Check JSON storage

    private Double total;

    @Column(name = "booking_date", insertable = false, updatable = false)
    private String bookingDate;

    private String status;

    public Booking() {}

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public Integer getMovieId() { return movieId; }
    public void setMovieId(Integer movieId) { this.movieId = movieId; }
    public String getMovieTitle() { return movieTitle; }
    public void setMovieTitle(String movieTitle) { this.movieTitle = movieTitle; }
    public String getMoviePoster() { return moviePoster; }
    public void setMoviePoster(String moviePoster) { this.moviePoster = moviePoster; }
    public String getSessionHall() { return sessionHall; }
    public void setSessionHall(String sessionHall) { this.sessionHall = sessionHall; }
    public String getSessionTime() { return sessionTime; }
    public void setSessionTime(String sessionTime) { this.sessionTime = sessionTime; }
    public String getSeats() { return seats; }
    public void setSeats(String seats) { this.seats = seats; }
    public Double getTotal() { return total; }
    public void setTotal(Double total) { this.total = total; }
    public String getBookingDate() { return bookingDate; }
    public void setBookingDate(String bookingDate) { this.bookingDate = bookingDate; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
