package ru.sportlive.mvp.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
public class CouchSport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JoinColumn(name = "couch_id")
    @ManyToOne
    @JsonBackReference
    private Couch couch;

    @JoinColumn(name = "sport_id")
    @ManyToOne
    @JsonBackReference
    private Sport sport;
}
