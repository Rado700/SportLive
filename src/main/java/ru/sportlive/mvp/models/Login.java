package ru.sportlive.mvp.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
public class Login {

    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Getter
    @Setter
    private String login ;
    @Getter
    @Setter
    private String password;

    public Login(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public Login() {
    }

    @Getter
    @Setter
    @JsonBackReference
    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private User user;

    @Getter
    @Setter
    @JsonBackReference
    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Couch couch;


}
