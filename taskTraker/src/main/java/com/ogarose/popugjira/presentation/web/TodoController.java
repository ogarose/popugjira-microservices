package com.ogarose.popugjira.presentation.web;

import com.ogarose.popugjira.application.usecase.task.GetAllAssignedToUser;
import com.ogarose.popugjira.application.usecase.task.GetAllClosed;
import com.ogarose.popugjira.application.usecase.task.GetAllOpened;
import com.ogarose.popugjira.domain.user.User;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
@AllArgsConstructor
public class TodoController {

    private final GetAllOpened getAllOpenedUseCase;
    private final GetAllClosed getAllClosedUseCase;
    private final GetAllAssignedToUser getAllAssignedToUserUseCase;

    @GetMapping({"", "/", "/index"})
    public String getIndexPage(Model model, Principal principal) {

        return "index";
    }

    @GetMapping({"/todo"})
    public String todo(Model model) {

        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        model.addAttribute("openTasks", getAllOpenedUseCase.execute());
        model.addAttribute("closedTasks", getAllClosedUseCase.execute());
        model.addAttribute("assignedTask", getAllAssignedToUserUseCase.execute(principal.getId()));

        return "todo/index";
    }
}
