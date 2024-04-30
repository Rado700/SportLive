package ru.sportlive.mvp.dto;

import ru.sportlive.mvp.models.Schedule;
import ru.sportlive.mvp.models.User;

public class BookingDTO {
    private Integer user_id;
    private Integer schedule_id;

    public BookingDTO(Integer user_id, Integer schedule_id) {
        this.user_id = user_id;
        this.schedule_id = schedule_id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Integer getSchedule_id() {
        return schedule_id;
    }

    public void setSchedule_id(Integer schedule_id) {
        this.schedule_id = schedule_id;
    }
}
