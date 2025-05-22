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
    @Getter
    @Setter
    private Double balance;

    @Getter
    @Setter
    private String experience;

    @Getter
    @Setter
    private String photo;

    public CouchInfoDTO(Integer id, String name,Double balance) {
        this.id = id;
        this.name = name;
        this.balance = balance;
    }

    public CouchInfoDTO(Integer id,String name, Double balance, String experience, String photo) {
        this.id = id;
        this.name = name;
        this.balance = balance;
        this.experience = experience;
        this.photo = photo;
    }
}
