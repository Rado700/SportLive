package ru.sportlive.mvp.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ru.sportlive.mvp.dto.output.GetScheduleDateUser;

import java.util.Date;

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
    private Date date;

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


    public Schedule() {
    }

    public Schedule(String place, String description, Date date, Couch couch) {
        this.place = place;
        this.description = description;
        this.date = date;
        this.couch = couch;
    }

    @Override
    public int compareTo(Schedule o) {
        return o.date.compareTo(date);
    }

    public GetScheduleDateUser getScheduleDateUser (){
        return new GetScheduleDateUser(id,place, date,couch.getCouchInfo(),booking.getUser().getUserInfo());
    }
}
