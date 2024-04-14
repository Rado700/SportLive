package ru.sportlive.mvp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sportlive.mvp.models.Couch;
import ru.sportlive.mvp.repository.CouchRepository;

import java.util.List;

@Service
public class CouchService {
    @Autowired
    CouchRepository couchRepository;

    public List<Couch> getAllUsers(){
        return couchRepository.findAll();
    }


}
