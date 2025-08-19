package ru.sportlive.mvp.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sportlive.mvp.dto.input.SeasonTicketInputDTO;
import ru.sportlive.mvp.dto.output.SeasonTicketDTO;
import ru.sportlive.mvp.models.*;
import ru.sportlive.mvp.services.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

@RestController
@RequestMapping("/api/seasonTickets")
public class SeasonTicketsController {
    @Autowired
    SportSectionService sectionService;

    @Autowired
    CouchService couchService;

    @Autowired
    SeasonTicketsService seasonTicketsService;

    @Autowired
    BookingService bookingService;

    @Autowired
    ScheduleService scheduleService;

    @Autowired
    UserService userService;

    private static final Map<String, DayOfWeek> RUSSIAN_DAY_OF_WEEK = Map.of(
            "Понедельник", DayOfWeek.MONDAY,
            "Вторник", DayOfWeek.TUESDAY,
            "Среда", DayOfWeek.WEDNESDAY,
            "Четверг", DayOfWeek.THURSDAY,
            "Пятница", DayOfWeek.FRIDAY,
            "Суббота", DayOfWeek.SATURDAY,
            "Воскресенье", DayOfWeek.SUNDAY
    );


    @Operation(summary = "Добавление абонемента")
    @PostMapping("/add/")
    public ResponseEntity<SeasonTicketDTO> addSeasonTicket(HttpSession httpSession, @RequestBody SeasonTicketInputDTO dto) {
        Integer couchId = (Integer) httpSession.getAttribute("couchId");
        Couch couch = couchService.getCouch(couchId);
        SportSection section = sectionService.getSportSection(dto.getSectionId());
        if (couch != null && section != null){
            SeasonTicket seasonTicket = seasonTicketsService.addSeasonTicket(dto,couch,section);
            return new ResponseEntity<>(seasonTicket.getSeasonTicketDTO(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

//    @Operation(summary = "Вывести для пользователя по uuid")
//    @GetMapping("/ticket/get/{uuid}")
//    public ResponseEntity<List<Map<String,Object>>>getTicketForUser(@PathVariable UUID uuid, HttpSession httpSession){
//        Integer userId = (Integer) httpSession.getAttribute("userId");
//        User user = userService.getUser(userId);
//        Map<UUID,List<Object>> getAllTicketForUser =  seasonTicketsService.getAllSeasonTicketForUser(uuid,user);
//        List<Map<String,Object>>result = new ArrayList<>();
//        for (UUID uuid:getAllTicketForUser) {
//            Map<String,Object> item = new HashMap<>();
//            item.put("id",getAllTicketForUser.get(uuid).get(0).getId);
//            result.add(item);
//        }
//        return new ResponseEntity<>(result,HttpStatus.OK);
//    }

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
            item.put("trainings", allSeasonTickets.get(uuid).get(0).getTrainings());
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

    @Operation(summary = "Удаление абонимента по id")
    @DeleteMapping("/deleteTicket{ticketId}")
    public ResponseEntity<SeasonTicket>deleteTicket(@PathVariable Integer ticketId){
        SeasonTicket seasonTicket = seasonTicketsService.deleteTicket(ticketId);
        return new ResponseEntity<>(seasonTicket,HttpStatus.OK);
    }

//    @Operation(summary = "Вывести все тарифы у пользователя")
//    @GetMapping("/user/tickets/{uuid}")
//    public ResponseEntity<List<SeasonTicket>>getAllTicketForUser(@PathVariable UUID uuid){
//
//    }


    @Operation(summary = "Бронирование тарифа")
    @PostMapping("/book/{uuid}")
    public ResponseEntity<SeasonTicket> booking(@PathVariable UUID uuid, HttpSession httpSession){
        List<SeasonTicket> ticketsByUUID = seasonTicketsService.getTicketsByUUID(uuid);
        Integer userId = (Integer) httpSession.getAttribute("userId");
        User user = userService.getUser(userId);
        if (ticketsByUUID.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        LocalDate localDateNow = LocalDate.now();
        Integer allTrainingCount = ticketsByUUID.get(0).getTrainings();

        List<LocalDateTime> matchingDates = new ArrayList<>();
        Map<DayOfWeek, List<LocalTime>> targetDateTimes = new HashMap<>();
        for (SeasonTicket ticket:ticketsByUUID) {

            String dayOfWeekRussian = ticket.getDayOfWeek();
            DayOfWeek targetDay = RUSSIAN_DAY_OF_WEEK.get(dayOfWeekRussian);
            if (targetDay == null) {
                System.out.println("Неизвестный день недели: " + ticket.getDayOfWeek());
                continue;
            }
            LocalTime targetTime = LocalTime.parse(ticket.getTime());
            if (!targetDateTimes.containsKey(targetDay)){
                targetDateTimes.put(targetDay, new ArrayList<>());
            }
            targetDateTimes.get(targetDay).add(targetTime);
            targetDateTimes.get(targetDay).sort(Comparator.naturalOrder());
        }

        while (allTrainingCount > 0){

            if (targetDateTimes.containsKey(localDateNow.getDayOfWeek())) {
                for (LocalTime targetTime : targetDateTimes.get(localDateNow.getDayOfWeek())) {
                    LocalDateTime matchingDateTime = LocalDateTime.of(localDateNow, targetTime);
                    matchingDates.add(matchingDateTime);
                    allTrainingCount--;
                    if (allTrainingCount == 0){
                        break;
                    }
                }
            }
            localDateNow = localDateNow.plusDays(1);
        }


        Integer sum = ticketsByUUID.get(0).getSum();
        int size = matchingDates.size();
        Double matchingPrice = Double.valueOf(sum)/size;

        if (user.getBalance() < sum || sum == 0){
            return new ResponseEntity<>(null,HttpStatus.PAYMENT_REQUIRED);
        }


        for (LocalDateTime date : matchingDates){
            Schedule currentSchedule = scheduleService.getScheduleByDateTime(date, ticketsByUUID.get(0).getCouch());
            if (currentSchedule != null) {
                Booking booking = bookingService.addBooking(currentSchedule, user,matchingPrice);
                if (booking == null) {
                    System.out.println("Не удалось создать бронирование на " + date);
                }
            } else {
                System.out.println("Расписание не найдено для даты: " + date);
            }
        }
        return  new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Вывести все тарифы для тренера")
    @GetMapping("/ticket/get/{sportSectionId}")
    public ResponseEntity< List<Map<String, Object>>> getSeasonTicket (@PathVariable Integer sportSectionId, HttpSession httpSession){
        SportSection section = sectionService.getSportSection(sportSectionId);
        Integer couchId = (Integer) httpSession.getAttribute("couchId");
        Couch couch = couchService.getCouch(couchId);
        Map<UUID, List<SeasonTicketDTO>> allSeasonTickets = seasonTicketsService.getAllSeasonTickets(section, couch);
        List<Map<String, Object>> result = new ArrayList<>();
        for (UUID uuid : allSeasonTickets.keySet()) {
            Map<String, Object> item =new HashMap<>();
            item.put("id",allSeasonTickets.get(uuid).get(0).getId());
            item.put("name", allSeasonTickets.get(uuid).get(0).getName());
            item.put("description", allSeasonTickets.get(uuid).get(0).getDescription());
            item.put("sum", allSeasonTickets.get(uuid).get(0).getSum());
            item.put("uuid", allSeasonTickets.get(uuid).get(0).getUuid() );
            item.put("couch", allSeasonTickets.get(uuid).get(0).getCouch());
            item.put("trainings", allSeasonTickets.get(uuid).get(0).getTrainings());
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
}
