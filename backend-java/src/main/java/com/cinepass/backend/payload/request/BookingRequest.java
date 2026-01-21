package com.cinepass.backend.payload.request;

import java.util.List;
import java.util.Map;

public class BookingRequest {
    private String id;
    private Integer movie_id;
    private String movie_title;
    private String movie_poster;
    private String session_hall;
    private String session_time;
    private List<Map<String, Object>> seats; // Changed to Map to handle JSON objects
    private Double total;

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public Integer getMovie_id() { return movie_id; }
    public void setMovie_id(Integer movie_id) { this.movie_id = movie_id; }
    public String getMovie_title() { return movie_title; }
    public void setMovie_title(String movie_title) { this.movie_title = movie_title; }
    public String getMovie_poster() { return movie_poster; }
    public void setMovie_poster(String movie_poster) { this.movie_poster = movie_poster; }
    public String getSession_hall() { return session_hall; }
    public void setSession_hall(String session_hall) { this.session_hall = session_hall; }
    public String getSession_time() { return session_time; }
    public void setSession_time(String session_time) { this.session_time = session_time; }
    public List<Map<String, Object>> getSeats() { return seats; }
    public void setSeats(List<Map<String, Object>> seats) { this.seats = seats; }
    public Double getTotal() { return total; }
    public void setTotal(Double total) { this.total = total; }
}
