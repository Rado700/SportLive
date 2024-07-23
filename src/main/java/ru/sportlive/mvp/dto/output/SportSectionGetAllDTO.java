package ru.sportlive.mvp.dto.output;

import lombok.Getter;
import lombok.Setter;

public class SportSectionGetAllDTO {
    @Getter
    @Setter
    private Integer id;
    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private SportInfoDTO sportInfoDTO;
    @Getter
    @Setter
    private OrganisationInfoDTO organisationInfoDTO;

    @Getter
    @Setter
    private CouchInfoDTO couchInfoDTO;

    public SportSectionGetAllDTO(Integer id, String name, SportInfoDTO sportInfoDTO, OrganisationInfoDTO organisationInfoDTO,CouchInfoDTO couchInfoDTO) {
        this.id = id;
        this.name = name;
        this.sportInfoDTO = sportInfoDTO;
        this.organisationInfoDTO = organisationInfoDTO;
        this.couchInfoDTO = couchInfoDTO;
    }
}
