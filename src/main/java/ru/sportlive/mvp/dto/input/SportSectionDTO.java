package ru.sportlive.mvp.dto.input;

import lombok.Getter;
import lombok.Setter;

public class SportSectionDTO {

    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private Integer sport_id;
    @Getter
    @Setter
    private Integer organisation_id;

    public SportSectionDTO(String name, Integer sport_id, Integer organisation_id) {
        this.name = name;
        this.sport_id = sport_id;
        this.organisation_id = organisation_id;
    }

    public SportSectionDTO() {
    }
}
