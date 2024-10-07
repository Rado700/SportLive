package ru.sportlive.mvp.dto.output;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;
import ru.sportlive.mvp.models.Schedule;

@Component
@NoArgsConstructor
public class BookingUserCouchDTO {

    @Getter
    @Setter
    private int id;
    @Getter
    @Setter
    private CouchInfoDTO couch;
    @Getter
    @Setter
    private UserInfoDTO user;

    @Getter
    @Setter
    private Integer schedule_id;


    public BookingUserCouchDTO(int id, CouchInfoDTO couch, UserInfoDTO user, Schedule schedule) {
        this.id = id;
        this.couch = couch;
        this.user = user;
        this.schedule_id = schedule.getId();
    }
}
