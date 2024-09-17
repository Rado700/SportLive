package ru.sportlive.mvp.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
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

    public Inventory(String name, Integer price, String type, String size) {
        this.name = name;
        this.price = price;
        this.type = type;
        this.size = size;
    }

    public Inventory(Set<User> user) {
        this.user = user;
    }
}
