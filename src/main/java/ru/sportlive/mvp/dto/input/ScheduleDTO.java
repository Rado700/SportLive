package ru.sportlive.mvp.dto.input;

import java.util.Date;

public class ScheduleDTO {
    private String place;
    private String description;
    private Date date;
    private Integer couch_id;

    public ScheduleDTO(String place, String description, Date date, Integer couch_id) {
        this.place = place;
        this.description = description;
        this.date = date;
        this.couch_id = couch_id;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getCouch_id() {
        return couch_id;
    }

    public void setCouch_id(Integer couch_id) {
        this.couch_id = couch_id;
    }
}
