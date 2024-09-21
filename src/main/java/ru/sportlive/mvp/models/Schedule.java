package ru.sportlive.mvp.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ru.sportlive.mvp.dto.output.GetScheduleDateUser;
import ru.sportlive.mvp.dto.output.UserInfoDTO;

import java.sql.Timestamp;

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
    private Timestamp date;

    @Getter
    @Setter
    private String typeWorkout;

    @Getter
    @Setter
    @JsonBackReference
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "couch_id")
    private Couch couch;

    @Getter
    @Setter
    @JsonManagedReference
    @OneToOne(mappedBy = "schedule",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Booking booking;

    @Getter
    @Setter
    @JsonBackReference
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "sport_section_id")
    private SportSection sportSection;


    public Schedule() {
    }


    public Schedule(String place, String description, Timestamp date, Couch couch, SportSection sportSection, String typeWorkout) {
        this.place = place;
        this.description = description;
        this.date = date;
        this.couch = couch;
        this.sportSection = sportSection;
        this.typeWorkout = typeWorkout;
    }




    @Override
    public int compareTo(Schedule o) {
        return o.date.compareTo(date);
    }

    public GetScheduleDateUser getScheduleDateUser (){
        UserInfoDTO userBooking = null;
        if (this.booking != null){
            userBooking = booking.getUser().getUserInfo();
        }
        return new GetScheduleDateUser(id,place, date,couch.getCouchInfo(),userBooking);
    }
}
