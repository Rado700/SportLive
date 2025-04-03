package ru.sportlive.mvp.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sportlive.mvp.dto.input.BookingDTO;
import ru.sportlive.mvp.dto.input.SeasonTicketInputDTO;
import ru.sportlive.mvp.dto.input.SportSectionDTO;
import ru.sportlive.mvp.dto.output.SeasonTicketDTO;
import ru.sportlive.mvp.models.Booking;
import ru.sportlive.mvp.models.Couch;
import ru.sportlive.mvp.models.SeasonTicket;
import ru.sportlive.mvp.models.SportSection;
import ru.sportlive.mvp.services.CouchService;
import ru.sportlive.mvp.services.SeasonTicketsService;
import ru.sportlive.mvp.services.SportSectionService;

import java.util.*;

@RestController
public class SeasonTicketsController {
    @Autowired
    SportSectionService sectionService;

    @Autowired
    CouchService couchService;

    @Autowired
    SeasonTicketsService seasonTicketsService;

    @Operation(summary = "Вывести все тарифы")
    @GetMapping("/ticket/get/{sportSectionId}/{couchId}")
    public ResponseEntity< List<Map<String, Object>>> getSeasonTicket (@PathVariable Integer sportSectionId, @PathVariable Integer couchId){
        SportSection section = sectionService.getSportSection(sportSectionId);
        Couch couch = couchService.getCouch(couchId);
        Map<UUID, List<SeasonTicketDTO>> allSeasonTickets = seasonTicketsService.getAllSeasonTickets(section, couch);
        List<Map<String, Object>> result = new ArrayList<>();
        for (UUID uuid : allSeasonTickets.keySet()) {
            Map<String, Object> item =new HashMap<>();
            item.put("name", allSeasonTickets.get(uuid).get(0).getName());
            item.put("description", allSeasonTickets.get(uuid).get(0).getDescription());
            item.put("sum", allSeasonTickets.get(uuid).get(0).getSum());
            item.put("uuid", allSeasonTickets.get(uuid).get(0).getUuid() );
            item.put("couch", allSeasonTickets.get(uuid).get(0).getCouch());
            item.put("days", allSeasonTickets.get(uuid).get(0).getDays());
            item.put("section", allSeasonTickets.get(uuid).get(0).getSection());
            List<Object> date = new ArrayList<>();
            for (SeasonTicketDTO dto : allSeasonTickets.get(uuid)){
                Map<String, Object> dateInfo = new HashMap<>();
                dateInfo.put("dayOfWeek", dto.getDayOfWeek());
                dateInfo.put("time", dto.getTime());
                date.add(dateInfo);
            }
            item.put("date", date);
            result.add(item);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Operation(summary = "Добавить тарифы",description = "Добавляем тарифы для user ")
    @PostMapping("/")
    public ResponseEntity<SeasonTicketDTO> getSeasonTicket (@RequestBody SeasonTicketInputDTO seasonTicketInputDTO){

        Integer couchId = seasonTicketInputDTO.getCouchId();
        Couch couch = couchService.getCouch(couchId);
        Integer sportSectionId = seasonTicketInputDTO.getSectionId();
        SportSection section = sectionService.getSportSection(sportSectionId);
        if (couch != null && section != null){
            SeasonTicket seasonTicket = seasonTicketsService.addSeasonTicket(seasonTicketInputDTO,couch,section);
            return new ResponseEntity<>(seasonTicket.getSeasonTicketDTO(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Бронирование абонимента")
    @PostMapping("/book/{uuid}")
    public ResponseEntity<SeasonTicket> booking(@PathVariable UUID uuid){
        List<SeasonTicket> ticketsByUUID = seasonTicketsService.getTicketsByUUID(uuid);
        if (ticketsByUUID.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }


        return  new ResponseEntity<>(HttpStatus.OK);
    }
}
