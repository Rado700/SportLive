package ru.sportlive.mvp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sportlive.mvp.dto.BookingDTO;
import ru.sportlive.mvp.models.Booking;
import ru.sportlive.mvp.models.Schedule;
import ru.sportlive.mvp.models.User;
import ru.sportlive.mvp.services.BookingService;
import ru.sportlive.mvp.services.ScheduleService;
import ru.sportlive.mvp.services.UserService;

@RestController
@RequestMapping("/booking")
public class BookingController {
    @Autowired
    BookingService bookingService;
    @Autowired
    UserService userService;
    @Autowired
    ScheduleService scheduleService;

    @GetMapping("/{id}")
    public ResponseEntity<Booking>getBooking(Integer id) {
        Booking booking = bookingService.getBooking(id);
        return new ResponseEntity<>(booking, HttpStatus.OK);
    }
    @PostMapping("/")
    public ResponseEntity<Booking>addBooking(@RequestBody BookingDTO bookingDTO){
        User user = userService.getUser(bookingDTO.getUser_id());
        Schedule schedule = scheduleService.getSchedule(bookingDTO.getSchedule_id());
        Booking booking = bookingService.addBooking(schedule,user);
        return new ResponseEntity<>(booking,HttpStatus.OK);
    }
}
