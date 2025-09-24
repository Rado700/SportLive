package ru.sportlive.mvp.dto.output;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public class UserTrainingDTO {
    @Getter
    @Setter
    private LocalDateTime date;

    @Getter
    @Setter
    private String typeWorkout;

    @Getter
    @Setter
    private Integer scheduleId;

    @Getter
    @Setter
    private Integer couchId;

    @Getter
    @Setter
    private Integer sportSectionId;

    public UserTrainingDTO(LocalDateTime date, String typeWorkout, Integer scheduleId, Integer couchId, Integer sportSectionId) {
        this.date = date;
        this.typeWorkout = typeWorkout;
        this.scheduleId = scheduleId;
        this.couchId = couchId;
        this.sportSectionId = sportSectionId;
    }
}


