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
    private Double summa;
    @Getter
    @Setter
    private String type;
    @Getter
    @Setter
    @Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
    private LocalDateTime localDateTime;

    @Getter
    @Setter
    @JsonBackReference
    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "login_id",foreignKey = @ForeignKey(
            foreignKeyDefinition = "FOREIGN KEY (login_id) REFERENCES login ON DELETE CASCADE ON UPDATE CASCADE"))
    private Login login;


    public Transaction(Double summa, String type, LocalDateTime date, Login login) {
        this.summa = summa;
        this.type = type;
        this.localDateTime = date;
        this.login = login;
    }

    public Transaction() {
    }
}
