package ru.sportlive.mvp.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ru.sportlive.mvp.dto.output.CouchInfoDTO;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="couch")
public class Couch {
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
    @JsonManagedReference
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Login login;

    @Getter
    @Setter
    @JsonManagedReference
    @OneToMany(mappedBy = "couch", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Inventory> inventory = new ArrayList<>();


    @Getter
    @Setter
    @JsonManagedReference
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "couch_sport_section",
            joinColumns = @JoinColumn(name = "couch_id"),
            inverseJoinColumns = @JoinColumn(name = "sport_section_id"))
    private List<SportSection> selectedSportSections = new ArrayList<>();


    @Getter
    @Setter
    @JsonManagedReference
    @OneToMany(mappedBy = "couch", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Schedule> schedules = new HashSet<>();

    @Getter
    @Setter
    @JsonBackReference
    @ManyToMany(mappedBy = "selectedCouches",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<User>users;

    @Getter
    @Setter
    @JsonBackReference
    @ManyToMany(mappedBy = "couches",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<Organisation>organisations;

    public void addSportSection(SportSection sportSection){
        this.selectedSportSections.add(sportSection);
    }


//    @Getter
//    @Setter
//    @JsonManagedReference
//    @OneToMany(mappedBy = "couch",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
//    private List<Booking>bookings = new ArrayList<>();


    public Couch() {
    }

    public Couch(String name, List<SportSection> selectedSportSections) {
        this.name = name;
        this.selectedSportSections = selectedSportSections;
    }
    public CouchInfoDTO getCouchInfo(){
        return new CouchInfoDTO(id,name);
    }
}