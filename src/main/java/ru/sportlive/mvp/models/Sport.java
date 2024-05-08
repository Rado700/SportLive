package ru.sportlive.mvp.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

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


    public Sport(String name_sport, String description, String instruction, String equipment) {
        this.name_sport = name_sport;
        this.description = description;
        this.instruction = instruction;
        this.equipment = equipment;
    }

    public Sport() {
    }

}
