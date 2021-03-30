package com.ogarose.popugjira.auth.model.event.biz;

import com.ogarose.popugjira.auth.model.Roles;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class UserRoleUpdated {
    private UUID publicId;
    private Roles role;
}
