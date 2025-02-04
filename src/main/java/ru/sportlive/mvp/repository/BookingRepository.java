package ru.sportlive.mvp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sportlive.mvp.models.Booking;
import ru.sportlive.mvp.models.Schedule;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking,Integer> {
//    List<Booking>findByScheduleId(Integer schedule_id);

    void deleteBySchedules(Schedule schedule);
}
