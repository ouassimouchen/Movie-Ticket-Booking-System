package com.cinepass.backend.controllers;

import com.cinepass.backend.entity.Movie;
import com.cinepass.backend.entity.Showtime;
import com.cinepass.backend.payload.response.MessageResponse;
import com.cinepass.backend.repository.MovieRepository;
import com.cinepass.backend.repository.ShowtimeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/admin/movies")
public class AdminMovieController {

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    ShowtimeRepository showtimeRepository;

    @Autowired
    ObjectMapper objectMapper;

    @PostMapping
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<?> addMovie(@RequestBody Movie movie) {
        Movie savedMovie = movieRepository.save(movie);
        return ResponseEntity.ok(savedMovie);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<?> updateMovie(@PathVariable Long id, @RequestBody Movie movieRequest) {
        return movieRepository.findById(id).map(movie -> {
            movie.setTitle(movieRequest.getTitle());
            movie.setPoster(movieRequest.getPoster());
            movie.setBackdrop(movieRequest.getBackdrop());
            movie.setRating(movieRequest.getRating());
            movie.setDuration(movieRequest.getDuration());
            movie.setGenre(movieRequest.getGenre());
            movie.setReleaseDate(movieRequest.getReleaseDate());
            movie.setImdbRating(movieRequest.getImdbRating());
            movie.setDescription(movieRequest.getDescription());
            movie.setCast(movieRequest.getCast());
            movie.setTrailer(movieRequest.getTrailer());
            movieRepository.save(movie);
            return ResponseEntity.ok(new MessageResponse("Movie updated successfully"));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<?> deleteMovie(@PathVariable Long id) {
        movieRepository.deleteById(id);
        return ResponseEntity.ok(new MessageResponse("Movie deleted successfully"));
    }

    @PostMapping("/{id}/showtimes")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<?> addShowtime(@PathVariable Long id, @RequestBody Showtime showtime) {
        return movieRepository.findById(id).map(movie -> {
            showtime.setMovie(movie);
            showtimeRepository.save(showtime);
            return ResponseEntity.ok(new MessageResponse("Showtime added successfully"));
        }).orElse(ResponseEntity.notFound().build());
    }
}
