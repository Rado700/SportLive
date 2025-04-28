package ru.sportlive.mvp.dto.output;

import java.time.LocalDateTime;

public class NotesDTO {
    Integer id;
    String notes;
    LocalDateTime localDateTime;

    public NotesDTO(Integer id, String notes, LocalDateTime localDateTime) {
        this.id = id;
        this.notes = notes;
        this.localDateTime = localDateTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }
}
