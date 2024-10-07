package ru.sportlive.mvp.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ru.sportlive.mvp.dto.output.BookingUserCouchDTO;

@Entity
public class Booking {
    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Getter
    @Setter
    @JsonBackReference
    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Schedule schedule;

    @Getter
    @Setter
    @JsonBackReference
    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id",unique = false)
    private User user;

//    @Getter
//    @Setter
//    @JsonBackReference
//    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
//    @JoinColumn(name = "couch_id", foreignKey = @ForeignKey(
//            foreignKeyDefinition = "FOREIGN KEY (couch_id) REFERENCES Couch ON DELETE CASCADE ON UPDATE CASCADE"
//    ))
//    private Couch couch;


    public Booking() {
    }

    public Booking(Schedule schedule, User user) {
        this.schedule = schedule;
        this.user = user;
    }

    public BookingUserCouchDTO getBookingUserCouch(){
        return new BookingUserCouchDTO(id,schedule.getCouch().getCouchInfo(),user.getUserInfo(),schedule);
    }




}
