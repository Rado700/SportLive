package ru.sportlive.mvp.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Sport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name_sport;
    private String description;
    private String instruction;
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
