package ru.sportlive.mvp.dto.input;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
public class CouchDTO {
    @Getter
    @Setter
    private String name;

    public CouchDTO(String name) {
        this.name = name;
    }

}