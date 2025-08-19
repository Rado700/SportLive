package ru.sportlive.mvp.dto.input;

import ru.sportlive.mvp.models.SportSection;

import java.util.ArrayList;
import java.util.List;

public class AllCouchDTO {
    private Integer id;
    private String name;
    private String experience;
    private String photo;

    public List<SportSection> section ;

    public AllCouchDTO(Integer id, String name, String experience, String photo, List<SportSection> selectedSportSections) {
        this.id = id;
        this.name = name;
        this.experience = experience;
        this.photo = photo;
        this.section = selectedSportSections;

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

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
