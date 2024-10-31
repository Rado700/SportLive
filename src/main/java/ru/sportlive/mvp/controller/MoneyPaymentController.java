package ru.sportlive.mvp.controller;

import io.github.cdimascio.dotenv.Dotenv;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sportlive.mvp.dto.input.PaymentConformationDTO;
import ru.sportlive.mvp.dto.input.UserPayDTO;
import ru.sportlive.mvp.models.Login;
import ru.sportlive.mvp.models.User;
import ru.sportlive.mvp.services.LoginService;
import ru.sportlive.mvp.services.MoneyPaymentService;
import ru.sportlive.mvp.services.TransactionService;
import ru.sportlive.mvp.services.UserService;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/yoomoney")
public class MoneyPaymentController {

    @Autowired
    MoneyPaymentService moneyPaymentService;

    @Autowired
    LoginService loginService;
    @Autowired
    UserService userService;
    @Autowired
    TransactionService transactionService;


    @GetMapping("/info")
    public String getInfo() throws Exception {
        Dotenv dotenv = Dotenv.load();
        String ACCESS_TOKEN = dotenv.get("ACCESS_TOKEN");
        System.out.println(ACCESS_TOKEN);
        return moneyPaymentService.getPaymentHistory();
    }

    @GetMapping("/infoPay/{operationId}")
    public String getInfoPay(@PathVariable String operationId) throws Exception {
        return moneyPaymentService.getDetailedPaymentHistory(operationId);
    }

    @GetMapping("/getInvoicePay/{amount}")
    public String getInvoicePay(@PathVariable String amount,HttpSession httpSession) throws Exception { //TODO:вместо тест сделать id пользователя получить из сессий
        Integer user_id = (Integer) httpSession.getAttribute("userId");
        return moneyPaymentService.createPaymentLink("4100115951516729", amount, user_id, "https://sportliveapp.ru");
    }

    @PostMapping("/getNotificationForPay/")
    public String getNotificationForPay() throws Exception {
        return moneyPaymentService.getNotification();
    }

    @PostMapping("/getPaymentConformation/")
    public ResponseEntity<Object> getConformationDTO(
            @RequestParam String notification_type,
            @RequestParam String operation_id,
            @RequestParam Double amount,
            @RequestParam String currency,
            @RequestParam OffsetDateTime datetime,
            @RequestParam String sender,
            @RequestParam boolean codepro,
            @RequestParam String label,
            @RequestParam String sha1_hash
    ) {
        Integer user_id;
        try {
            user_id = Integer.valueOf(label);
        }catch (Exception e){
            user_id = 1;
        }

//        datetime = datetime.withOffsetSameInstant(ZoneOffset.ofHours(3));
        String formattedDatetime = datetime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX"));

        Dotenv dotenv = Dotenv.configure()
                .ignoreIfMalformed()
                .ignoreIfMissing()
                .load();
        // Ваш код для хеширования и обработки
        String notification_secret = dotenv.get("SHA1_KEY");  // Ваш секретный ключ
        System.out.println(notification_secret);
        String dataForHash = String.join("&", notification_type, operation_id, amount.toString(), currency, formattedDatetime, sender, codepro ? "true" : "false", notification_secret, label);
        System.out.println(dataForHash);
        String calculatedHash = DigestUtils.sha1Hex(dataForHash);

        System.out.println(calculatedHash);
        System.out.println(sha1_hash);

        if (!calculatedHash.equals(sha1_hash)) {
            return new ResponseEntity<>("Invalid sha1_hash", HttpStatus.BAD_REQUEST);
        }

        // Если хэш совпал, продолжаем обработку
        User user = userService.getUser(user_id);
        user = userService.deposit(amount, user);
        Login login = loginService.getUserLogin(user_id);
        transactionService.addTransaction(login, amount.intValue(), "deposit");
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

}
