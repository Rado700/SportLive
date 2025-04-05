package ru.sportlive.mvp.dto.input;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.sportlive.mvp.dto.output.CouchInfoDTO;
import ru.sportlive.mvp.dto.output.SportSectionGetAllDTO;

import java.sql.Time;
import java.time.LocalTime;
import java.util.UUID;

@AllArgsConstructor
public class SeasonTicketInputDTO {
    @Getter
    @Setter
    private Integer id;
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
    private String days;
    @Getter
    @Setter
    private Integer sectionId;
    @Getter
    @Setter
    private Integer couchId;
}
