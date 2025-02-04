package ru.sportlive.mvp.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ru.sportlive.mvp.dto.output.BookingUserCouchDTO;
import ru.sportlive.mvp.dto.output.CouchInfoDTO;

import java.util.List;

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
    @ManyToOne()
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


    @Getter
    @Setter
    @JsonBackReference
    @ManyToOne
    private Schedule schedules;


    public Booking(User user, Schedule schedules) {
        this.user = user;
        this.schedules = schedules;
    }

    public Booking() {

    }

    public BookingUserCouchDTO getBookingUserCouch(){
        CouchInfoDTO couchInfoDTO = null;
        if (schedules != null && schedules.getCouch() != null) {
            couchInfoDTO = schedules.getCouch().getCouchInfo();
        }

        return new BookingUserCouchDTO(id,couchInfoDTO,user.getUserInfo(),schedules);
    }

}
