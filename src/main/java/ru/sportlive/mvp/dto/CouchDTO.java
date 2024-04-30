package ru.sportlive.mvp.dto;

public class CouchDTO {
    private String name;
    private Integer organisation_id;

    public CouchDTO(String name, Integer organisation_id) {
        this.name = name;
        this.organisation_id = organisation_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getOrganisation_id() {
        return organisation_id;
    }

    public void setOrganisation_id(Integer organisation_id) {
        this.organisation_id = organisation_id;
    }
}