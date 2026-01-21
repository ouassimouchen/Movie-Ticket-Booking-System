package com.cinepass.backend.controllers;

import com.cinepass.backend.entity.Movie;
import com.cinepass.backend.entity.Showtime;
import com.cinepass.backend.repository.MovieRepository;
import com.cinepass.backend.repository.ShowtimeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/movies")
public class MovieController {

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    ShowtimeRepository showtimeRepository;

    @Autowired
    ObjectMapper objectMapper;

    @GetMapping
    public List<Map<String, Object>> getAllMovies() throws Exception {
        List<Movie> movies = movieRepository.findAll();
        List<Map<String, Object>> response = new ArrayList<>();
        for (Movie m : movies) {
            response.add(convertMovieToMap(m));
        }
        return response;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getMovieById(@PathVariable Long id) throws Exception {
        return movieRepository.findById(id).map(m -> {
            try {
                return ResponseEntity.ok(convertMovieToMap(m));
            } catch (Exception e) {
                return ResponseEntity.internalServerError().build();
            }
        }).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/showtimes")
    public List<Showtime> getShowtimes(@PathVariable Long id) {
        return showtimeRepository.findByMovieId(id);
    }

    @GetMapping("/showtimes/{id}")
    public ResponseEntity<Showtime> getShowtimeById(@PathVariable Long id) {
        return showtimeRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    private Map<String, Object> convertMovieToMap(Movie m) throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("id", m.getId());
        map.put("title", m.getTitle());
        map.put("poster", m.getPoster());
        map.put("backdrop", m.getBackdrop());
        map.put("rating", m.getRating());
        map.put("duration", m.getDuration());
        try {
            map.put("genre", m.getGenre() != null && !m.getGenre().isEmpty() ? 
                objectMapper.readValue(m.getGenre(), List.class) : new ArrayList<>());
        } catch (Exception e) {
            map.put("genre", new ArrayList<>());
        }
        
        map.put("releaseDate", m.getReleaseDate());
        map.put("imdbRating", m.getImdbRating());
        map.put("description", m.getDescription());
        
        try {
            map.put("cast", m.getCast() != null && !m.getCast().isEmpty() ? 
                objectMapper.readValue(m.getCast(), List.class) : new ArrayList<>());
        } catch (Exception e) {
            map.put("cast", new ArrayList<>());
        }
        map.put("trailer", m.getTrailer());
        return map;
    }
}
