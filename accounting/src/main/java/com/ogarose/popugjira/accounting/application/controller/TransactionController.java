package com.ogarose.popugjira.accounting.application.controller;

import com.ogarose.popugjira.accounting.domain.user.TransactionRepository;
import com.ogarose.popugjira.accounting.domain.user.User;
import com.ogarose.popugjira.accounting.domain.user.UserRepository;
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
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;

    @GetMapping("/user/{id}/day")
    public String userDayTransaction(@PathVariable UUID id, Model model) {
        User searchedUser = userRepository.find(id).orElseThrow();

        model.addAttribute("transactions", transactionRepository.findAllOfDayByUser(searchedUser));

        return "transaction/user_day";
    }
}
