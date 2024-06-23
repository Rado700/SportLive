package ru.sportlive.mvp.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Sport {
    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Getter
    @Setter
    private String name_sport;
    @Getter
    @Setter
    private String description;
    @Getter
    @Setter
    private String instruction;
    @Getter
    @Setter
    private String equipment;

    @Getter
    @Setter
    @JsonManagedReference
    @OneToMany(mappedBy = "sport",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<SportSection> sportSection;


    public Sport(String name_sport, String description, String instruction, String equipment) {
        this.name_sport = name_sport;
        this.description = description;
        this.instruction = instruction;
        this.equipment = equipment;
    }

    public Sport() {
    }

}
