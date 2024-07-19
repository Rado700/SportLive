package ru.sportlive.mvp.dto.output;

import lombok.Getter;
import lombok.Setter;
import ru.sportlive.mvp.models.User;

public class UserInfoDTO {
    @Getter
    @Setter
    private Integer id;
    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private String surname;
    @Getter
    @Setter
    private int height;
    @Getter
    @Setter
    private int weight;

    @Getter
    @Setter
    private Integer balance;


    public UserInfoDTO(Integer id, String name, String surname, int height, int weight,Integer balance) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.height = height;
        this.weight = weight;
        this.balance = balance;
    }
}
