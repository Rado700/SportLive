package ru.sportlive.mvp.dto.input;

import lombok.Getter;
import lombok.Setter;


public class CouchDTO {
    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private Integer organisation_id;

    public CouchDTO(String name, Integer organisation_id) {
        this.name = name;
        this.organisation_id = organisation_id;
    }

}