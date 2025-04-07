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
import ru.sportlive.mvp.models.*;
import ru.sportlive.mvp.services.*;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
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
    @Autowired
    LoginService loginService;
    @Autowired
    TGService tgService;

    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    @Operation(summary = "Вывести бронь по id")
    @GetMapping("/{id}")
    public ResponseEntity<Booking>getBooking(Integer id) {
        Booking booking = bookingService.getBooking(id);
        return new ResponseEntity<>(booking, HttpStatus.OK);
    }
    @Operation(summary = "Добавить бронь",description = "Добавляем бронь по user,добавляем в расписание")
    @PostMapping("/")//TODO:Бронь на (такое то число и время)
    public ResponseEntity<Booking>addBooking(@RequestBody BookingDTO bookingDTO, HttpSession httpSession) throws IOException {

        Integer id = (Integer) httpSession.getAttribute("userId");
        Integer loginUserId = (Integer) httpSession.getAttribute("loginUserId");
        Schedule schedule = scheduleService.getSchedule(bookingDTO.getSchedule_id());
        Couch couch = schedule.getCouch();
        User user = userService.getUser(id);
        Login couchLogin = loginService.getCouchLogin(couch.getId());
        Login userLogin = loginService.getLogin(loginUserId);
        String userTelegramId = userLogin.getTelegramId();
        String couchTelegramId = couchLogin.getTelegramId();
        if (user.getBalance() < schedule.getSum()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Booking booking = bookingService.addBooking(schedule, user);
        String couchMessageText;
        if (userTelegramId != null) {
            couchMessageText = "Забронированно время на " + booking.getSchedules().getDate().format(dateTimeFormatter) + " пользователем " + "<b><a href='tg://user?id=" + userTelegramId + "'>" + user.getName() + "</a></b>";
        } else {
            couchMessageText = "Забронированно время на " + booking.getSchedules().getDate().format(dateTimeFormatter) + " пользователем " + user.getName();
        }

        String userMessageText;
        if (couchTelegramId != null) {
            userMessageText = "Забронирована тренировка на " + booking.getSchedules().getDate().format(dateTimeFormatter) + "у тренера <b><a href='tg://user?id=" + couchTelegramId + "'>" + couch.getName() + "</a></b>";
        }
        else {
            userMessageText = "Забронирована тренировка на " + booking.getSchedules().getDate().format(dateTimeFormatter) + "у тренера " + couch.getName();

        }
        tgService.sendMessage(couchTelegramId,couchMessageText);
        tgService.sendMessage(userTelegramId,userMessageText);

        return new ResponseEntity<>(booking,HttpStatus.OK);
    }

    @Operation(summary = "Удаление брони по id")
    @DeleteMapping("/{id}")
    public ResponseEntity<Booking>deleteBooking(@PathVariable Integer id){
        Booking booking = bookingService.deleteBooking(id);
        return new ResponseEntity<>(booking,HttpStatus.OK);
    }

    @Operation(summary = "Удаление брони из расписания по id")
    @DeleteMapping("/schedule/{scheduleId}")//TODO:Бронь удалена на (число и время)
    public ResponseEntity<Void>deleteBookingSchedule(@PathVariable Integer scheduleId, HttpSession httpSession) throws IOException {
        Integer userId = (Integer) httpSession.getAttribute("userId");
        Schedule schedule = scheduleService.getSchedule(scheduleId);
        Couch couch = schedule.getCouch();
        User user = userService.getUser(userId);
        Login loginUser = loginService.getUserLogin(user.getId());
        Login loginCouch = loginService.getCouchLogin(couch.getId());
        String couchTelegramId = loginCouch.getTelegramId();
        String userTelegramId = loginUser.getTelegramId();

        List<Booking> bookingsToRemove = new ArrayList<>();
        for (Booking book : schedule.getBookings()) {
            if (book.getUser().equals(user)) {
                bookingsToRemove.add(book);
            }
        }
        String messageText = "Бронь снята на "+ bookingsToRemove.get(0).getSchedules().getDate().format(dateTimeFormatter);
        String couchMessageText;
        if (userTelegramId != null) {
            couchMessageText = "Бронь снята на " + bookingsToRemove.get(0).getSchedules().getDate().format(dateTimeFormatter) + " пользователем <b><a href='tg://user?id=" + userTelegramId + "'>" + user.getName() + "</a></b>";
        } else {
            couchMessageText = "Бронь снята на " + bookingsToRemove.get(0).getSchedules().getDate().format(dateTimeFormatter) + " пользователем " + user.getName();
        }
        tgService.sendMessage(userTelegramId,messageText);
        tgService.sendMessage(couchTelegramId,couchMessageText);
        bookingService.deleteBookingByScheduleForUser(scheduleId, user);

        return ResponseEntity.ok().build();
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
    @GetMapping("/getAllBookingUser/")
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
    @GetMapping("/couchBooking/")
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