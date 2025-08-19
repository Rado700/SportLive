package ru.sportlive.mvp.dto.input;

public class UserTgDTO {
    private String name;
    private String surname;
    private String tg;

    public UserTgDTO(String name, String surname, String tg) {
        this.name = name;
        this.surname = surname;
        this.tg = tg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getTg() {
        return tg;
    }

    public void setTg(String tg) {
        this.tg = tg;
    }
}
