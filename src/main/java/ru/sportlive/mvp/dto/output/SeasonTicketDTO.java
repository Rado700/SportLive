package ru.sportlive.mvp.dto.output;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
public class SeasonTicketDTO {
    @Getter
    private Integer id;
    @Getter
    private UUID uuid;
    @Getter
    private String name;
    @Getter
    private String description;
    @Getter
    private Integer sum;
    @Getter
    private String dayOfWeek;
    @Getter
    private String time;
    @Getter
    private Integer trainings;
    @Getter
    private CouchInfoDTO couch;
    @Getter
    private SportSectionGetAllDTO section;

}
