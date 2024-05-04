package ru.sportlive.mvp.dto;

import ru.sportlive.mvp.models.User;

public class UserPayDTO {
    private Integer user_id;
    private Integer sum;

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Integer getSum() {
        return sum;
    }

    public void setSum(Integer sum) {
        this.sum = sum;
    }
}
