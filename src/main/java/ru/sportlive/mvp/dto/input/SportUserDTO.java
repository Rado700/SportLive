package ru.sportlive.mvp.dto.input;

import lombok.Getter;
import lombok.Setter;

public class SportUserDTO {

    @Getter
    @Setter
    private Integer sport_id;

    public SportUserDTO(Integer sport_id) {
        this.sport_id = sport_id;
    }
}
