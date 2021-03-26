package com.ogarose.popugjira.auth.controller;

import com.ogarose.popugjira.auth.controller.response.UserInfoResponse;
import com.ogarose.popugjira.auth.model.User;
import com.ogarose.popugjira.auth.repository.UserRepositoryJpa;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@AllArgsConstructor
public class AuthController {

    private final UserRepositoryJpa userRepositoryJpa;

    @RequestMapping("/auth/user-info")
    public UserInfoResponse userInfo(Principal principal) {
        User userModel = userRepositoryJpa.findByUsername(principal.getName()).orElseThrow();

        UserInfoResponse userInfo = new UserInfoResponse();
        userInfo.setSub(userModel.getUsername());
        userInfo.setName(userModel.getUsername());
        userInfo.setPublicId(userModel.getId());
        userInfo.setRole(userModel.getRole().name());
        userInfo.setEmail(userModel.getEmail());
        userInfo.setPhone(userModel.getPhone());

        return userInfo;
    }
}
