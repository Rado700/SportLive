package ru.sportlive.mvp.dto.output;

import lombok.Getter;
import lombok.Setter;

public class OrganisationInfoDTO {
    @Getter
    @Setter
    private Integer id;
    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private String description;

    public OrganisationInfoDTO(Integer id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }
}
