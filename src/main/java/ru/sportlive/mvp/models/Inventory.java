package ru.sportlive.mvp.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor

public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private Integer price;
    private String type;
    private String size;


    @JsonBackReference
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "couch_id")
    private Couch couch;


    @JsonBackReference
    @ManyToMany(mappedBy = "selectedInventory",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Set<User> user = new HashSet<>();

    public Inventory() {
    }

    public Inventory(String name, Integer price, String type, String size, Couch couch) {
        this.name = name;
        this.price = price;
        this.type = type;
        this.size = size;
        this.couch = couch;
    }
}
