package ru.sportlive.mvp.dto.input;

import org.springframework.cglib.core.Local;

import java.sql.Timestamp;
import java.time.LocalDateTime;


public class ScheduleDTO {
    private String place;
    private String description;
    private LocalDateTime date;
    private String typeWorkout;
    private Double sum;

    public ScheduleDTO(String place, String description, LocalDateTime date, String typeWorkout, Double sum) {
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

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getTypeWorkout() {
        return typeWorkout;
    }

    public void setTypeWorkout(String typeWorkout) {
        this.typeWorkout = typeWorkout;
    }

    public Double getSum() {
        return sum;
    }

    public void setSum(Double sum) {
        this.sum = sum;
    }
}
