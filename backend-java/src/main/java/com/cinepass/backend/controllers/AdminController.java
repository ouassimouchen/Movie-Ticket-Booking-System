package com.cinepass.backend.controllers;

import com.cinepass.backend.entity.User;
import com.cinepass.backend.payload.response.MessageResponse;
import com.cinepass.backend.repository.BookingRepository;
import com.cinepass.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/admin")
public class AdminController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    BookingRepository bookingRepository;

    @GetMapping("/users")
    @PreAuthorize("hasAuthority('admin')")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @PutMapping("/users/{id}")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody User userRequest) {
        return userRepository.findById(id).map(user -> {
            user.setName(userRequest.getName());
            user.setEmail(userRequest.getEmail());
            user.setRole(userRequest.getRole());
            userRepository.save(user);
            return ResponseEntity.ok(new MessageResponse("User updated successfully"));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/users/{id}")
    @PreAuthorize("hasAuthority('admin')")
    @javax.transaction.Transactional
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        if (!userRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        try {
           bookingRepository.deleteByUserId(id);
           userRepository.deleteById(id);
           return ResponseEntity.ok(new MessageResponse("User deleted successfully with all bookings"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error deleting user: " + e.getMessage()));
        }
    }

    @GetMapping("/bookings")
    @PreAuthorize("hasAuthority('admin')")
    public List<com.cinepass.backend.entity.Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    @DeleteMapping("/bookings/{id}")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<?> deleteBooking(@PathVariable String id) {
        if (!bookingRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        bookingRepository.deleteById(id);
        return ResponseEntity.ok(new MessageResponse("Booking deleted successfully"));
    }
}
