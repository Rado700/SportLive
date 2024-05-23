package ru.sportlive.mvp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sportlive.mvp.dto.input.ScheduleDTO;
import ru.sportlive.mvp.models.Couch;
import ru.sportlive.mvp.models.Schedule;
import ru.sportlive.mvp.repository.CouchRepository;
import ru.sportlive.mvp.repository.ScheduleRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ScheduleService {

    @Autowired
    ScheduleRepository scheduleRepository;

    public Schedule getSchedule(Integer schedule_id) {
        return scheduleRepository.findById(schedule_id).orElse(null);
    }
    public Schedule addSchedule(String place, String description, Date date, Couch couch_id){
        Schedule schedule = new Schedule(place,description,date,couch_id);
        scheduleRepository.save(schedule);
        return schedule;
    }


    public Schedule deleteSchedule(Integer id){
        Schedule schedule = getSchedule(id);
        scheduleRepository.delete(schedule);
        return schedule;
    }
    public List<Schedule>getAllSchedule(){
        return scheduleRepository.findAll();
    }

    public List<Schedule> getScheduleCouch(Couch couch){
        Set<Schedule>schedules = couch.getSchedules();
        return schedules.stream().sorted().collect(Collectors.toList());

    }
    public Schedule updateToSchedule(Schedule schedule , ScheduleDTO scheduleDTO){
        schedule.setPlace(scheduleDTO.getPlace());
        schedule.setDescription(scheduleDTO.getDescription());
        schedule.setDate(scheduleDTO.getDate());
        scheduleRepository.save(schedule);
        return schedule;
    }

}
