package ru.sportlive.mvp.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ru.sportlive.mvp.dto.output.GetScheduleDateUser;
import ru.sportlive.mvp.dto.output.UserInfoDTO;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class Schedule implements Comparable<Schedule> {
    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Getter
    @Setter
    private String place;
    @Getter
    @Setter
    private String description;
    @Getter
    @Setter
    private LocalDateTime date;

    @Getter
    @Setter
    private String typeWorkout;

    @Getter
    @Setter
    private Integer sum;

    @Getter
    @Setter
    private String exercise;


    @Getter
    @Setter
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "couch_id")
    private Couch couch;

    @Getter
    @Setter
    @JsonManagedReference
    @OneToMany(mappedBy = "schedules", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Booking> bookings;


    @Getter
    @Setter
    @JsonBackReference
    @ManyToOne()
    @JoinColumn(name = "sport_section_id")
    private SportSection sportSection;


    public Schedule() {
    }

    public Schedule(String exercise) {
        this.exercise = exercise;
    }

    public Schedule(String place, String description, LocalDateTime date, Couch couch, SportSection sportSection, String typeWorkout, Integer sum) {
        this.place = place;
        this.description = description;
        this.date = date;
        this.couch = couch;
        this.sportSection = sportSection;
        this.typeWorkout = typeWorkout;
        this.sum = sum;
    }


    @Override
    public int compareTo(Schedule o) {
        return o.date.compareTo(date);
    }

    public GetScheduleDateUser getScheduleDateUser (){
        List<UserInfoDTO> userBooking = null;
        if (this.bookings != null){
           userBooking = bookings.stream().map(booking -> booking.getUser().getUserInfo()).collect(Collectors.toList());

        }
        return new GetScheduleDateUser(id,place,date,couch.getCouchInfo(),userBooking);
    }
}
