package ru.sportlive.mvp.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
    @OneToMany(mappedBy = "organisation", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Couch> couch = new ArrayList<>();

    public Organisation() {

    }

    public Organisation(String name, String description) {
        this.name = name;
        this.description = description;
    }


}
