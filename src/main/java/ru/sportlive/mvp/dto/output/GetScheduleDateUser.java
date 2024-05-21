package ru.sportlive.mvp.dto.output;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@NoArgsConstructor
public class GetScheduleDateUser {
    @Getter
    @Setter
    private Integer id;
    @Getter
    @Setter
    private String place;
    @Getter
    @Setter
    private Date date;
    @Getter
    @Setter
    private CouchInfoDTO couchInfoDTO;
    @Getter
    @Setter
    private UserInfoDTO userInfoDTO;

    public GetScheduleDateUser(Integer id,String place, Date date, CouchInfoDTO couchInfoDTO, UserInfoDTO userInfoDTO) {
        this.id = id;
        this.place = place;
        this.date = date;
        this.couchInfoDTO = couchInfoDTO;
        this.userInfoDTO = userInfoDTO;
    }
}
