package ru.sportlive.mvp.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sportlive.mvp.models.Couch;
import ru.sportlive.mvp.models.SportSection;
import ru.sportlive.mvp.repository.SportSectionRepository;

@Service
public class SportSectionService {

    @Autowired
    SportSectionRepository sportSectionRepository;


    public SportSection getSportSection(Integer id){
        return sportSectionRepository.findById(id).orElse(null);
    }
}
