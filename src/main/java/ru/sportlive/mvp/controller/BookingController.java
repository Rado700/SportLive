package ru.sportlive.mvp.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sportlive.mvp.dto.input.BookingDTO;
import ru.sportlive.mvp.dto.output.BookingUserCouchDTO;
import ru.sportlive.mvp.dto.output.GetScheduleDateUser;
import ru.sportlive.mvp.models.Booking;
import ru.sportlive.mvp.models.Couch;
import ru.sportlive.mvp.models.Schedule;
import ru.sportlive.mvp.models.User;
import ru.sportlive.mvp.services.BookingService;
import ru.sportlive.mvp.services.CouchService;
import ru.sportlive.mvp.services.ScheduleService;
import ru.sportlive.mvp.services.UserService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/booking")
public class BookingController {
    @Autowired
    BookingService bookingService;
    @Autowired
    UserService userService;
    @Autowired
    ScheduleService scheduleService;

    @Autowired
    CouchService couchService;

    @Operation(summary = "Вывести бронь по id")
    @GetMapping("/{id}")
    public ResponseEntity<Booking>getBooking(Integer id) {
        Booking booking = bookingService.getBooking(id);
        return new ResponseEntity<>(booking, HttpStatus.OK);
    }
    @Operation(summary = "Добавить бронь",description = "Добавляем бронь по user,добавляем в расписание")
    @PostMapping("/")
    public ResponseEntity<Booking>addBooking(@RequestBody BookingDTO bookingDTO, HttpSession httpSession){
        Integer id = (Integer) httpSession.getAttribute("userId");
        User user = userService.getUser(id);
        Schedule schedule = scheduleService.getSchedule(bookingDTO.getSchedule_id());
        Booking booking = bookingService.addBooking(schedule,user);
        return new ResponseEntity<>(booking,HttpStatus.OK);
    }

    @Operation(summary = "Удаление брони по id")
    @DeleteMapping("/{id}")
    public ResponseEntity<Booking>deleteBooking(@PathVariable Integer id){
        Booking booking = bookingService.deleteBooking(id);
        return new ResponseEntity<>(booking,HttpStatus.OK);
    }

    @Operation(summary = "Вывести все брони")
    @GetMapping("/")
    public ResponseEntity<List<Booking>>getAllBookings(){
        List<Booking> bookings = bookingService.getAllBookings();
        return new ResponseEntity<>(bookings,HttpStatus.OK);
    }

    @Operation(summary = "Все брони пользователя по id")
    @GetMapping("/user/{id}")
    public ResponseEntity<List<Booking>>getUserBooking(@PathVariable Integer id){
        List<Booking> booking = bookingService.getUserBookings(id);
        return new ResponseEntity<>(booking,HttpStatus.OK);
    }
    @Operation(summary = "Все брони пользователя")
    @GetMapping("/getAllBookingUser")
    public ResponseEntity<List<Booking>>getUserBooking(HttpSession httpSession){
        Integer id = (Integer) httpSession.getAttribute("userId");
        List<Booking> booking = bookingService.getUserBookings(id);
        return new ResponseEntity<>(booking,HttpStatus.OK);
    }

    @Operation(summary = "Все брони тренера по id")
    @GetMapping("/couch/{id}")
    public ResponseEntity<List<BookingUserCouchDTO>>getCouchBookingBySchedule(@PathVariable Integer id){
        Couch couch = couchService.getCouch(id);
        List<Schedule>schedules = scheduleService.getScheduleCouch(couch);
        List<Booking>bookings = bookingService.getCouchBookingBySchedules(schedules);
        return new ResponseEntity<>(bookings.stream().map(Booking::getBookingUserCouch).collect(Collectors.toList()), HttpStatus.OK);
    }

    @Operation(summary = "Все брони тренера")
    @GetMapping("/couchBooking")
    public ResponseEntity<List<BookingUserCouchDTO>>getCouchBookingBySchedule(HttpSession httpSession){
        Integer id = (Integer) httpSession.getAttribute("couchId");
        Couch couch = couchService.getCouch(id);
        List<Schedule>schedules = scheduleService.getScheduleCouch(couch);
        List<Booking>bookings = bookingService.getCouchBookingBySchedules(schedules);
        return new ResponseEntity<>(bookings.stream().map(Booking::getBookingUserCouch).collect(Collectors.toList()), HttpStatus.OK);
    }


    @Operation(summary = "Вывод всех броней которые были проведены тренером для user")
    @GetMapping("/couch/user/{couchId}/{userId}")
    public ResponseEntity<List<GetScheduleDateUser>>getBookingCouchForUser(@PathVariable Integer couchId, @PathVariable Integer userId){
        Couch couch = couchService.getCouch(couchId);
        List<Booking> bookingUser = bookingService.getUserBookings(userId);
        List<Schedule>scheduleList = bookingService.getAllSchedulesCouchByUser(bookingUser,couch);
        return new ResponseEntity<>(scheduleList.stream().map(Schedule::getScheduleDateUser).collect(Collectors.toList()),HttpStatus.OK);
    }
}

//GET /booking/couch/user/{couchId}/{userId} - выводить список всех броней, которые были проведены тренером с couchId для юзера userId