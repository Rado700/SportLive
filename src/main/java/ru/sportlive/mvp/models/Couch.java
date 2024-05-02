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
@Table(name="couch")
public class Couch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

    @JsonManagedReference
    @OneToMany(mappedBy = "couch", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Inventory> inventory = new ArrayList<>();

    @JsonBackReference
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "organisation_id", foreignKey = @ForeignKey(
            foreignKeyDefinition = "FOREIGN KEY (organisation_id) REFERENCES Organisation ON DELETE CASCADE ON UPDATE CASCADE"
    ))
    private Organisation organisation;

    @JsonBackReference
    @ManyToMany(mappedBy = "selectedCouches",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Set<User> user = new HashSet<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "couch",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Set<Schedule>schedules = new HashSet<>();


    public Couch() {
    }

    public Couch(String name, Organisation organisation) {
        this.name = name;
        this.organisation = organisation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Inventory> getInventory() {
        return inventory;
    }

    public void setInventory(List<Inventory> inventory) {
        this.inventory = inventory;
    }

    public Organisation getOrganisation() {
        return organisation;
    }

    public void setOrganisation(Organisation organisation) {
        this.organisation = organisation;
    }

    public Set<User> getUser() {
        return user;
    }

    public void setUser(Set<User> user) {
        this.user = user;
    }

    public Set<Schedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(Set<Schedule> schedules) {
        this.schedules = schedules;
    }
}
