package ru.sportlive.mvp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.sportlive.mvp.dto.input.CouchDTO;
import ru.sportlive.mvp.models.*;
import ru.sportlive.mvp.repository.CouchRepository;
import ru.sportlive.mvp.repository.InventoryRepository;
import ru.sportlive.mvp.repository.OrganisationRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

@Service
public class CouchService {
    @Autowired
    CouchRepository couchRepository;
    @Autowired
    InventoryRepository inventoryRepository;
    @Autowired
    OrganisationRepository organisationRepository;


    public List<Couch> getAllCouches(){
        return couchRepository.findAll();
    }

    public Couch addCouch (String name, List<SportSection> sportSections_id, String experience){
        Couch couch = new Couch(name,sportSections_id, experience);
        couchRepository.save(couch);
        return couch;
    }
    public Couch addCouchPhoto(Couch couch, MultipartFile photo) throws IOException {

        if (photo != null) {
            String fileName = couch.getId() +"."+ photo.getContentType().split("/")[1];
            Path targetLocation = Paths.get("src/main/resources/static/coach/photo").resolve(fileName);
            Files.copy(photo.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            couch.setPhoto("/coach/photo/"+ fileName);
        }
        couchRepository.save(couch);
        return couch;
    }

    public Couch addCouches() {
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

    public Organisation addOrganisationToCouch (Organisation organisation, Couch couch){
        organisation.getCouches().add(couch);
        organisationRepository.save(organisation);
        return organisation;
    }

    public Couch updateToCouch(Couch couch, CouchDTO couchDTO){
        couch.setName(couchDTO.getName());
        return couch;
    }

    public Couch addCouchToSportSection(SportSection sportSection, Couch couch) {
        couch.addSportSection(sportSection);
        return couchRepository.save(couch);
    }

    public Couch deposit(Integer amount, Couch couch){
        int dep = couch.getBalance();
        dep += amount;
        couch.setBalance(dep);
        couchRepository.save(couch);
        return couch;
    }

    public Couch withdraw (Integer amount, Couch couch){
        int dep =couch.getBalance();
        dep -= amount;
        couch.setBalance(dep);
        couchRepository.save(couch);
        return couch;
    }

    public Integer getCouchBalance(Integer id){
        Optional<Couch> user = couchRepository.findById(id);
        return user.map(Couch::getBalance).orElse(null);
    }
}
