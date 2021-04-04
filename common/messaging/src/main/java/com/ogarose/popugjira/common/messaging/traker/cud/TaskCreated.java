package com.ogarose.popugjira.common.messaging.traker.cud;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.UUID;

@ToString
@Getter
@NoArgsConstructor
public class TaskCreated {
    private UUID id;

    public TaskCreated(UUID id) {
        this.id = id;
    }
}
