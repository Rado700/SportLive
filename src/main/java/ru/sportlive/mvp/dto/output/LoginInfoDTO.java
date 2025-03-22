package ru.sportlive.mvp.dto.output;

import ru.sportlive.mvp.models.Couch;
import ru.sportlive.mvp.models.User;

public class LoginInfoDTO {
    Integer id;
    UserInfoDTO user;
    CouchInfoDTO couch;

    public LoginInfoDTO(Integer id, UserInfoDTO user, CouchInfoDTO couch) {
        this.id = id;
        this.user = user;
        this.couch = couch;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UserInfoDTO getUser() {
        return user;
    }

    public void setUser(UserInfoDTO user) {
        this.user = user;
    }

    public CouchInfoDTO getCouch() {
        return couch;
    }

    public void setCouch(CouchInfoDTO couch) {
        this.couch = couch;
    }
}
