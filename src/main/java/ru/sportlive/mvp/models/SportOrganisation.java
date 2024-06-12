package ru.sportlive.mvp.models;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@NoArgsConstructor
public class SportOrganisation {

    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Getter
    @Setter
    @JsonBackReference
    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Organisation organisation;

    @Getter
    @Setter
    @JsonBackReference
    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Sport sport;

    @Getter
    @Setter
    @JsonBackReference
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<Schedule>schedules;



    public SportOrganisation(Organisation organisation, Sport sport) {
        this.organisation = organisation;
        this.sport = sport;
    }
}
