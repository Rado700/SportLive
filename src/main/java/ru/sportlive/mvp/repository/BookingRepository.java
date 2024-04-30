package ru.sportlive.mvp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sportlive.mvp.models.Booking;

public interface BookingRepository extends JpaRepository<Booking,Integer> {
}
