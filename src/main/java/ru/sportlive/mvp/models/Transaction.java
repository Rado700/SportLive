package ru.sportlive.mvp.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
public class Transaction {
    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Getter
    @Setter
    private Integer summa;
    @Getter
    @Setter
    private String type;
    @Getter
    @Setter
    private Timestamp dateTime;

    @Getter
    @Setter
    @JsonBackReference
    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "login_id",foreignKey = @ForeignKey(
            foreignKeyDefinition = "FOREIGN KEY (login_id) REFERENCES login ON DELETE CASCADE ON UPDATE CASCADE"))
    private Login login;


    public Transaction(Integer summa, String type, Timestamp date, Login login) {
        this.summa = summa;
        this.type = type;
        this.dateTime = date;
        this.login = login;
    }

    public Transaction() {
    }
}
