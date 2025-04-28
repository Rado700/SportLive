package ru.sportlive.mvp.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Inventory {
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
    private Integer price;
    @Getter
    @Setter
    private String type;
    @Getter
    @Setter
    private String size;
    @Getter
    @Setter
    private Integer amount;


    @Getter
    @Setter
    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "couch_id")
    private Couch couch;


    @Getter
    @JsonBackReference
    @ManyToMany(mappedBy = "selectedInventory", fetch = FetchType.EAGER)
    private Set<User> user = new HashSet<>();

    public Inventory(String name, Integer price, String type, String size, Couch couch, Integer amount) {
        this.name = name;
        this.price = price;
        this.type = type;
        this.size = size;
        this.couch = couch;
        this.amount = amount;

    }

    public Inventory(String name, Integer price, String type, String size, Couch couch) {
        this.name = name;
        this.price = price;
        this.type = type;
        this.size = size;
        this.couch = couch;
    }

    public Inventory(String name, Integer price, String type, String size) {
        this.name = name;
        this.price = price;
        this.type = type;
        this.size = size;
    }

    public Inventory(Set<User> user) {
        this.user = user;
    }

    public Inventory() {

    }
}
