package ru.sportlive.mvp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PagesController {
    @GetMapping("/")
    public String authorisation(){
        return "autorisation";
    }

    @GetMapping("/account")
    public String account(){
        return "user";
    }

    @GetMapping("/couches")
    public String couch(){
        return "couch";
    }
}
