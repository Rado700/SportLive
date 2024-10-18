package ru.sportlive.mvp.controller;

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

    private static final String SHA1 = System.getenv("SHA1");

    @GetMapping("/info")
    public String getInfo() throws Exception {
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

    @PostMapping(value = "/getPaymentConformation/", consumes = "application/x-www-form-urlencoded")
    public ResponseEntity<Object> getConformationDTO(@RequestBody PaymentConformationDTO paymentConformationDTO) {
        Integer user_id = Integer.valueOf(paymentConformationDTO.getLabel());

        // Получаем параметры для хеширования
        String notification_type = paymentConformationDTO.getNotification_type();
        String operation_id = paymentConformationDTO.getOperation_id();
        String amount = paymentConformationDTO.getAmount().toString();
        String currency = paymentConformationDTO.getCurrency();
        String datetime = paymentConformationDTO.getData().toString();
        String sender = paymentConformationDTO.getSender();
        String codepro = paymentConformationDTO.getCodepro() ? "true" : "false";
        String notification_secret = SHA1;  // Здесь ваш секретный ключ
        String label = paymentConformationDTO.getLabel() != null ? paymentConformationDTO.getLabel() : "";

        // Формируем строку для хеширования
        String dataForHash = String.join("&", notification_type, operation_id, amount, currency, datetime, sender, codepro, notification_secret, label);

        // Вычисляем SHA-1 хэш
        String calculatedHash = DigestUtils.sha1Hex(dataForHash);

        // Проверяем хэш
        if (!calculatedHash.equals(paymentConformationDTO.getSha1_hash())) {
            return new ResponseEntity<>("Invalid sha1_hash", HttpStatus.BAD_REQUEST);
        }

        // Если хэш совпал, продолжаем обработку
        User user = userService.getUser(user_id);
        user = userService.deposit(paymentConformationDTO.getAmount(), user);
        Login login = loginService.getUserLogin(user_id);

        transactionService.addTransaction(login, paymentConformationDTO.getAmount().intValue(), "deposit");

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

}
