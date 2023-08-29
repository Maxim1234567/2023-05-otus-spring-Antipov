package ru.otus.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PassportController {
    @GetMapping("/passport/info")
    public String infoPage(
            @RequestParam("series") String series, @RequestParam("number") String number, Model model) {
        model.addAttribute("series", series);
        model.addAttribute("number", number);

        return "info-passport";
    }
}
