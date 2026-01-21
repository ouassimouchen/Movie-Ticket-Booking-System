package com.cinepass.backend.repository;

import com.cinepass.backend.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, String> {
    List<Booking> findByUserIdOrderByBookingDateDesc(Long userId);
    void deleteByUserId(Long userId);
}
