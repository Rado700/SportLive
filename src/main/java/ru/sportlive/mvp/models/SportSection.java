package ru.sportlive.mvp.models;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor
public class SportSection {

    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    @JsonBackReference
    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "organisation_id")
    private Organisation organisation;

    @Getter
    @Setter
    @JsonBackReference
    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "sport_id")
    private Sport sport;

    @Getter
    @Setter
    @JsonBackReference
    @OneToMany(mappedBy = "sportSection",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<Schedule>schedules;

    @Getter
    @Setter
    @JsonBackReference
    @ManyToMany(mappedBy = "selectedSportSections",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<User>users;

    @Getter
    @Setter
    @JsonBackReference
    @ManyToMany(mappedBy = "selectedSportSections",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<Couch>couches;

    public SportSection(Organisation organisation, Sport sport) {
        this.organisation = organisation;
        this.sport = sport;
    }
}
