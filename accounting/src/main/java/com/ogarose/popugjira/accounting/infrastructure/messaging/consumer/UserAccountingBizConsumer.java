package com.ogarose.popugjira.accounting.infrastructure.messaging.consumer;

import com.ogarose.popugjira.accounting.application.handler.SendNotificationWhenBalancePaidHandler;
import com.ogarose.popugjira.common.messaging.MessageTopics;
import com.ogarose.popugjira.common.messaging.accounting.biz.BalancePaid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
@KafkaListener(topics = MessageTopics.USER_ACCOUNTING_BIZ)
public class UserAccountingBizConsumer {

    private final SendNotificationWhenBalancePaidHandler handler;

    @KafkaHandler
    public void listener(BalancePaid balancePaid) {
        handler.handle(balancePaid.getUserId(), balancePaid.getPaidSum());
    }
}
