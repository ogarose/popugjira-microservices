package com.ogarose.popugjira.accounting.application.controller;

import com.ogarose.popugjira.accounting.usecase.user.GetAllUserDayTransactionsUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

@Controller
@RequestMapping("/transactions")
@AllArgsConstructor
public class TransactionController {
    private GetAllUserDayTransactionsUseCase getAllUserDayTransactionsUseCase;

    @GetMapping("/user/{id}/day")
    public String userDayTransaction(@PathVariable UUID id, Model model) {
        model.addAttribute("transactions", getAllUserDayTransactionsUseCase.execute(id));

        return "transaction/user_day";
    }
}
