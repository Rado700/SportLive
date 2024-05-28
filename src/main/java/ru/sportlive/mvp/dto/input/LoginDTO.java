package ru.sportlive.mvp.dto.input;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;


public class LoginDTO {

    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private String password;




    public LoginDTO(String name, String password) {
        this.name = name;
        this.password = password;
    }


}
