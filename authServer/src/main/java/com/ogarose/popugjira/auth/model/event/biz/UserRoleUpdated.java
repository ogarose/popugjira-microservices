package com.ogarose.popugjira.auth.model.event.biz;

import com.ogarose.popugjira.auth.model.Roles;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UserRoleUpdated {
    private String type = "userRoleUpdated";
    private UUID id;
    private Roles role;
}
