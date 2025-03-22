package ru.sportlive.mvp.dto.output;

import lombok.Getter;
import lombok.Setter;

public class UserAllInventoryDTO {
    @Getter
    @Setter
    private Integer id;
    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private Integer price;
    @Getter
    @Setter
    private String type;
    @Getter
    @Setter
    private String size;
    @Getter
    @Setter
    private Integer amount;

    public UserAllInventoryDTO(Integer id, String name, Integer price, String type, String size, Integer amount) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.type = type;
        this.size = size;
        this.amount = amount;
    }
}
