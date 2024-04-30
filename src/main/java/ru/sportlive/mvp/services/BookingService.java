package ru.sportlive.mvp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sportlive.mvp.models.Booking;
import ru.sportlive.mvp.models.Schedule;
import ru.sportlive.mvp.models.User;
import ru.sportlive.mvp.repository.BookingRepository;

@Service
public class BookingService {

    @Autowired
    BookingRepository bookingRepository;



    public Booking getBooking (Integer id){
        return bookingRepository.findById(id).orElse(null);
    }

    public Booking addBooking(Schedule schedule, User user){
        Booking booking = new Booking(schedule,user);
        bookingRepository.save(booking);
        return booking;
    }
}
