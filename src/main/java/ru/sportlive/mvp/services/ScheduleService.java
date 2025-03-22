package ru.sportlive.mvp.services;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sportlive.mvp.dto.input.ScheduleAddExerciseDTO;
import ru.sportlive.mvp.dto.input.ScheduleDTO;
import ru.sportlive.mvp.models.Booking;
import ru.sportlive.mvp.models.Couch;
import ru.sportlive.mvp.models.Schedule;
import ru.sportlive.mvp.models.SportSection;
import ru.sportlive.mvp.repository.BookingRepository;
import ru.sportlive.mvp.repository.ScheduleRepository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
@Transactional
@Service
public class ScheduleService {

    @Autowired
    ScheduleRepository scheduleRepository;

    @Autowired
    BookingRepository bookingRepository;


    public Schedule getSchedule(Integer schedule_id) {
        return scheduleRepository.findById(schedule_id).orElseThrow(()->new EntityNotFoundException("Расписание с id " + schedule_id + " не найдено"));
    }

    public List<Schedule> getScheduleTypeWorkout(String type){
        List<Schedule> schedule = scheduleRepository.findAll();
        return schedule.stream().filter(s -> s.getTypeWorkout().equals(type)).collect(Collectors.toList());
    }

    public Schedule addSchedule(String place, String description, String typeWorkout, LocalDateTime date, Couch couch_id, SportSection section_id, Integer sum){
        Schedule schedule = new Schedule(place,description,date,couch_id,section_id,typeWorkout,sum);
        scheduleRepository.save(schedule);
        return schedule;
    }


    public void deleteSchedule(Integer id) {
        Schedule schedule = getSchedule(id);
        List<Booking> getBookings = new ArrayList<>(schedule.getBookings()); // Копируем список, чтобы избежать ConcurrentModificationException

        // Удаляем связанные бронирования
        for (Booking booking : getBookings) {
            bookingRepository.delete(booking);
        }

        // Теперь можно удалить сам Schedule
        scheduleRepository.delete(schedule);
    }


    public List<Schedule>getAllSchedule(){
        return scheduleRepository.findAll();
    }


    public List<Schedule> getScheduleCouch(Couch couch){
        Set<Schedule>schedules = couch.getSchedules();
        return schedules.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());

    }
    public Schedule updateToSchedule(Schedule schedule , ScheduleDTO scheduleDTO){
        schedule.setPlace(scheduleDTO.getPlace());
        schedule.setDescription(scheduleDTO.getDescription());
        schedule.setDate(scheduleDTO.getDate());
        scheduleRepository.save(schedule);
        return schedule;
    }

        public String getExercise(Integer id){
        Optional<Schedule> exercise = scheduleRepository.findById(id);
        return exercise.map(Schedule::getExercise).orElse(null);
    }

        public void addExercise(Integer schedule_id, ScheduleAddExerciseDTO exercise){
        Schedule schedule = scheduleRepository.findById(schedule_id).orElse(null);
        schedule.setExercise(String.valueOf(exercise));
        scheduleRepository.save(schedule);
    }

}
