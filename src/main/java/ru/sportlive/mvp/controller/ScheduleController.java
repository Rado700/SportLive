package ru.sportlive.mvp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sportlive.mvp.dto.ScheduleDTO;
import ru.sportlive.mvp.models.Couch;
import ru.sportlive.mvp.models.Schedule;
import ru.sportlive.mvp.services.CouchService;
import ru.sportlive.mvp.services.ScheduleService;

@RestController
public class ScheduleController {
    @Autowired
    ScheduleService scheduleService;

    @Autowired
    CouchService couchService;


    @GetMapping("/getSchedule/{schedule_id}")
    public ResponseEntity<Schedule>getSchedule(@PathVariable Integer schedule_id){
        Schedule schedule = scheduleService.getSchedule(schedule_id);
        return new ResponseEntity<>(schedule, HttpStatus.OK);
    }

    @PostMapping("/addSchedule")
    public ResponseEntity<Schedule>addSchedule(@RequestBody ScheduleDTO scheduleDTO){
        Couch couch = couchService.getCouch(scheduleDTO.getCouch_id());
        Schedule schedule = scheduleService.addSchedule(scheduleDTO.getPlace(),scheduleDTO.getDescription(),scheduleDTO.getDate(),couch);
        return new ResponseEntity<>(schedule,HttpStatus.OK);
    }


}
