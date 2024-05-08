package ru.sportlive.mvp.dto.output;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

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

    public BookingUserCouchDTO(int id, CouchInfoDTO couch, UserInfoDTO user) {
        this.id = id;
        this.couch = couch;
        this.user = user;
    }
}
