package ru.sportlive.mvp.models;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.sportlive.mvp.dto.output.CouchInfoDTO;
import ru.sportlive.mvp.dto.output.SportSectionGetAllDTO;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    public SportSection(String name, Sport sport, Organisation organisation) {
        this.name = name;
        this.sport = sport;
        this.organisation = organisation;
    }

    public SportSectionGetAllDTO getSportSectionAllDTO(){
        List<CouchInfoDTO>newListCouches;
        if (couches== null){
            newListCouches = new ArrayList<>();
        }else {
            newListCouches = couches.stream().map(Couch::getCouchInfo).toList();
        }
            return new SportSectionGetAllDTO(id, name, sport.getSportInfoDTO(), organisation.getOrganisationInfoDTO(), newListCouches);

    }
}
