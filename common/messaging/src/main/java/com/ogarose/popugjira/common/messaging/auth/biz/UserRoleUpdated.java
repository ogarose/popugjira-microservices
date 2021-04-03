package com.ogarose.popugjira.common.messaging.auth.biz;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserRoleUpdated {
    private UUID publicId;
    private String role;
}
