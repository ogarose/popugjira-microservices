package com.ogarose.popugjira.common.messaging.accounting.biz;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BalancePaid {
    private UUID userId;
    private Integer paidSum;
}
