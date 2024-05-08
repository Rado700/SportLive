package ru.sportlive.mvp.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sportlive.mvp.dto.input.ScheduleDTO;
import ru.sportlive.mvp.models.Couch;
import ru.sportlive.mvp.models.Schedule;
import ru.sportlive.mvp.services.CouchService;
import ru.sportlive.mvp.services.ScheduleService;

import java.util.List;

@RestController
@RequestMapping("/schedule")
public class ScheduleController {
    @Autowired
    ScheduleService scheduleService;

    @Autowired
    CouchService couchService;

    @Operation(summary = "Вывести расписание по id")
    @GetMapping("/{id}")
    public ResponseEntity<Schedule>getSchedule(@PathVariable Integer id){
        Schedule schedule = scheduleService.getSchedule(id);
        return new ResponseEntity<>(schedule, HttpStatus.OK);
    }

    @Operation(summary = "Добавить расписание",description = "Добавить расписание и тренера по id")
    @PostMapping("/")
    public ResponseEntity<Schedule>addSchedule(@RequestBody ScheduleDTO scheduleDTO){
        Couch couch = couchService.getCouch(scheduleDTO.getCouch_id());
        Schedule schedule = scheduleService.addSchedule(scheduleDTO.getPlace(),scheduleDTO.getDescription(),scheduleDTO.getDate(),couch);
        return new ResponseEntity<>(schedule,HttpStatus.OK);
    }

    @Operation(summary = "Вывести все расписание")
    @GetMapping("/")
    public ResponseEntity<List<Schedule>>getAllSchedule(){
        List<Schedule> schedule = scheduleService.getAllSchedule();
        return new ResponseEntity<>(schedule,HttpStatus.OK);
    }

    @Operation(summary = "Удалить расписание по id")
    @DeleteMapping("/{id}")
    public ResponseEntity<Schedule>deleteSchedule(@PathVariable Integer id){
        Schedule schedule = scheduleService.deleteSchedule(id);
        return new ResponseEntity<>(schedule,HttpStatus.OK);
    }

    @Operation(summary = "Вывести расписание у тренера")
    @GetMapping("/couch/{id}")
    public ResponseEntity<List<Schedule>>getScheduleCouch(@PathVariable Integer id){
        Couch couch = couchService.getCouch(id);
        if (couch == null){
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
        List<Schedule> schedule = scheduleService.getScheduleCouch(couch);
        return new ResponseEntity<>(schedule,HttpStatus.OK);
    }


}
