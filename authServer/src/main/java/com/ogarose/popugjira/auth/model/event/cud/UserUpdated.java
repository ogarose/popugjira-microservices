package com.ogarose.popugjira.auth.model.event.cud;

import com.ogarose.popugjira.auth.model.Roles;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class UserUpdated {
    private UUID publicId;
    private String username;
    private Roles role;
    private String email;
    private String phone;
}
