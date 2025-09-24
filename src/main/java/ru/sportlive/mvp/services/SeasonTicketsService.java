package ru.sportlive.mvp.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.sportlive.mvp.dto.input.SeasonTicketInputDTO;
import ru.sportlive.mvp.dto.output.SeasonTicketDTO;
import ru.sportlive.mvp.models.Couch;
import ru.sportlive.mvp.models.SeasonTicket;
import ru.sportlive.mvp.models.SportSection;
import ru.sportlive.mvp.models.User;
import ru.sportlive.mvp.repository.SeasonTicketsRepository;

@Transactional
@Service
public class SeasonTicketsService {

    @Autowired
    SeasonTicketsRepository seasonTicketsRepository;

    public SeasonTicket deleteTicket (Integer id){
        seasonTicketsRepository.findById(id).ifPresent(seasonTicket -> seasonTicketsRepository.delete(seasonTicket));
        return null;
    }
//    public Map<UUID, List<SeasonTicketDTO>> getAllSeasonTicketForUser(UUID uuid, User user){
//        List<SeasonTicket>allSeasonTicket = seasonTicketsRepository.findAll();
//        Map<UUID, List<SeasonTicketDTO>> seasonTicketDTOS = new HashMap<>();
//        for (SeasonTicket seasonTicket : allSeasonTicket) {
//            if (allSeasonTicket.get(0).getUuid() == uuid) {
//                List<SeasonTicketDTO>getAllIdSeasonTicket = new ArrayList<>();
//                getAllIdSeasonTicket.add(seasonTicket.getSeasonTicketDTO());
//                seasonTicketDTOS.put(seasonTicket.getUuid(),getAllIdSeasonTicket);
//            }
//        }
//        return seasonTicketDTOS;
//    }

    public Map<UUID, List<SeasonTicketDTO>> getAllSeasonTickets(SportSection section, Couch couch){
        List<SeasonTicket> allSeason = seasonTicketsRepository.findAll();
        Map<UUID, List<SeasonTicketDTO>> seasonTicketDTOS = new HashMap<>();
        for (SeasonTicket ticket : allSeason) {
            if (ticket.getCouch() == couch && ticket.getSportSection() == section) {
                if (seasonTicketDTOS.containsKey(ticket.getUuid())){
                    seasonTicketDTOS.get(ticket.getUuid()).add(ticket.getSeasonTicketDTO());
                } else {
                    List<SeasonTicketDTO> st = new ArrayList<>();
                    st.add(ticket.getSeasonTicketDTO());
                    seasonTicketDTOS.put(ticket.getUuid(),st);
                }
            }
        }
        return seasonTicketDTOS;
    }
    public List<SeasonTicket> bookSeasonTickets(UUID uuid, User user){
        List<SeasonTicket> seasonTickets = seasonTicketsRepository.findAllByUuid(uuid);
        user.setTickets(seasonTickets);
        return user.getTickets();
    }


    public SeasonTicket addSeasonTicket(SeasonTicketInputDTO seasonTicketInputDTO, Couch couch, SportSection sportSection){
        SeasonTicket seasonTicket = new SeasonTicket(
                seasonTicketInputDTO.getUuid(),
                seasonTicketInputDTO.getName(),
                seasonTicketInputDTO.getDescription(),
                seasonTicketInputDTO.getSum(),
                seasonTicketInputDTO.getDayOfWeek(),
                seasonTicketInputDTO.getTime(),
                seasonTicketInputDTO.getTrainings(),
                couch,
                sportSection);
        seasonTicketsRepository.save(seasonTicket);
        return seasonTicket;
    }

    public List<SeasonTicket> getTicketsByUUID(UUID uuid){
        return seasonTicketsRepository.findAllByUuid(uuid);
    }
}
