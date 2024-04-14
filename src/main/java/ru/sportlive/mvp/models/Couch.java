package ru.sportlive.mvp.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="couch")
public class Couch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

    @JsonManagedReference
    @OneToMany(mappedBy = "couch",fetch = FetchType.EAGER)
    private List<Inventory> inventory = new ArrayList<>();

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "organisation_id")
    private Organisation organisation;

    @JsonBackReference
    @ManyToMany(mappedBy = "selectedCouches")
    private Set<User> user = new HashSet<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "couch",cascade = CascadeType.ALL)
    private Set<Schedule>schedules = new HashSet<>();

}
