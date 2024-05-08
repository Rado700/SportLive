package ru.sportlive.mvp.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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
    @JoinColumn(name = "user_id",foreignKey = @ForeignKey(
            foreignKeyDefinition = "FOREIGN KEY (user_id) REFERENCES \"user\" ON DELETE CASCADE ON UPDATE CASCADE"))
    private User user;

    public Transaction(Integer summa, String type, Timestamp date, User user) {
        this.summa = summa;
        this.type = type;
        this.dateTime = date;
        this.user = user;
    }

    public Transaction() {
    }
}
