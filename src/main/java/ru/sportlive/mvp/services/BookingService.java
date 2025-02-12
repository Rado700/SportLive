package ru.sportlive.mvp.services;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sportlive.mvp.models.Booking;
import ru.sportlive.mvp.models.Couch;
import ru.sportlive.mvp.models.Schedule;
import ru.sportlive.mvp.models.User;
import ru.sportlive.mvp.repository.BookingRepository;
import ru.sportlive.mvp.repository.ScheduleRepository;
import ru.sportlive.mvp.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
@Transactional
@Service
public class BookingService {

    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ScheduleRepository scheduleRepository;

    public Booking getBooking (Integer id){
        return bookingRepository.findById(id).orElse(null);
    }

    public Booking addBooking(Schedule schedule, User user){
        Booking booking = null;
        if (schedule.getBookings().isEmpty()) {
            booking = new Booking(user, schedule);
            bookingRepository.save(booking);
        }
        return booking;
    }

    public Booking deleteBooking (Integer id){
        Booking booking = getBooking(id);
        bookingRepository.delete(booking);
        return booking;
    }

    public void deleteBookingByScheduleForUser(Integer id, User user){
        Schedule schedule = scheduleRepository.findById(id).orElse(null);
        List<Booking> bookingsToRemove = new ArrayList<>();

        for (Booking book : schedule.getBookings()) {
            if (book.getUser().equals(user)) {
                bookingsToRemove.add(book);
            }
        }

        if (!bookingsToRemove.isEmpty()) {
            for (Booking book : bookingsToRemove) {
                schedule.getBookings().remove(book); // Удаляем из списка бронирований расписания
                bookingRepository.delete(book); // Удаляем из базы
            }
            scheduleRepository.save(schedule); // Сохраняем обновленное расписание
        }
    }
//    public Booking deleteBookingSchedule (Integer schedule_id,Integer userId){
//        List<Booking>bookings = bookingRepository.findByScheduleId(schedule_id);
//        bookings = bookings.stream().filter(booking1 -> Objects.equals(booking1.getUser().getId(), userId)).collect(Collectors.toList());
//        Booking booking = bookings.get(0);
//        bookingRepository.delete(booking);
//        return booking;
//    }
//
//    public List<Booking> deleteAllBookingSchedule (Integer schedule_id){
//        List<Booking>bookings = bookingRepository.findByScheduleId(schedule_id);
//        bookingRepository.deleteAll(bookings);
//        return bookings;
//    }

    public List<Booking> getAllBookings(){
        return bookingRepository.findAll();
    }

    public List<Booking> getUserBookings(Integer id){
        Optional<User> user = userRepository.findById(id);
        return user.map(User::getBooking).orElse(null);
    }

    public List<Booking>getCouchBookingBySchedules(List<Schedule> schedules){
        List<Booking> bookings = new ArrayList<>();
        for (Schedule schedule : schedules) {
            List<Booking> bookings2 = schedule.getBookings();
            System.out.println("Бронирования для расписания: "+ bookings2);
            if (bookings2 != null && !bookings2.isEmpty()) {
                Booking booking = bookings2.get(0);
                bookings.add(booking);
            }
        }
        return bookings;
    }

    public List<Schedule>getAllSchedulesCouchByUser(List<Booking>bookings,Couch couch){
        List<Schedule>schedules = new ArrayList<>();
        for (Booking booking: bookings) {
            Schedule schedule = booking.getSchedules();
            Couch couch2 = schedule.getCouch();
            if (schedule != null && couch2 == couch ){
                schedules.add(schedule);
            }
        }
        return schedules;
    }

}
