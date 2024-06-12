package ru.sportlive.mvp.dto.input;

import lombok.Getter;
import lombok.Setter;


public class CouchDTO {
    @Getter
    @Setter
    private String name;

    public CouchDTO(String name, Integer organisation_id) {
        this.name = name;
    }

}