package ru.sportlive.mvp.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
//import ru.sportlive.mvp.dto.output.GetUserBooking;
import ru.sportlive.mvp.dto.output.GetUserBooking;
import ru.sportlive.mvp.dto.output.UserInfoDTO;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
//@AllArgsConstructor
//@NoArgsConstructor
@Table(name = "\"user\"")
public class User {
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
    private String surname;
    @Getter
    @Setter
    private int height;
    @Getter
    @Setter
    private int weight;
    @Getter
    @Setter
    private int balance;

    @Getter
    @Setter
    @JsonManagedReference
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Booking> booking = new ArrayList<>();


    @Getter
    @Setter
    @JsonManagedReference
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_inventory",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "inventory_id"))
    private Set<Inventory> selectedInventory = new HashSet<>();


    @JsonManagedReference
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_couch",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "couch_id"))
    private Set<Couch> selectedCouches = new HashSet<>();


    @Getter
    @Setter
    @JsonManagedReference
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Transaction> transactionList = new HashSet<>();

    public User(String name, String surname, int height, int weight) {
        this.name = name;
        this.surname = surname;
        this.height = height;
        this.weight = weight;
    }

    public User() {
    }

    public UserInfoDTO getUserInfo(){
        return new UserInfoDTO(id,name,surname,height,weight);
    }
}
