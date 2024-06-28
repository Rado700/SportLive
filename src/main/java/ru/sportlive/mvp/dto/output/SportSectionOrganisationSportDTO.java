package ru.sportlive.mvp.dto.output;

import lombok.Getter;
import lombok.Setter;

public class SportSectionOrganisationSportDTO {

    @Getter
    @Setter
    private Integer id;
    @Getter
    @Setter
    private SportInfoDTO sportInfoDTO;
    @Getter
    @Setter
    private OrganisationInfoDTO organisationInfoDTO;

    public SportSectionOrganisationSportDTO(Integer id, SportInfoDTO sportInfoDTO, OrganisationInfoDTO organisationInfoDTO) {
        this.id = id;
        this.sportInfoDTO = sportInfoDTO;
        this.organisationInfoDTO = organisationInfoDTO;
    }
}
