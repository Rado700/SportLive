package ru.sportlive.mvp.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import ru.sportlive.mvp.dto.output.SeasonTicketDTO;
import ru.sportlive.mvp.dto.output.SportInfoDTO;

import java.sql.Time;
import java.time.LocalTime;
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
    private String days;

    @Getter
    @Setter
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "couch_id")
    private Couch couch;


    @Getter
    @Setter
    @JsonBackReference
    @ManyToOne()
    @JoinColumn(name = "sport_section_id")
    private SportSection sportSection;

    public SeasonTicket() {

    }

    public SeasonTicketDTO getSeasonTicketDTO() {
        return new SeasonTicketDTO(id,uuid,name,description,sum,dayOfWeek,time,days,sportSection.getSportSectionAllDTO(),couch.getCouchInfo());
    }

    public SeasonTicket(Integer id, UUID uuid, String name, String description, Integer sum, String dayOfWeek, String time,String days, Couch couch, SportSection sportSection) {
        this.id = id;
        this.uuid = uuid;
        this.name = name;
        this.description = description;
        this.sum = sum;
        this.dayOfWeek = dayOfWeek;
        this.time = time;
        this.days = days;
        this.couch = couch;
        this.sportSection = sportSection;
    }
}
