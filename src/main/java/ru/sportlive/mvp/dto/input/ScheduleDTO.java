package ru.sportlive.mvp.dto.input;

import java.sql.Timestamp;


public class ScheduleDTO {
    private String place;
    private String description;
    private Timestamp date;
    private String typeWorkout;
    private Integer sum;

    public ScheduleDTO(String place, String description, Timestamp date, String typeWorkout, Integer sum) {
        this.place = place;
        this.description = description;
        this.date = date;
        this.typeWorkout = typeWorkout;
        this.sum = sum;
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

    public String getTypeWorkout() {
        return typeWorkout;
    }

    public void setTypeWorkout(String typeWorkout) {
        this.typeWorkout = typeWorkout;
    }

    public Integer getSum() {
        return sum;
    }

    public void setSum(Integer sum) {
        this.sum = sum;
    }
}
