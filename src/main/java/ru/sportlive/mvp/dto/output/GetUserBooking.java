package ru.sportlive.mvp.dto.output;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;
import ru.sportlive.mvp.models.Booking;
import ru.sportlive.mvp.models.Couch;
import ru.sportlive.mvp.models.Schedule;

import java.util.List;
import java.util.Set;

@Component
@NoArgsConstructor
public class GetUserBooking {
    @Getter
    @Setter
    private CouchInfoDTO couchInfoDTO;

    @Getter
    @Setter
    private UserInfoDTO userInfoDTO;
    @Getter
    @Setter
    private List<Booking> booking;




}
