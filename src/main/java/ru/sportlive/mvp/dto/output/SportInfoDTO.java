package ru.sportlive.mvp.dto.output;

import lombok.Getter;
import lombok.Setter;

public class SportInfoDTO {

    @Getter
    @Setter
    private Integer id;
    @Getter
    @Setter
    private String name_sport;
    @Getter
    @Setter
    private String description;
    @Getter
    @Setter
    private String instruction;
    @Getter
    @Setter
    private String equipment;

    public SportInfoDTO(Integer id, String name_sport, String description, String instruction, String equipment) {
        this.id = id;
        this.name_sport = name_sport;
        this.description = description;
        this.instruction = instruction;
        this.equipment = equipment;
    }
}
