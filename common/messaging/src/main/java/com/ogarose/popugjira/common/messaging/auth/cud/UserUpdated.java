package com.ogarose.popugjira.common.messaging.auth.cud;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class UserUpdated {
    private UUID publicId;
    private String username;
    private String role;
    private String email;
    private String phone;
}
