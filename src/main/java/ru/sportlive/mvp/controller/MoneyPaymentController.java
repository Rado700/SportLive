package ru.sportlive.mvp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.sportlive.mvp.services.MoneyPaymentService;

@RestController
@RequestMapping("/yoomoney")
public class MoneyPaymentController {

    @Autowired
    MoneyPaymentService moneyPaymentService;

    @GetMapping("/info")
    public String getInfo() throws Exception {
        return moneyPaymentService.getPaymentHistory();
    }


}
