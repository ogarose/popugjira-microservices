package com.ogarose.popugjira.auth.model.event.cud;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UserUpdated {
    private UUID id;
    private String type = "userUpdated";

    public UserUpdated(UUID id) {
        this.id = id;
    }
}
