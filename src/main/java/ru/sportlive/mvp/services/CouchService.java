package ru.sportlive.mvp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sportlive.mvp.dto.input.CouchDTO;
import ru.sportlive.mvp.models.Couch;
import ru.sportlive.mvp.models.Organisation;
import ru.sportlive.mvp.models.Schedule;
import ru.sportlive.mvp.repository.CouchRepository;
import ru.sportlive.mvp.repository.InventoryRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class CouchService {
    @Autowired
    CouchRepository couchRepository;
    @Autowired
    InventoryRepository inventoryRepository;

    public List<Couch> getAllCouches(){
        return couchRepository.findAll();
    }
    public Couch addCouch (String name, Organisation organisation_id){
        Couch couch = new Couch(name,organisation_id);
        couchRepository.save(couch);
        return couch;
    }
    public Couch addCouchs () {
        Couch couch = new Couch();
        couchRepository.save(couch);
        return couch;
    }
    public Couch getCouch(Integer id){
        return couchRepository.findById(id).orElse(null);
    }

    public Couch deleteCouch(Integer id){
        Couch couch = getCouch(id);
        couchRepository.delete(couch);
        return couch;
    }

    public Couch addCouchToOrganisation (Organisation organisation, Couch couch){
        couch.setOrganisation(organisation);
        couchRepository.save(couch);
        return couch;
    }

    public Couch updateToCouch(Couch couch, CouchDTO couchDTO){
        couch.setName(couchDTO.getName());
        return couch;
    }

}
