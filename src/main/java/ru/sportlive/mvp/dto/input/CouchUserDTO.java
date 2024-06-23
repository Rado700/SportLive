package ru.sportlive.mvp.dto.input;

import lombok.Getter;
import lombok.Setter;


public class CouchUserDTO {
    @Setter
    @Getter
    private Integer id;

    public CouchUserDTO(Integer id) {
        this.id = id;
    }
}
