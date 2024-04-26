package ru.sportlive.mvp.dto;

public class CouchDTO {
    private String name;
    private Integer organisationId;

    public CouchDTO(String name, Integer organisation_id) {
        this.name = name;
        this.organisationId = organisation_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getOrganisationId() {
        return organisationId;
    }

    public void setOrganisationId(Integer organisationId) {
        this.organisationId = organisationId;
    }
}