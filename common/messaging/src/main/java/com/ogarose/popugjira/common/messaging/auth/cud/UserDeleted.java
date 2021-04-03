package com.ogarose.popugjira.common.messaging.auth.cud;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class UserDeleted {
    private UUID publicId;

    public UserDeleted(UUID publicId) {
        this.publicId = publicId;
    }
}
