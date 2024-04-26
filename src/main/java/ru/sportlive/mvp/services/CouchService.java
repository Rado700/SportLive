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

    public List<Couch> getAllCouches(){
        return couchRepository.findAll();
    }
    public Couch addCouch (String name,Integer organisationId){
        Couch couch = new Couch(name,organisationId);
        return couchRepository.save(couch);
    }


}
