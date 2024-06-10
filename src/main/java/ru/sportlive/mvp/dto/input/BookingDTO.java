package ru.sportlive.mvp.dto.input;

import ru.sportlive.mvp.models.Schedule;
import ru.sportlive.mvp.models.User;

public class BookingDTO {

    private Integer schedule_id;

    public BookingDTO( Integer schedule_id) {

        this.schedule_id = schedule_id;
    }

    public Integer getSchedule_id() {
        return schedule_id;
    }

    public void setSchedule_id(Integer schedule_id) {
        this.schedule_id = schedule_id;
    }
}
