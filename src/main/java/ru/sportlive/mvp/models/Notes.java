package ru.sportlive.mvp.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ru.sportlive.mvp.dto.output.NotesDTO;

import java.time.LocalDateTime;

@Entity
public class Notes {
    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    @Getter
    @Setter
    String notes;
    @Getter
    @Setter
    LocalDateTime dateTime;


    @Getter
    @Setter
    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "couch_id")
    private Couch couch;


    @Getter
    @Setter
    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;


    public Notes(String notes, LocalDateTime dateTime) {
        this.notes = notes;
        this.dateTime = dateTime;
    }

    public Notes() {

    }

    public NotesDTO notesDTO(){
        return new NotesDTO(id,notes,dateTime);
    }
}
