package com.ogarose.popugjira.common.messaging.auth.cud;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class UserCreated {
    private UUID publicId;
    private String username;
    private String role;
    private String email;
    private String phone;

    public UserCreated() {
    }
}
