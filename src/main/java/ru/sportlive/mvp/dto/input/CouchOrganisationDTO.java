package ru.sportlive.mvp.dto.input;

import lombok.Getter;
import lombok.Setter;

public class CouchOrganisationDTO {
    @Getter
    @Setter
    private Integer couch_id;
    @Getter
    @Setter
    private Integer organisation_id;

    public CouchOrganisationDTO(Integer couch_id, Integer organisation_id) {
        this.couch_id = couch_id;
        this.organisation_id = organisation_id;
    }
}
