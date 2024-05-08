package ru.sportlive.mvp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sportlive.mvp.models.Booking;
import ru.sportlive.mvp.models.Couch;
import ru.sportlive.mvp.models.Schedule;
import ru.sportlive.mvp.models.User;
import ru.sportlive.mvp.repository.BookingRepository;
import ru.sportlive.mvp.repository.CouchRepository;
import ru.sportlive.mvp.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookingService {

    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    UserRepository userRepository;


    public Booking getBooking (Integer id){
        return bookingRepository.findById(id).orElse(null);
    }

    public Booking addBooking(Schedule schedule, User user){
        Booking booking = new Booking(schedule,user);
        bookingRepository.save(booking);
        return booking;
    }

    public Booking deleteBooking (Integer id){
        Booking booking = getBooking(id);
        bookingRepository.delete(booking);
        return booking;
    }
    public List<Booking> getAllBookings(){
        return bookingRepository.findAll();
    }

    public List<Booking>getUserBooking(Integer id){
        Optional<User> user = userRepository.findById(id);
        return user.map(User::getBooking).orElse(null);
    }

    public List<Booking>getCouchBookingBySchedules(List<Schedule> schedules){
        List<Booking> bookings = new ArrayList<>();
        for (Schedule schedule : schedules) {
            Booking booking = schedule.getBooking();
            if (booking != null) {
                bookings.add(booking);
            }
        }
        return bookings;

    }

}
