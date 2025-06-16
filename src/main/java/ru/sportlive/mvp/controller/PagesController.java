package ru.sportlive.mvp.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.sportlive.mvp.models.Couch;
import ru.sportlive.mvp.models.Login;
import ru.sportlive.mvp.services.CouchService;
import ru.sportlive.mvp.services.LoginService;

import java.security.NoSuchAlgorithmException;

@Controller
public class PagesController {
    @Autowired
    CouchService couchService;

    @Autowired
    LoginService loginService;
    @GetMapping("/")
    public String authorisation(HttpSession httpSession){
        Integer couchId = (Integer) httpSession.getAttribute("couchId");
        Integer userId = (Integer) httpSession.getAttribute("userId");
        if (couchId != null){
            return "redirect:couches";
        }else if (userId != null){
            return "redirect:account";
        }else{
            return "autorisation";
        }
    }

    @GetMapping("/auth/tg")
    public String authTg(HttpSession httpSession){
        return "authTg";
    }

    @Operation(summary = "qr-cod Couch для User")
    @GetMapping("/auth/qr")
    public String getCouchTgId(@RequestParam String couchTgId,@RequestParam String page,@RequestParam String section, HttpSession httpSession) throws NoSuchAlgorithmException {
        Login loginCouch = loginService.getLoginByTgId(couchTgId);
        Couch couch = loginCouch.getCouch();
        Integer userId = (Integer) httpSession.getAttribute("userId");
        if (userId != null) {
            return "redirect:/account?page=" + page + "&couchId=" + couch.getId() + "&section" + section;
        }
        return "redirect:/?couchId=" + couch.getId();
    }
    @GetMapping("/account")
    public String account(HttpSession httpSession){
        Integer userId = (Integer) httpSession.getAttribute("userId");
        if (userId == null){
            return "redirect:/";
        }
        return "user";
    }

    @GetMapping("/couches")
    public String couch(HttpSession httpSession){
        Integer couchID = (Integer) httpSession.getAttribute("couchId");
        if (couchID == null){
            return "redirect:/";
        }
        return "couch";
    }

    @GetMapping("/account/user/select")
    public String organisationUser(HttpSession httpSession){
        Integer userId = (Integer) httpSession.getAttribute("userId");
        if (userId == null){
            return "redirect:/";
        }
        return "enterUserForOrganisation";
    }
    @GetMapping("/account/couch/select")
    public String organisationCouch(HttpSession httpSession){
        Integer couchID = (Integer) httpSession.getAttribute("couchId");
        if (couchID == null){
            return "redirect:/";
        }
        return "enterCouchForOrganisation";
    }
    @GetMapping("/account/user/changeCoach")
    public String coachChangeForUser(HttpSession httpSession){
        Integer userId = (Integer) httpSession.getAttribute("userId");
        if (userId == null){
            return "redirect:/";
        }
        return "changeCoach";
    }

//    @GetMapping("/pay")
//    public String getPay(HttpSession httpSession) {
//        Integer userId = (Integer) httpSession.getAttribute("userId");
//        if (userId != null) {
//            return "redirect:/http://localhost:8080/yoomoney/getInvoicePay/";
//        }
//        return null;
//    }
}
