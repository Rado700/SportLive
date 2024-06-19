package ru.sportlive.mvp.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Organisation {
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
    private String description;

    @Getter
    @Setter
    @JsonManagedReference
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "couch_organisation",
            joinColumns = @JoinColumn(name = "couch_id"),
            inverseJoinColumns = @JoinColumn(name = "organisation_id"))
    private List<Couch> couches = new ArrayList<>();


    @Getter
    @Setter
    @JsonManagedReference
    @OneToMany(mappedBy = "organisation",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<SportOrganisation> sportOrganisation;


    public Organisation() {

    }

    public Organisation(String name, String description) {
        this.name = name;
        this.description = description;
    }


}
