package ru.sportlive.mvp.dto.input;

import java.math.BigInteger;

public class AuthTgDTO {
    String loginId;
    String tgId;

    public AuthTgDTO(String tgId) {
        this.tgId = tgId;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getTgId() {
        return tgId;
    }

    public void setTgId(String tgId) {
        this.tgId = tgId;
    }

    public AuthTgDTO(String loginId, String tgId) {
        this.loginId = loginId;
        this.tgId = tgId;
    }
}
