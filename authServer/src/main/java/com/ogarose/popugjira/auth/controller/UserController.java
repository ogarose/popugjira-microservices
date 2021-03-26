package com.ogarose.popugjira.auth.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ogarose.popugjira.auth.service.command.UserCommand;
import com.ogarose.popugjira.auth.model.User;
import com.ogarose.popugjira.auth.service.UserService;
import com.ogarose.popugjira.auth.service.command.UserCommandMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@Controller
@AllArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserCommandMapper userCommandMapper;

    @GetMapping("/users")
    public String showList(Model model) {
        List<User> users = userService.getAll();

        model.addAttribute("users", users);

        return "user/list";
    }

    @GetMapping("/users/add")
    public String showAdd(Model model) {
        List<User> users = userService.getAll();

        model.addAttribute("userCommand", new UserCommand());

        return "user/add";
    }

    @PostMapping("/users")
    public String addUser(@Valid UserCommand userCommand, BindingResult result) throws JsonProcessingException {
        if(result.hasErrors()) {
            return "user/add";
        }

        if(userCommand.getId() != null) {
            userService.updateUser(userCommand);
        } else {
            userService.createUser(userCommand);
        }

        return "redirect:/users";
    }

    @GetMapping("/users/{id}/edit")
    public String showAdd(@PathVariable("id") UUID id, Model model) {
        User user = userService.getById(id);

        UserCommand userCommand = userCommandMapper.toCommand(user);

        model.addAttribute("userCommand", userCommand);

        return "user/add";
    }

    @GetMapping("/users/{id}/delete")
    public String delete(@PathVariable("id") UUID id, Model model) throws JsonProcessingException {
        User user = userService.getById(id);

        userService.delete(user);

        return "redirect:/users";
    }
}
