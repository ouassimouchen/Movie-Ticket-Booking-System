package com.cinepass.backend.entity;

import javax.persistence.*;

@Entity
@Table(name = "showtimes")
public class Showtime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;

    private String time;
    private String hall;
    private Double price;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Movie getMovie() { return movie; }
    public void setMovie(Movie movie) { this.movie = movie; }
    public String getTime() { return time; }
    public void setTime(String time) { this.time = time; }
    public String getHall() { return hall; }
    public void setHall(String hall) { this.hall = hall; }
    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }
}
