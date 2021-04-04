package com.ogarose.popugjira.accounting.application.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @GetMapping({"", "/", "/index"})
    public String getIndexPage(Model model) {
        return "index";
    }
}
