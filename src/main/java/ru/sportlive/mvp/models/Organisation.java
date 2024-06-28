package ru.sportlive.mvp.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ru.sportlive.mvp.dto.output.OrganisationInfoDTO;

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
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "couch_organisation",
            joinColumns = @JoinColumn(name = "organisation_id"),
            inverseJoinColumns = @JoinColumn(name = "couch_id"))
    private List<Couch> couches = new ArrayList<>();


    @Getter
    @Setter
    @JsonManagedReference
    @OneToMany(mappedBy = "organisation",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<SportSection> sportSection;


//    @Getter
//    @Setter
//    @JsonBackReference
//    @ManyToMany(mappedBy = "selectedOrganisation", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    private List<User> users = new ArrayList<>();
    public Organisation() {

    }

    public Organisation(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public OrganisationInfoDTO getOrganisationInfoDTO() {
        return new OrganisationInfoDTO(id,name,description);
    }

}
