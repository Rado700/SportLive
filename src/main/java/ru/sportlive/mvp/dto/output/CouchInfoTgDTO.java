package ru.sportlive.mvp.dto.output;

public class CouchInfoTgDTO {


    Integer id;
    String name;
    String photo;
    String tgId;

    public CouchInfoTgDTO(Integer id, String name, String photo, String tgId) {
        this.id = id;
        this.name = name;
        this.photo = photo;
        this.tgId = tgId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getTgId() {
        return tgId;
    }

    public void setTgId(String tgId) {
        this.tgId = tgId;
    }
}
