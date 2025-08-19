package ru.sportlive.mvp.dto.input;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;


@NoArgsConstructor
public class CouchDTO {
    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private String experience;

    @Getter
    @Setter
    private MultipartFile photo;


    public CouchDTO(String name) {
        this.name = name;
    }

    public CouchDTO(String name, String experience, MultipartFile photo) {
        this.name = name;
        this.experience = experience;
        this.photo = photo;
    }
}