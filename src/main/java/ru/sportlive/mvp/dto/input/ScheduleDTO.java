package ru.sportlive.mvp.dto.input;

import java.sql.Timestamp;
import java.util.Date;

public class ScheduleDTO {
    private String place;
    private String description;
    private Timestamp date;

    public ScheduleDTO(String place, String description, Timestamp date) {
        this.place = place;
        this.description = description;
        this.date = date;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }


}
