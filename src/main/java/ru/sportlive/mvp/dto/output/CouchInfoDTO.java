package ru.sportlive.mvp.dto.output;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import ru.sportlive.mvp.models.Couch;

public class CouchInfoDTO {

    @Getter
    @Setter
    private Integer id;
    @Getter
    @Setter
    private String name;

    public CouchInfoDTO(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}
