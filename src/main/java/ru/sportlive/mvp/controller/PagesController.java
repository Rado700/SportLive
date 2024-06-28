package ru.sportlive.mvp.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.sportlive.mvp.services.CouchService;

@Controller
public class PagesController {
    @Autowired
    CouchService couchService;
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
    public String organisationUser(){
        return "enterUserForOrganisation";
    }
    @GetMapping("/account/couch/select")
    public String organisationCouch(){
        return "enterCouchForOrganisation";
    }
}
