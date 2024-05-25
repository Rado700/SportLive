package ru.sportlive.mvp.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
public class Login {

    @Getter
    @Setter
    @Id
    private Integer id;
    @Getter
    @Setter
    private String login ;
    @Getter
    @Setter
    private String password;

    public Login(Integer id, String login, String password) {
        this.id = id;
        this.login = login;
        this.password = password;
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
