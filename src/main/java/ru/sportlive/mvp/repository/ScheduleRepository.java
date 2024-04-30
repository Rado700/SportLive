package ru.sportlive.mvp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sportlive.mvp.models.Schedule;

public interface ScheduleRepository extends JpaRepository<Schedule,Integer> {
}
