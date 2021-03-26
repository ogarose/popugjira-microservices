package com.ogarose.popugjira.auth.model.event.cud;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UserCreated {
    private UUID id;
    private String type = "userCreated";

    public UserCreated(UUID id) {
        this.id = id;
    }
}
