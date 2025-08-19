package ru.sportlive.mvp.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ru.sportlive.mvp.dto.output.SeasonTicketDTO;

import java.util.List;
import java.util.UUID;

@Entity
public class SeasonTicket {

    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Getter
    @Setter
    private UUID uuid;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private String description;

    @Getter
    @Setter
    private Integer sum;

    @Getter
    @Setter
    private String dayOfWeek;

    @Getter
    @Setter
    private String time;

    @Getter
    @Setter
    private Integer trainings;

    @Getter
    @Setter
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "couch_id")
    private Couch couch;


    @Getter
    @Setter
    @JsonManagedReference
    @OneToMany
    @JoinColumn(name = "user_id")
    private List<User> user;

    @Getter
    @Setter
    @JsonBackReference
    @ManyToOne()
    @JoinColumn(name = "sport_section_id")
    private SportSection sportSection;

    public SeasonTicket() {

    }

    public SeasonTicketDTO getSeasonTicketDTO() {
        return new SeasonTicketDTO(id,uuid,name,description,sum,dayOfWeek,time, trainings,couch.getCouchInfo(),sportSection.getSportSectionAllDTO());
    }

    public SeasonTicket(UUID uuid, String name, String description, Integer sum, String dayOfWeek, String time, Integer trainings, Couch couch, SportSection sportSection) {
        this.uuid = uuid;
        this.name = name;
        this.description = description;
        this.sum = sum;
        this.dayOfWeek = dayOfWeek;
        this.time = time;
        this.trainings = trainings;
        this.couch = couch;
        this.sportSection = sportSection;
    }
}
