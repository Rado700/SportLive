package ru.sportlive.mvp.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sportlive.mvp.dto.input.ScheduleDTO;
import ru.sportlive.mvp.models.Couch;
import ru.sportlive.mvp.models.Schedule;
import ru.sportlive.mvp.models.SportSection;
import ru.sportlive.mvp.services.CouchService;
import ru.sportlive.mvp.services.ScheduleService;
import ru.sportlive.mvp.services.SportSectionService;
import ru.sportlive.mvp.services.SportService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/schedule")
public class ScheduleController {
    @Autowired
    ScheduleService scheduleService;

    @Autowired
    CouchService couchService;

    @Autowired
    SportSectionService sportSectionService;

    @Operation(summary = "Вывести расписание по id")
    @GetMapping("/{id}")
    public ResponseEntity<Schedule> getSchedule(@PathVariable Integer id) {
        Schedule schedule = scheduleService.getSchedule(id);
        return new ResponseEntity<>(schedule, HttpStatus.OK);
    }

    @Operation(summary = "Добавить расписание", description = "Добавить расписание по спортсекций id")
    @PostMapping("/{sportSectionId}")
    public ResponseEntity<Schedule> addSchedule(@PathVariable Integer sportSectionId, @RequestBody ScheduleDTO scheduleDTO, HttpSession httpSession) {
        Integer couchId = (Integer) httpSession.getAttribute("couchId");
        Couch couch = couchService.getCouch(couchId);
        SportSection section = sportSectionService.getSportSection(sportSectionId);
        if (couch == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        Schedule schedule = scheduleService.addSchedule(scheduleDTO.getPlace(), scheduleDTO.getDescription(), scheduleDTO.getDate(), couch,section);
        return new ResponseEntity<>(schedule, HttpStatus.OK);
    }

    @Operation(summary = "Вывести все расписание")
    @GetMapping("/")
    public ResponseEntity<List<Schedule>> getAllSchedule() {
        List<Schedule> schedule = scheduleService.getAllSchedule();
        return new ResponseEntity<>(schedule, HttpStatus.OK);
    }

    @Operation(summary = "Удалить расписание по id")
    @DeleteMapping("/{id}")
    public ResponseEntity<Schedule> deleteSchedule(@PathVariable Integer id) {
        Schedule schedule = scheduleService.deleteSchedule(id);
        return new ResponseEntity<>(schedule, HttpStatus.OK);
    }

    @Operation(summary = "Вывести расписание у тренера по id")
    @GetMapping("/couch/{id}")
    public ResponseEntity<List<Schedule>> getScheduleCouch(@PathVariable Integer id) {
        Couch couch = couchService.getCouch(id);
        if (couch == null) {
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
        List<Schedule> schedule = scheduleService.getScheduleCouch(couch);
        return new ResponseEntity<>(schedule, HttpStatus.OK);
    }

    @Operation(summary = "Вывести расписание у тренера")
    @GetMapping("/couch/")
    public ResponseEntity<List<Schedule>> getScheduleCouch(HttpSession httpSession) {
        Integer couch_id = (Integer) httpSession.getAttribute("couchId");
        Couch couch = couchService.getCouch(couch_id);
        if (couch == null) {
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
        List<Schedule> schedule = scheduleService.getScheduleCouch(couch);
        return new ResponseEntity<>(schedule, HttpStatus.OK);
    }

    @Operation(summary = "Вывести расписание у тренера  и спортсекций")
    @GetMapping("/couch/sport-section/{couch_id}/{sportSection_id}")
    public ResponseEntity<List<Schedule>> getScheduleCouch(@PathVariable Integer couch_id, @PathVariable Integer sportSection_id) {
        Couch couch = couchService.getCouch(couch_id);
        SportSection section = sportSectionService.getSportSection(sportSection_id);
        if (couch == null) {
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
        if (section == null) {
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
        List<Schedule>newSchedule = new ArrayList<>();
        List<Schedule> schedule = scheduleService.getScheduleCouch(couch);
        for (Schedule schedules : schedule) {
            if (schedules.getSportSection() == section){
                newSchedule.add(schedules);
            }
        }

        return new ResponseEntity<>(newSchedule, HttpStatus.OK);
    }

    @Operation(summary = "Обновления расписания тренера")
    @PutMapping("/{id}")
    public ResponseEntity<Schedule> updateSchedule(@PathVariable Integer id, @RequestBody ScheduleDTO scheduleDTO) {
        Schedule schedule = scheduleService.getSchedule(id);
        schedule = scheduleService.updateToSchedule(schedule, scheduleDTO);
        return new ResponseEntity<>(schedule, HttpStatus.OK);
    }

}