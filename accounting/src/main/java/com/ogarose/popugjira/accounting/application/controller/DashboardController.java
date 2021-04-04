package com.ogarose.popugjira.accounting.application.controller;

import com.ogarose.popugjira.accounting.domain.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class DashboardController {
    private final UserRepository userRepository;

    @GetMapping({"", "/", "/index"})
    public String getIndexPage(Model model) {

        model.addAttribute("users", userRepository.findAll());

        return "index";
    }
}
