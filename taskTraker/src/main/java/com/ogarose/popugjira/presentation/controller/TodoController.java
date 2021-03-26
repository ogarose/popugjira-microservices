package com.ogarose.popugjira.presentation.controller;

import com.ogarose.popugjira.application.TaskService;
import com.ogarose.popugjira.domain.user.User;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
@AllArgsConstructor
public class TodoController {

    private final TaskService taskService;

    @GetMapping({"", "/", "/index"})
    public String getIndexPage(Model model, Principal principal) {

        return "index";
    }

    @GetMapping({"/todo"})
    public String todo(Model model) {

        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        model.addAttribute("openTasks", taskService.findAllOpened());
        model.addAttribute("closedTasks", taskService.findAllClosed());
        model.addAttribute("assignedTask", taskService.findAllAssignedToUser(principal));

        return "todo/index";
    }
}
