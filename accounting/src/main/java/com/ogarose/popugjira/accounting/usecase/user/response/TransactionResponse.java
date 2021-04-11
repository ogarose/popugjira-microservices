package com.ogarose.popugjira.accounting.usecase.user.response;

import lombok.Value;

import java.time.LocalDateTime;

@Value
public class TransactionResponse {
    Integer id;
    Integer debit;
    Integer credit;
    String description;
    LocalDateTime createdAt;
}
