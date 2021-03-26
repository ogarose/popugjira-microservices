package com.ogarose.popugjira.auth.service.command;

import com.ogarose.popugjira.auth.model.Roles;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;
import java.util.UUID;

@Getter
@Setter
public class UserCommand {
    private UUID id;
    @Size(min = 4)
    private String username;
    private Roles role;
    private String password;
    private String email;
    private String phone;
}
