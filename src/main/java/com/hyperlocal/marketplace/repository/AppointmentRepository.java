package com.hyperlocal.marketplace.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.hyperlocal.marketplace.model.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    List<Appointment> findByProviderId(Long providerId);

    boolean existsByProviderIdAndDateAndStartTimeLessThanAndEndTimeGreaterThan(
            Long providerId,
            LocalDate date,
            LocalTime startTime,
            LocalTime endTime
    );

    List<Appointment> findByFreelancerEmail(String freelancerEmail);

    List<Appointment> findByClientEmail(String clientEmail);
}