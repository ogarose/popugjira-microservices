package com.ogarose.popugjira.auth.controller.response;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UserInfoResponse {
    public String sub;
    public String name;
    public UUID publicId;
    public String role;
    public String email;
    public String phone;
}
