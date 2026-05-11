package com.hyperlocal.marketplace.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.hyperlocal.marketplace.model.Booking;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByFreelancerEmail(String freelancerEmail); // ← NEW
}