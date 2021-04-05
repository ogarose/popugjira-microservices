package com.ogarose.popugjira.accounting.application.controller;

import com.ogarose.popugjira.accounting.application.usecase.user.PayDayBalance;
import com.ogarose.popugjira.accounting.domain.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class DashboardController {
    private final UserRepository userRepository;
    private final PayDayBalance payDayBalanceService;

    @GetMapping({"", "/", "/index"})
    public String getIndexPage(Model model) {

        model.addAttribute("users", userRepository.findAll());
        model.addAttribute("managerEarn", userRepository.getManagerEarnToday());

        return "index";
    }

    @GetMapping("/pay-day-balance")
    public String payDayBalance() {
        payDayBalanceService.execute();

        return "redirect:/";
    }
}
