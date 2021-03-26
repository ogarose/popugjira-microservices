package com.ogarose.popugjira.auth.model.event.cud;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UserDeleted {
    private UUID id;
    private String type = "userDeleted";

    public UserDeleted(UUID id) {
        this.id = id;
    }
}
