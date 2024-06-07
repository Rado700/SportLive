package ru.sportlive.mvp.dto.input;

import lombok.Getter;
import lombok.Setter;

public class CouchOrganisationDTO {

    @Getter
    @Setter
    private Integer organisation_id;

    public CouchOrganisationDTO( Integer organisation_id) {
        this.organisation_id = organisation_id;
    }
}
