package ru.sportlive.mvp.dto.input;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
public class SeasonTicketInputDTO {
    @Getter
    @Setter
    private UUID uuid;
    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private String description;
    @Getter
    @Setter
    private Integer sum;
    @Getter
    @Setter
    private String dayOfWeek;
    @Getter
    @Setter
    private String time;
    @Getter
    @Setter
    private Integer trainings;
    @Getter
    @Setter
    private Integer sectionId;

}
