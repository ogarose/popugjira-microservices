package com.ogarose.popugjira.accounting.application.controller;

import com.ogarose.popugjira.accounting.usecase.user.CalculateManagerEarnTodayUseCase;
import com.ogarose.popugjira.accounting.usecase.user.GetAllUseCase;
import com.ogarose.popugjira.accounting.usecase.user.PayDayBalance;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class DashboardController {
    private final PayDayBalance payDayBalanceService;
    private final GetAllUseCase getAllUseCase;
    private final CalculateManagerEarnTodayUseCase calculateManagerEarnTodayUseCase;

    @GetMapping({"", "/", "/index"})
    public String getIndexPage(Model model) {

        model.addAttribute("users", getAllUseCase.execute());
        model.addAttribute("managerEarn", calculateManagerEarnTodayUseCase.execute());

        return "index";
    }

    @GetMapping("/pay-day-balance")
    public String payDayBalance() {
        payDayBalanceService.execute();

        return "redirect:/";
    }
}
