package com.cinepass.backend.controllers;

import com.cinepass.backend.entity.Booking;
import com.cinepass.backend.payload.request.BookingRequest;
import com.cinepass.backend.payload.response.MessageResponse;
import com.cinepass.backend.repository.BookingRepository;
import com.cinepass.backend.security.services.UserDetailsImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/bookings")
public class BookingController {
    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    ObjectMapper objectMapper;

    @PostMapping
    public ResponseEntity<?> createBooking(@RequestBody BookingRequest bookingRequest) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Booking booking = new Booking();
        booking.setId(bookingRequest.getId());
        booking.setUserId(userDetails.getId());
        booking.setMovieId(bookingRequest.getMovie_id());
        booking.setMovieTitle(bookingRequest.getMovie_title());
        booking.setMoviePoster(bookingRequest.getMovie_poster());
        booking.setSessionHall(bookingRequest.getSession_hall());
        booking.setSessionTime(bookingRequest.getSession_time());
        booking.setTotal(bookingRequest.getTotal());
        booking.setStatus("Confirmed");

        try {
            booking.setSeats(objectMapper.writeValueAsString(bookingRequest.getSeats()));
        } catch (JsonProcessingException e) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error processing seats data"));
        }

        bookingRepository.save(booking);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Booking successful");
        response.put("bookingId", booking.getId());

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<?> getUserBookings() {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Booking> bookings = bookingRepository.findByUserIdOrderByBookingDateDesc(userDetails.getId());

        List<Map<String, Object>> parsedBookings = new ArrayList<>();
        for (Booking b : bookings) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", b.getId());
            map.put("user_id", b.getUserId());
            map.put("movie_id", b.getMovieId());
            map.put("movie_title", b.getMovieTitle());
            map.put("movie_poster", b.getMoviePoster());
            map.put("session_hall", b.getSessionHall());
            map.put("session_time", b.getSessionTime());
            map.put("total", b.getTotal());
            map.put("booking_date", b.getBookingDate());
            map.put("status", b.getStatus());

            try {
                map.put("seats", objectMapper.readValue(b.getSeats(), List.class));
            } catch (JsonProcessingException e) {
                map.put("seats", new ArrayList<>());
            }

            // Structure matching Node.js backend
            Map<String, String> movie = new HashMap<>();
            movie.put("title", b.getMovieTitle());
            movie.put("poster", b.getMoviePoster());
            map.put("movie", movie);

            Map<String, String> session = new HashMap<>();
            session.put("hall", b.getSessionHall());
            session.put("time", b.getSessionTime());
            map.put("session", session);

            parsedBookings.add(map);
        }

        return ResponseEntity.ok(parsedBookings);
    }
}
