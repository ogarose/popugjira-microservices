package com.ogarose.popugjira.accounting.usecase.user.response;

import lombok.Value;

import java.util.UUID;

@Value
public class UserResponse {
    UUID id;
    String username;
    Integer balance;
}
