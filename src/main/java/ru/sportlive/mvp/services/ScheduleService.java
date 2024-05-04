package ru.sportlive.mvp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sportlive.mvp.models.Couch;
import ru.sportlive.mvp.models.Schedule;
import ru.sportlive.mvp.repository.ScheduleRepository;

import java.util.Date;
import java.util.List;

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

}
